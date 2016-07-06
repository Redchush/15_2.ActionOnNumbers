package root.command;


public interface Command {

    double execute(double... args);
    Command getAggregate();

}
