package root.view.impl;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



class ConsoleHelper {

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static final String EXIT = "EXIT";

    protected static void writeMessage(String message){
        if (message != null) {
            System.out.println(message);
        }
    }
    protected static void writeMessage(double message){
        System.out.println(message);
    }

    protected static String readString(){
        String result = "";
        while (true){
            try{
                result = reader.readLine();
                if (result == null||result.length() == 0) throw new Exception();
                break;
            }catch (Exception e){
                final String ERROR = "The enter error. Try again.";
                writeMessage(ERROR);
            }
            finally {
                if (result.equalsIgnoreCase(EXIT)){
                    System.exit(0);
                }
            }
        }
        return result;
    }

    protected static void closeReaderQuetly() {
        try {
            reader.close();
        } catch (IOException e) {
           ConsoleHelper.writeMessage("Internal program problem");
        }
    }


}

