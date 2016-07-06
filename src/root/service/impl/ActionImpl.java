package root.service.impl;

import root.command.Command;
import root.command.impl.CommandManager;
import root.service.Action;
import root.service.exception.*;

import java.io.*;

public class ActionImpl implements Action {

    @Override
    public double doAction(String source) throws ServiceException, WriteAbortedException {
        DataTransferObject vault = getDataFromSource(source);
        ThreadManager manager = null;
        try{
            Command command = defineCommand(vault.getCommandCode());
            double[] data = defineData(vault.getData());
            Command aggregate = command.getAggregate();
            manager = new ThreadManager(command, data, aggregate);
            double result = manager.calculate();
            String path = getPathAndCheckExistance(source);
            writeResultToFile(result, path);
            return result;
        } catch (NumberFormatException e){
            throw new SourceFormatException("Wrong format of data in " + source, e);
        } catch (InterruptedException e) {
            throw new ServiceException("Something terminate the calculation " + source, e);
        }
    }

    private DataTransferObject getDataFromSource(String source) throws SourceException {
        DataTransferObject vault = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(source));
            String commandLine = reader.readLine();
            String dataLine = reader.readLine();
            vault = new DataTransferObject(commandLine, dataLine);
        } catch (FileNotFoundException e) {
            throw new SourceNotFoundException("Source on path " + source + " doesn't exist", e);
        } catch (IOException e) {
            throw new SourceException("Unexpected exception during reading file " + source, e);
        } finally {
            closeReaderQuetly(reader);
        }
        return vault;
    }

    private void closeReaderQuetly(Reader reader) throws SourceException {
        try {
            if (reader != null){
                reader.close();
            }
        } catch (IOException e) {
            throw new SourceException("The problem during closing file");
        }
    }

    private double[] defineData(String line) throws SourceFormatException{
        String[] data = line.trim()
                            .split(" ");
        double[] result = new double[data.length];
        double temp = 0;
        try {
            for (int i = 0; i < data.length; i++) {
                String part = data[i];
                temp = Double.parseDouble(part);
                result[i] = temp;
            }
        } catch (NumberFormatException e){
            throw new SourceFormatException("The lexeme " + temp  + "hasn't the double format.");
        }
        return result;
    }

    private Command defineCommand(String line) throws SourceFormatException{
        Command command = null;
        try{
            int commandCode = Integer.parseInt(line.trim());
            command = CommandManager.getCommandByCode(commandCode);
            if (command == null) {
                throw new SourceFormatException("There no such command: " + line);
            }
        } catch (NumberFormatException e){
            throw new SourceFormatException("The command " + line + " isn't a number");
        }
        return command;
    }

    private void writeResultToFile(double result, String source) throws ServiceException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(source, true));
            writer.write("\n" + Double.toString(result));
        } catch (IOException e) {
            throw new WriteResultException("Can't write the result to file", e);
        } finally {
            closeWriterQuetly(writer);
        }
    }

    private void closeWriterQuetly(Writer reader) throws SourceException {
        try {
            if (reader != null){
                reader.close();
            }
        } catch (IOException e) {
            throw new SourceException("The problem during closing file");
        }
    }

    private String getPathAndCheckExistance(String source) throws WriteResultException {
        String pathAppendix = "\\out.dat";
        File file = new File(source);
        File parent = file.getParentFile();
        String path = parent.getAbsolutePath() + pathAppendix;
        File current = new File(path);
        if (!current.exists()){
            try {
                current.createNewFile();
            } catch (IOException e) {
                throw new WriteResultException("Problem with creating file on path " + path);
            }
        }
        return path;
    }

    private class DataTransferObject{
        private String commandCode;
        private String data;

        private DataTransferObject(String commandCode, String data) {
            this.commandCode = commandCode;
            this.data = data;
        }

        private String getCommandCode() {
            return commandCode;
        }

        private String getData() {
            return data;
        }

        @Override
        public String toString() {
            return "DataTransferObject{" +
                    "commandCode='" + commandCode + '\'' +
                    ", data='" + data + '\'' +
                    '}';
        }
    }
}
