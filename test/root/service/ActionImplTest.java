package root.service;

import org.junit.Assert;
import root.command.Command;
import root.command.impl.CommandManager;
import root.service.exception.ServiceException;
import root.service.exception.SourceFormatException;
import root.service.impl.ActionImpl;

import java.io.File;
import java.io.WriteAbortedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


import static org.junit.Assert.*;


public class ActionImplTest {

    private static Action actionImpl = new ActionImpl();
    private static double[] expected = {1.1, 1.2, 1.3};


    @org.junit.Test
    public void executeADD() throws Exception {
        String path = "test\\testSource\\in1.dat";
        double[] expected = {1.1, 1.2, 1.3};
        double expectedResult = 0;
        for (int i = 0; i < expected.length; i++) {
            expectedResult+= expected[i];
        }
        double actual = actionImpl.doAction(path);
        System.out.println(actual);
        assertEquals(expectedResult, actual, 0.000001);
    }
    @org.junit.Test
    public void executeMultiply() throws ServiceException, WriteAbortedException {
        String path = "test\\testSource\\in2.dat";
        double expectedResult = 1;
        for (int i = 0; i < expected.length; i++) {
            expectedResult*= expected[i];
        }

        double actual = actionImpl.doAction(path);
        assertEquals(expectedResult, actual, 0.000001);
    }

    @org.junit.Test
    public void executeSumAndSquare() throws ServiceException, WriteAbortedException {
        String path = "test\\testSource\\in3.dat";
        double expectedResult = 0;
        for (int i = 0; i < expected.length; i++) {
            expectedResult+= Math.pow(expected[i], 2);
        }
        double actual = actionImpl.doAction(path);
        assertEquals(expectedResult, actual, 0.000001);
    }

    @org.junit.Test
    public void convertToDoubleArray() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method m = actionImpl.getClass().getDeclaredMethod("defineData", String.class);
        double[] expected = {1.1, 1.2, 1.3};
        m.setAccessible(true);
        String testString = "1.1 1.2 1.3";
        double[] actual = (double[]) m.invoke(actionImpl, testString);
        m.setAccessible(false);
        assertArrayEquals(expected, actual, 0.0001);
    }

    @org.junit.Test
    public void getDataFromSource() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String path = "test\\testSource\\in1.dat";
        Method m = actionImpl.getClass().getDeclaredMethod("getDataFromSource", String.class);
        m.setAccessible(true);
        Object actual = m.invoke(actionImpl, path);
        m.setAccessible(false);
        String toStrOfVault = "DataTransferObject{commandCode='1', data='1.1 1.2 1.3'}";
        assertEquals(toStrOfVault, actual.toString());

    }
    @org.junit.Test
    public void defineCommand() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String command = "1";
        String command2 = "2";
        String command3 = "3";

        Method m = actionImpl.getClass().getDeclaredMethod("defineCommand", String.class);
        m.setAccessible(true);

        Object actual = m.invoke(actionImpl, command);
        Assert.assertEquals(actual.getClass(), Class.forName("root.command.impl.Add"));

        Object actual2 = m.invoke(actionImpl, command2);
        Assert.assertEquals(actual2.getClass(), Class.forName("root.command.impl.Multiply"));

        Object actual3 = m.invoke(actionImpl, command3);
        Assert.assertEquals(actual3.getClass(), Class.forName("root.command.impl.SumOfSquares"));

        m.setAccessible(false);
    }
    @org.junit.Test
    public void getPathAndCheckExistance() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       String path = "test\\testSource\\in1.dat";
        String expected = "test\\testSource\\in1.dat";
        Method m = actionImpl.getClass().getDeclaredMethod("getPathAndCheckExistance", String.class);
        m.setAccessible(true);
        Object object = m.invoke(actionImpl, path);
        m.setAccessible(false);
        File file = new File(path);
        assertTrue(file.exists());
        String actual = file.getPath();
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void writeResultToFile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String path = "test\\testSource\\out.dat";
        Method m = actionImpl.getClass().getDeclaredMethod("writeResultToFile", double.class, String.class);
        m.setAccessible(true);
        File file = new File(path);
        final long totalSpace = file.getTotalSpace();
        Object object = m.invoke(actionImpl, 3.1, path);
        File file1 = new File(path);
        final long totalSpaceAfter = file1.getTotalSpace();
        m.setAccessible(false);
    }
}