package com.nullwert.annilyser.logsim;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LogSimulator {
    public static void main(String[] args) {
        LogSimulator sim = new LogSimulator();
        sim.startSim();
    }

    private void startSim() {
        Thread t = new Thread(new ReadWriter("C:\\Users\\Torin\\Documents\\git\\annilyser\\Annilyser\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\baselogs\\gamelog_6.txt"));
        t.start();
    }

    class ReadWriter implements Runnable {
        private final String file;
        private List<String> lines;
        private long delay = 0;
        private PrintWriter writer;

        public ReadWriter(String file) {
            this.file = file;
            try {
                this.lines = Files.readAllLines(Paths.get(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (Files.exists(Paths.get("C:\\Users\\Torin\\Documents\\git\\annilyser\\Annilyser\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt"))) {
                    Files.delete(Paths.get("C:\\Users\\Torin\\Documents\\git\\annilyser\\Annilyser\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt"));
                    Files.createFile(Paths.get("C:\\Users\\Torin\\Documents\\git\\annilyser\\Annilyser\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt"));
                }

                this.writer = new PrintWriter("C:\\Users\\Torin\\Documents\\git\\annilyser\\Annilyser\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt","UTF-8");
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
            if (!line.contains(":") || !line.contains("]") || !line.contains("[")) {
                return 0;
            }

            String substr = line.substring(0, 10);
            String[] arr = substr.split(":");
            arr[0] = arr[0].substring(1);
            arr[2] = arr[2].substring(0, 2);
            int h = Integer.parseInt(arr[0]), m = Integer.parseInt(arr[1]), s = Integer.parseInt(arr[2]);
            long stamp = (h * 3600 + m * 60 + s) * 1000;
            return stamp;
        }

    }
}
