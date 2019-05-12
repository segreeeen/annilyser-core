package com.nullwert.annilyser;

import com.nullwert.annilyser.io.LogDetector;
import com.nullwert.annilyser.model.DataStorage;
import com.nullwert.annilyser.model.listener.*;
import com.nullwert.annilyser.parser.Parser;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogAnalyser {
    private ExecutorService parserExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private Parser parser;
    private String logPath;

    public LogAnalyser() {
        this.logPath = LogDetector.getLogPath().toString();
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
        if(logPath != null) {
            parser = new Parser("F:\\git\\annilyserV2\\annilyser-core\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt", realtime);
            parserExecutor.submit(parser);
        }
    }

    public void stopParser() {
        parser.stop();
        parserExecutor.shutdownNow();
    }
    
    public String getLogPath() {
        return logPath;
    }
    
    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

}
