package com.nullwert.annilyser.parser;

import com.nullwert.annilyser.domain.GameController;
import com.nullwert.annilyser.domain.IGame;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParserController {
    private ExecutorService exec = Executors.newCachedThreadPool();
    private FileReader reader;
    private ParserRunnable parserRunnable;

    public ParserController(String path, boolean realtime) {
        reader = new FileReader(path, realtime);
    }

    public static void main(String[] args) {
        new ParserController("C:\\Users\\Torin\\Documents\\git\\annilyser\\annilyser-core\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt", false).parseRealtime(GameController.getInstance().newGame());
    }

    public void parseRealtime(IGame game) {
        reader.start();
        if (parserRunnable != null && parserRunnable.isRunning()) {
            parserRunnable.stop();
        }

        parserRunnable = new ParserRunnable(reader, GameController.getInstance());
        exec.submit(parserRunnable);
    }

    public static IGame parseLines(IGame game, List<String> originalLines) {
        Parser parser = new Parser(game);
        originalLines.forEach(l -> parser.parse(l));
        return game;
    }

    public void stop() {
        this.parserRunnable.stop();
    }
}
