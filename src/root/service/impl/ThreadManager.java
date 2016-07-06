package root.service.impl;

import root.command.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadManager {

    private static int optimalTheadsCount = Runtime.getRuntime().availableProcessors() + 1;
    private double[] data;
    private Command command;
    private Command aggregateCommand;
    private volatile double[] tempResult;
    private List<CommandThread> threads;

    public ThreadManager(Command command, double[] data, Command aggregateCommand) {
        this.data = data;
        this.command = command;
        this.aggregateCommand = aggregateCommand;

    }

    public double calculate() throws InterruptedException {
        dispathThreads();
        return getResult();
    }

    private void dispathThreads(){

        threads = new ArrayList<CommandThread>();
        int length = data.length;
        int share = defineShareOfEachThread(length);
        int maxThread = length/share;
        tempResult = new double[maxThread];
        int counterThread = 0;
        for (int i = 0; i < length; i = i + share) {
            int end= i + share;
            if ((length - end)/share < 1){
                end = length;
            }
            threads.add(new CommandThread(i, end, counterThread));
            counterThread++;
            if (counterThread > maxThread - 1) return;
        }
    }

    private double getResult() throws InterruptedException {
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).join();
        }
        if (tempResult.length/2 > 2){
            data = tempResult;
            command = aggregateCommand;
            return calculate();
        }
        return aggregateCommand.execute(tempResult);
    }

    private int defineShareOfEachThread(int length){
        int defaultThreads = (length/2 == 0) ? 1 : length/2;
        if ((defaultThreads == 1)||(defaultThreads < optimalTheadsCount)){
            return length/defaultThreads;
        }
        int share = length/ optimalTheadsCount;
        return share;
    }

    @Override
    public String toString() {
        return "ThreadManager{" +
                "data=" + Arrays.toString(data) +
                ", command=" + command +
                '}';
    }

    private class CommandThread extends Thread{
        private int counter;
        private int begin;
        private int end;

        public CommandThread(int begin, int end, int counter) {
            super();
            this.end = end;
            this.begin = begin;
            this.counter = counter;
            this.start();
        }

        @Override
        public void run() {
            double result = command.execute(Arrays.copyOfRange(data, begin, end));
            tempResult[counter] = result;
        }
    }
}