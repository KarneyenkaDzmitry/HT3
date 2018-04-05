package application;

import utils.Helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AppClass {
    private Helper helper = new Helper();
    private String commandFile;
    private String statistics;
    private int passed, failed;
    private long totalTime;
    private ArrayList<String> list = new ArrayList<>();

    public AppClass(String source, String target) {
        commandFile = source;
        statistics = target;
    }


    public void run() throws IOException {
        if (checkFiles(commandFile, statistics)) {
            long start, finish;
            boolean success;
            ArrayList<String> commands = helper.readerLines(commandFile);
            if ((commands != null) && (commands.size() != 0)) {
                for (String line : commands) {
                    start = System.currentTimeMillis();
                    success = helper.execute(helper.parser(line));
                    finish = System.currentTimeMillis();
                    addStatistic(success, line, finish - start);
                }
            }
            printStatistic(statistics);
        } else {
            throw new IOException();
        }

    }

    private boolean checkFiles(String commandFile, String statistics) {
        return true;
    }

    private void addStatistic(Boolean success, String instruction, Long time) {
        StringBuilder builder = new StringBuilder();
        if (success) {
            passed++;
            builder.append("+");
        } else {
            failed++;
            builder.append("!");
        }
        totalTime += time;
        builder.append(" [" + instruction + "] " + (double) time / 1000.0);
        list.add(builder.toString());
    }

    private String printStatistic() {
        StringBuilder builder = new StringBuilder();
        for (String line : list) {
            builder.append(line + "\n");
        }
        int totalTests = failed + passed;
        double averageTime = (totalTime / (failed + passed)) / 1000.0;
        builder.append("Total tests: " + totalTests + "\n");
        builder.append("Passed/Failed: " + passed + "/" + failed + "\n");
        builder.append("Total time: " + totalTime / 1000.0 + "\n");
        builder.append("Average time: " + averageTime + "\n");
        return builder.toString();
    }

    private void printStatistic(String fileName) {
        Path path = Paths.get(fileName);
        File file = new File(fileName);
        try {
            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(printStatistic());
            writer.close();
            output.close();
        } catch (IOException e) {
            System.out.println("Something wrong with output file. So the results will print on console:");
            System.out.println(printStatistic());
            e.printStackTrace();
        }
    }
}
