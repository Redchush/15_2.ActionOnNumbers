package root.command.impl;

import root.command.Command;

public enum CommandManager {

    ADD(new Add()),
    MULTIPLY(new Multiply()),
    SUM_OF_SQUARES(new SumOfSquares());

    private Command command;
    private int code;

    private CommandManager(Command command) {
        this.command = command;
        this.code = this.ordinal() + 1;
    }

    public static Command getCommandByCode(int code) {
        for(CommandManager c : CommandManager.values()) {
            if (c.code == code){
                return c.command;
            }
        }
        return null;
    }

    public Command getCommand() {
        return command;
    }
}
