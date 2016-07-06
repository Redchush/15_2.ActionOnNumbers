package root.command.impl;


import root.command.Command;

class SumOfSquares implements Command {

    @Override
    public double execute(double... args) {
        double result = 0;
        for (int i = 0; i < args.length; i++) {
            result+= Math.pow(args[i], 2);
        }
        return result;
    }

    @Override
    public Command getAggregate() {
        return new Add();
    }
}
