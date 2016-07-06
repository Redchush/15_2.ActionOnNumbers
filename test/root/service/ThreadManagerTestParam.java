package root.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import root.command.Command;
import root.command.impl.CommandManager;
import root.service.impl.ThreadManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ThreadManagerTestParam {

    private Command command;
    private double[] data;

    public ThreadManagerTestParam(double[] data, Command command) {
        this.command = command;
        this.data = data;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        List<Object[]> objects = new ArrayList<Object[]>();
        Command command = CommandManager.getCommandByCode(1);

        for (int i = 1; i < 5; i++) {
            double[] array =  createRandomZArray(i*10);
            objects.add(new Object[]{
                    array, command
            });
        }
        command = CommandManager.getCommandByCode(2);
        for (int i = 1; i < 5; i++) {
            double[] array =  createRandomZArray(i*10);
            objects.add(new Object[]{
                    array, command
            });
        }

        command = CommandManager.getCommandByCode(3);
        for (int i = 1; i < 5; i++) {
            double[] array =  createRandomZArray(i*10);
            objects.add(new Object[]{
                    array, command
            });
        }
        return objects;
    }

    private static double[] createRandomArray(int length){
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            double temp = Math.random()*100;
            result[i] = temp;
        }
        return result;
    }

    private static double[] createRandomZArray(int length){
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            int temp = (int) (Math.random()*10);

            result[i] = temp;
        }
        return result;
    }

    @Test
    public void totalCompare() throws Exception {
        double expected = this.command.execute(this.data);
        ThreadManager manager = new ThreadManager(this.command, this.data, this.command.getAggregate());
        double actual = manager.calculate();
        assertEquals(expected, actual, 0.0000001);
    }
}