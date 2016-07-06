package root.service;

import org.junit.Assert;
import org.junit.Test;
import root.command.Command;
import root.command.impl.CommandManager;
import root.service.impl.ThreadManager;

import java.util.Arrays;

import static org.junit.Assert.*;


public class ThreadManagerTest {
    private static  double[] data = {1.1, 1.2, 1.3};

    @Test
    public void getResult() throws Exception {
        Command command = CommandManager.getCommandByCode(1);
        double expected = command.execute(data);
        ThreadManager manager = new ThreadManager(command, data, command.getAggregate());
        double actual = manager.calculate();
        Assert.assertEquals(expected,actual, 0.000001 );
    }


    private double[] createRandomArray(int length){
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            double temp = Math.random()*100;
            result[i] = temp;
        }
        return result;
    }

    private double[] create(int length){
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            int temp = (int) (Math.random()*10);

            result[i] = temp;
        }
        return result;
    }

    @Test
    public void totalCompare() throws Exception {
        double[] toTest = createRandomArray(120);
        System.out.println(Arrays.toString(toTest));
        Command command = CommandManager.getCommandByCode(1);
        double expected = command.execute(toTest);
        ThreadManager manager = new ThreadManager(command, toTest, command.getAggregate());
        double actual = manager.calculate();
        assertEquals(expected, actual, 0.0000001);
    }


}