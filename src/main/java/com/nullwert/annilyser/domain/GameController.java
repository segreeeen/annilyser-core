package com.nullwert.annilyser.domain;

import com.nullwert.annilyser.domain.listener.*;
import com.nullwert.annilyser.parser.Line;
import com.nullwert.annilyser.parser.Parser;
import com.nullwert.annilyser.parser.ParserController;
import com.nullwert.annilyser.parser.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

final public class GameController implements IGame {
    private Game currentGame;
    private List<GamestateChangeListener> GCListeners;
    private List<PhaseChangeListener> phaseListeners;
    private List<KillListener> killListeners;
    private List<NexusListener> nexusListeners;

    Logger logger = LoggerFactory.getLogger(Parser.class);

    public IGame getGame() {
        return currentGame;
    }

    private static class Loader {
        static final GameController INSTANCE = new GameController();
    }

    private GameController() {
        GCListeners = new ArrayList<>();
        phaseListeners = new ArrayList<>();
        killListeners = new ArrayList<>();
        nexusListeners = new ArrayList<>();
        newGame();
    }

    public Game newGame() {
        this.currentGame = new Game();
        this.currentGame.setGCListeners(GCListeners);
        this.currentGame.setPhaseListeners(phaseListeners);
        this.currentGame.setKillListeners(killListeners);
        this.currentGame.setNexusListeners(nexusListeners);
        return currentGame;
    }

    public void startGame() {

    }

    public void loadGame() {
        this.currentGame = new Game();
    }

    public void saveGame() {
        this.currentGame = new Game();
    }

    public Game getGameInTimespan(long from, long until) {
        List<String> lines = this.currentGame.getLines().stream().filter(l -> l.getTime() >= from && l.getTime() <= until).map(Line::getOriginalMessage).collect(Collectors.toList());
        Game game = new Game();
        ParserController.parseLines(game, lines);
        return game;
    }

    public static GameController getInstance() {
        return Loader.INSTANCE;
    }

    public Player addPlayer(String name, Token.Class clazz, Token.Team team) {
        return this.currentGame.addPlayer(name, clazz, team);
    }

    public void setGameState(Token.GameState gameState, String time) {
        this.currentGame.setGameState(gameState, time);
    }

    public void setGameState(Token.GameState state, String time, Token.Team winTeam) {
        this.currentGame.setGameState(state, time, winTeam);
    }

    public void setPhase(Token.Phase phase, String time) {
        this.currentGame.setPhase(phase, time);
    }

    public void addKill(Kill kill) {
        this.currentGame.addKill(kill);
    }

    @Override
    public void addLine(Line line) {
        this.currentGame.addLine(line);
    }

    public void destroyNexus(String player, Token.Team team, String timestamp) {
        this.currentGame.destroyNexus(player, team, timestamp);
    }

    public void registerGamestateChangeListener (GamestateChangeListener l) {
        this.GCListeners.add(l);
        this.currentGame.registerGamestateChangeListener(l);
    }

    public void registerPhaseChangeListener(PhaseChangeListener l) {
        this.phaseListeners.add(l);
        this.currentGame.registerPhaseChangeListener(l);
    }

    public void registerKillListener(KillListener l) {
        this.killListeners.add(l);
        this.currentGame.registerKillListener(l);
    }

    public void registerNexusListener(NexusListener l) {
        this.nexusListeners.add(l);
        this.currentGame.registerNexusListener(l);
    }

}
