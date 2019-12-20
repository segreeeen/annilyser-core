package com.nullwert.annilyser.logsim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LogSimulator {

    ExecutorService executorService;
    ReadWriter readWriter;

    Logger logger = LoggerFactory.getLogger(LogSimulator.class);

    public LogSimulator() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public void startSim(String fileIn, String fileOut) {
        this.readWriter = new ReadWriter(fileIn, fileOut);
        this.executorService.execute(readWriter);
    }

    public void stopSim() {
        this.readWriter.stop();
        try {
            this.executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            logger.error("Something happened: \n",e);
        }
    }

    class ReadWriter implements Runnable {
        private final String fileIn;
        private List<String> lines;
        private long delay = 0;
        private PrintWriter writer;
        private boolean stop;

        public ReadWriter(String fileIn, String fileOut) {
            if (fileOut == null || "".equals(fileOut)) {
                fileOut = "./SimuationLog.log";
            }

            if (fileOut.equals(fileIn)) {
                logger.error("source and destination are identical, cannot read and write from/to same source!");
            }

            this.fileIn = fileIn;
            try {
                this.lines = Files.readAllLines(Paths.get(fileIn)).stream().filter(l -> l.startsWith("[")).collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (Files.exists(Paths.get(fileOut))) {
                    Files.delete(Paths.get(fileOut));
                    Files.createFile(Paths.get(fileOut));
                }

                this.writer = new PrintWriter(fileOut,"Cp1252");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                delay = 0;
                for (int i = 0; i < lines.size(); i++) {
                    if (stop) {
                        break;
                    }
                    Thread.sleep(Math.abs(delay/3));
                    writer.println(lines.get(i));
                    writer.flush();
                    if (i+1<lines.size()) {
                        delay = getTimeDifference(lines.get(i), lines.get(i + 1));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private long getTimeDifference(String first, String second) {
            return getTimestampAsMilliSeconds(second) - getTimestampAsMilliSeconds(first);
        }

        private long getTimestampAsMilliSeconds(String line) {
            try {
                String substr = line.substring(0, 10);
                String[] arr = substr.split(":");
                arr[0] = arr[0].substring(1);
                arr[2] = arr[2].substring(0, 2);
                int h = Integer.parseInt(arr[0]), m = Integer.parseInt(arr[1]), s = Integer.parseInt(arr[2]);
                long stamp = (h * 3600 + m * 60 + s) * 1000;
                return stamp;
            } catch (Exception e) {
                return 0;
            }
        }

        public void stop() {
            this.stop = true;
        }
    }
}
