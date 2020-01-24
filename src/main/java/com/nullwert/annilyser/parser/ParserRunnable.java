package com.nullwert.annilyser.parser;

import com.nullwert.annilyser.domain.Game;
import com.nullwert.annilyser.domain.GameController;
import com.nullwert.annilyser.domain.IGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParserRunnable implements Runnable {
    private final BlockingQueue<String> lines;
    private final Parser parser;
    Logger logger = LoggerFactory.getLogger(Parser.class);

    private boolean running = true;


    public ParserRunnable(ILineProducer lineProducer, IGame game) {
        this.lines = lineProducer.getDoneLines();
        this.parser = new Parser(game);
    }

    public void run() {
        logger.info("Started parser");
        while (running) {
            String s = "";
            try {
                s = this.lines.take();
                this.parser.parse(s);
            } catch (InterruptedException e) {
                logger.info("Shutting down parser.");
            }
        }
    }

    public void stop() {
        this.running = false;
        Thread.currentThread().interrupt();
    }

    public boolean isRunning() {
        return running;
    }
}
