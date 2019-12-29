package com.nullwert.annilyser;

import com.nullwert.annilyser.io.LogDetector;
import com.nullwert.annilyser.logsim.LogSimulator;
import com.nullwert.annilyser.model.DataStorage;
import com.nullwert.annilyser.model.listener.*;
import com.nullwert.annilyser.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogAnalyser {
    private ExecutorService parserExecutor = Executors.newSingleThreadExecutor();
    private Parser parser;
    private String fileIn;
    Logger logger = LoggerFactory.getLogger(LogAnalyser.class);
    private String fileOut;

    public LogAnalyser(String fileIn, String fileOut) {
        if(fileOut == null || "".equals(fileOut)) {
            LogDetector.getLogPath().ifPresent(path -> setFileOut(path.toString()));
        }

        this.fileIn = fileIn;
    }

    public static boolean isPathDetected() {
        return LogDetector.getLogPath().isPresent();
    }

    public static String getPath() {
        if (LogDetector.getLogPath().isPresent()) {
            return LogDetector.getLogPath().get().toString();
        } else {
            return "";
        }
    }

    public void registerGamestateChangeListener(GamestateChangeListener l) {
        DataStorage.getInstance().registerGamestateChangeListener(l);
    }

    public void registerPhaseChangeListener(PhaseChangeListener l) {
        DataStorage.getInstance().registerPhaseChangeListener(l);
    }

    public void registerKillListener(KillListener l) {
        DataStorage.getInstance().registerKillListener(l);
    }

    public void registerNexusListener(NexusListener l) {
        DataStorage.getInstance().registerNexusListener(l);
    }

    public void startParser(boolean realtime) {
        if (!realtime && !Files.exists(Paths.get(fileOut))) {
            logger.info("Destination file doesn't exist, but realtime is deactivated. Not starting.");
        }

        if (realtime) {
            if (this.fileIn == null || this.fileIn.isEmpty()) {
                logger.info("Deactivating realtime mode, since sourcefile is empty. Trying to read from destination instead.");
            } else {
                new LogSimulator().startSim(fileIn, fileOut);
            }
        }

        if(fileOut != null) {
            parser = new Parser(fileOut, realtime);
            parserExecutor.submit(parser);
        }
    }

    public void stopParser() {
        parser.stop();
        parserExecutor.shutdownNow();
    }

    public void setFileIn(String fileIn) {
        this.fileIn = fileIn;
    }

    public void setFileOut(String fileOut) {
        this.fileOut = fileOut;
    }
}
