package root.view.impl;

import root.service.Action;
import root.service.exception.ServiceException;
import root.service.exception.SourceFormatException;
import root.service.exception.SourceNotFoundException;
import root.view.View;

import java.io.WriteAbortedException;

public class ConsoleView implements View {
    private Action action;

    public ConsoleView(Action action){
        this.action = action;
    }

    @Override
    public void start(String source){
        while (true) {
            try {
                ConsoleHelper.writeMessage("Starting execute the file " + source);
                double result = action.doAction(source);
                ConsoleHelper.writeMessage("The result of calculation, that you can see in out.dat, is:");
                ConsoleHelper.writeMessage(result);
            } catch (SourceNotFoundException e){
                ConsoleHelper.writeMessage("Invalid root.view.impl.testSource path");
            } catch (SourceFormatException e){
                ConsoleHelper.writeMessage("You file isn't meet the file format requirments");
            } catch (ServiceException e) {
                ConsoleHelper.writeMessage("Sorry. Can't execute this file");
            } catch (WriteAbortedException e) {
                ConsoleHelper.writeMessage("Sorry. Can't write the result to file");
            } finally {
                restart();
            }
        }
    }

    @Override
    public void restart(){
        ConsoleHelper.writeMessage("Do you want to execute another file? yes/no");
        String s = ConsoleHelper.readString();
        if (s.equalsIgnoreCase("no")){
            System.exit(0);
        } else {
            ConsoleHelper.writeMessage("Enter the file path.");
            String result = ConsoleHelper.readString();
            start(result);
        }
    }

    @Override
    public void closeStream(){
        ConsoleHelper.closeReaderQuetly();
    }
}
