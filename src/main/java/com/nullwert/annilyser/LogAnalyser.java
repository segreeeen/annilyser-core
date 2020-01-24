package com.nullwert.annilyser;

import com.nullwert.annilyser.parser.LogDetector;
import com.nullwert.annilyser.logsim.LogSimulator;
import com.nullwert.annilyser.domain.GameController;
import com.nullwert.annilyser.domain.listener.*;
import com.nullwert.annilyser.parser.Parser;
import com.nullwert.annilyser.parser.ParserController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogAnalyser {
    private ExecutorService parserExecutor = Executors.newSingleThreadExecutor();
    private ParserController parserController;
    private String fileIn;
    private Logger logger = LoggerFactory.getLogger(LogAnalyser.class);
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
        GameController.getInstance().registerGamestateChangeListener(l);
    }

    public void registerPhaseChangeListener(PhaseChangeListener l) {
        GameController.getInstance().registerPhaseChangeListener(l);
    }

    public void registerKillListener(KillListener l) {
        GameController.getInstance().registerKillListener(l);
    }

    public void registerNexusListener(NexusListener l) {
        GameController.getInstance().registerNexusListener(l);
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
            parserController = new ParserController(fileOut, realtime);
            parserController.parseRealtime(GameController.getInstance().getGame());
        }
    }

    public void stopParser() {
        parserController.stop();
    }

    public void setFileIn(String fileIn) {
        this.fileIn = fileIn;
    }

    public void setFileOut(String fileOut) {
        this.fileOut = fileOut;
    }
}
