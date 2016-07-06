package root.command.impl;


import root.command.Command;

class Multiply implements Command {
    @Override
    public double execute(double... args) {
        double result = 1;
        for (int i = 0; i < args.length; i++) {
            result *=args[i];
        }
        return result;

    }

    @Override
    public Command getAggregate() {
        return this;
    }
}
