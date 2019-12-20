package com.nullwert.annilyser.io;

import com.nullwert.annilyser.logsim.LogSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.Line;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class FileReader {
    private String path;
    private long lastFileSize = 0;
    private boolean firstExecution = true;
    private final BlockingQueue<String> rawLines = new ArrayBlockingQueue<String>(4096);
    private final BlockingQueue<String> doneLines = new ArrayBlockingQueue<String>(4096);
    private LineSplitterRunnable splitter;
    private ScheduledExecutorService schedExec = Executors.newSingleThreadScheduledExecutor();
    private ExecutorService exec = Executors.newCachedThreadPool();
    private boolean realtime;
    Logger logger = LoggerFactory.getLogger(LogSimulator.class);


    public FileReader(String path, boolean realtime) {
        this.path = path;
        this.realtime = realtime;
    }


    public BlockingQueue<String> getDoneLines() {
        return doneLines;
    }

    public void stop() {
        splitter.running = false;
        splitter.stop();
        schedExec.shutdownNow();
        exec.shutdownNow();
    }

    public void start() {
        if (realtime) {
            schedExec.scheduleAtFixedRate(new ReadLatestRunnable(), 1, 400, TimeUnit.MILLISECONDS);
            logger.info("Started reader thread.");
            splitter = new LineSplitterRunnable();
            exec.submit(splitter);
        } else {
            NonRealtimeReader nonRealtimeReader = new NonRealtimeReader();
            splitter = new LineSplitterRunnable();
            exec.execute(nonRealtimeReader);
            exec.execute(splitter);
        }
    }

    private class LineSplitterRunnable implements Runnable {

        boolean running = true;

        @Override
        public void run() {
            splitLines();
        }

        private void splitLines() {
            while (running) {
                try {
                    String lines = rawLines.take();
                    String[] arr = lines.split("\n");
                    int count = 0;
                    for (String s : arr) {
                        s = s.trim();
                        if (!s.equals("")) {
                            doneLines.add(s);
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        }

        private void stop() {
            running = false;
            Thread.currentThread().interrupt();
        }

    }

    private class ReadLatestRunnable implements Runnable {

        @Override
        public void run() {

            String lines = readLatest();
            if (lines != null) {
                rawLines.offer(lines);
            }
        }

        private String readLatest() {
            try {
                RandomAccessFile aFile = new RandomAccessFile(path, "r");
                FileChannel inChannel = aFile.getChannel();
                long fileSize = inChannel.size();
                long readLength = 0;
                if (firstExecution) {
                    lastFileSize = fileSize;
                    firstExecution = false;
                }
                if (lastFileSize < fileSize) {
                    readLength = fileSize - lastFileSize;
                } else {
                    return null;
                }
                ByteBuffer buffer = ByteBuffer.allocate((int) readLength);
                inChannel.position(lastFileSize);
                inChannel.read(buffer);
                buffer.flip();
                inChannel.close();
                aFile.close();
                lastFileSize = fileSize;
                String retval = new String(buffer.array(), "Cp1252");

                return retval;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class NonRealtimeReader implements Runnable {

        @Override
        public void run() {
            try {
                Stream<String> lines = Files.lines(Paths.get(path), Charset.forName("UTF-8"));
                lines.forEach(s -> {
                    rawLines.offer(s);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
