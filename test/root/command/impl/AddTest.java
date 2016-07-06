package root.command.impl;

import root.command.Command;

import static org.junit.Assert.*;


public class AddTest {
    @org.junit.Test
    public void execute() throws Exception {
        Command command = CommandManager.ADD.getCommand();
        double expected = 3.2;
        double result = command.execute(1.1, 2.1);
        assertEquals(3.2, result, 0.000001 );

    }
}