package com.nullwert.annilyser.domain;

import com.nullwert.annilyser.domain.listener.GamestateChangeListener;
import com.nullwert.annilyser.domain.listener.KillListener;
import com.nullwert.annilyser.domain.listener.NexusListener;
import com.nullwert.annilyser.domain.listener.PhaseChangeListener;
import com.nullwert.annilyser.domain.listener.events.GamestateEvent;
import com.nullwert.annilyser.domain.listener.events.KillEvent;
import com.nullwert.annilyser.domain.listener.events.NexusEvent;
import com.nullwert.annilyser.domain.listener.events.PhaseChangeEvent;
import com.nullwert.annilyser.parser.Line;
import com.nullwert.annilyser.parser.Parser;
import com.nullwert.annilyser.parser.TimeUtils;
import com.nullwert.annilyser.parser.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Game implements IGame{
    private Map<String, Player> players;
    private Map<Token.Team, Nexus> nexuses;
    private Token.GameState gameState;
    private Token.Phase phase;
    private long startTime;
    private Semaphore timeLock = new Semaphore(1);
    private AtomicLong lastKillEventTime;
    private AtomicLong lastLocalKillEventTime;
    private boolean firstKill;
    private Map<Token.Team, Team> teams;
    private Map<Token.Class, Klasse> classes;
    private List<Kill> totalKills;

    public List<Line> getLines() {
        return lines;
    }

    private List<Line> lines;

    private List<GamestateChangeListener> GCListeners;
    private List<PhaseChangeListener> phaseListeners;
    private List<KillListener> killListeners;
    private List<NexusListener> nexusListeners;

    Logger logger = LoggerFactory.getLogger(Parser.class);

    public Game() {
        teams = new HashMap<>();
        classes = new HashMap<>();
        init();
    }

    private void init() {
        this.GCListeners = new ArrayList<>();
        this.phaseListeners = new ArrayList<>();
        this.killListeners = new ArrayList<>();
        this.nexusListeners = new ArrayList<>();
        this.players = new ConcurrentHashMap<>();
        this.nexuses = new ConcurrentHashMap<>();
        this.lines = new ArrayList<>();
        this.nexuses.put(Token.Team.BLUE, new Nexus(Token.Team.BLUE));
        this.nexuses.put(Token.Team.GREEN, new Nexus(Token.Team.GREEN));
        this.nexuses.put(Token.Team.RED, new Nexus(Token.Team.RED));
        this.nexuses.put(Token.Team.YELLOW, new Nexus(Token.Team.YELLOW));
        this.totalKills = new ArrayList<>();
        this.gameState = Token.GameState.IDLE;
        this.phase = Token.Phase.ONE;
        this.startTime = -1;
        this.lastKillEventTime = new AtomicLong(0);
        this.lastLocalKillEventTime = new AtomicLong(0);
        this.firstKill = true;
        teams.clear();
        initTeams();
    }

    private void initTeams() {
        teams.put(Token.Team.BLUE, new Team(Token.Team.BLUE));
        teams.put(Token.Team.GREEN, new Team(Token.Team.GREEN));
        teams.put(Token.Team.RED, new Team(Token.Team.RED));
        teams.put(Token.Team.YELLOW, new Team(Token.Team.YELLOW));
    }



    public Player addPlayer(String name, Token.Class clazz, Token.Team team) {
        if (players.get(name) == null) {
            Player p = new Player(name, clazz, team);
            teams.get(team).addPlayer(p);
            classes.computeIfAbsent(clazz, Klasse::new).addPlayer(p);
            players.put(name, p);
            return p;
        } else {
            return players.get(name);
        }
    }

    public void setGameState(Token.GameState gameState, String time) {
        if (this.gameState == Token.GameState.PHASE) {
            if (gameState != Token.GameState.PHASE) {
                if (this.startTime == -1) {
                    this.startTime = TimeUtils.convertTimestampToMillis(time);
                }
                this.gameState = gameState;
                fireGamestateEvent(gameState, (TimeUtils.convertTimestampToMillis(time) - this.startTime) / 1000);
            }
        } else {
            if (gameState == Token.GameState.STARTED) {
                this.startTime = TimeUtils.convertTimestampToMillis(time);

                logger.info("The game has started at: {} ", time);
            } else if (gameState == Token.GameState.DONE) {
                this.startTime = -1;
            }
            this.gameState = gameState;
            fireGamestateEvent(gameState, (TimeUtils.convertTimestampToMillis(time) - this.startTime) / 1000);
        }
    }

    public void setGameState(Token.GameState state, String time, Token.Team winTeam) {
        this.gameState = state;
        fireGamestateEvent(gameState, (TimeUtils.convertTimestampToMillis(time) - this.startTime) / 1000, winTeam);
    }

    public void setPhase(Token.Phase phase, String time) {
        if (this.startTime == -1) {
            this.startTime = TimeUtils.convertTimestampToMillis(time);
        }
        this.phase = phase;
        firePhaseChangeEvent(phase, (TimeUtils.convertTimestampToMillis(time) - this.startTime) / 1000);
    }

    public void addKill(Kill kill) {
        this.totalKills.add(kill);
        kill.getKiller().addKill(kill);
        kill.getVictim().addKill(kill);
        teams.get(kill.getKiller().getTeam()).addKill(kill);
        teams.get(kill.getVictim().getTeam()).addKill(kill);
        classes.get(kill.getKiller().getClazz()).addKill(kill);
        classes.get(kill.getVictim().getClazz()).addKill(kill);

        if (this.startTime == -1) {
            this.startTime = TimeUtils.convertTimestampToMillis(kill.getTimestamp());
        }
        if (firstKill) {
            this.lastKillEventTime.set(this.startTime);
            this.lastLocalKillEventTime.set(System.currentTimeMillis());
            firstKill = false;
        }

        kill.setTimestampSeconds((TimeUtils.convertTimestampToMillis(kill.getTimestamp()) - this.startTime) / 1000);

        logger.info("Killer: {}, Killerteam: {}, Killerclass: {}", kill.getKiller().getName(), kill.getKiller().getTeam(), kill.getKiller().getClazz());
        logger.info("Victim: {}, Victimteam: {}, Victimclass: {}", kill.getVictim().getName(), kill.getVictim().getTeam(), kill.getVictim().getClazz());
        Long before = System.currentTimeMillis();
        KillEvent killEvent = new KillEvent(kill,
                classes,
                players.values().stream().map(Player::getCopy).collect(Collectors.toList()),
                teams.values().stream().map(Team::getCopy).collect(Collectors.toList()),
                new ArrayList<>(totalKills));
        Long delta = System.currentTimeMillis() - before;
        fireKillEvent(killEvent);
        updateLastActionTime(kill.getTimestampSeconds());
        updateLocalLastActionTime();
    }

    @Override
    public void addLine(Line line) {
        this.lines.add(line);
    }

    private void updateLastActionTime(long actionTime) {
        try {
            timeLock.acquire();
            this.lastKillEventTime.set(actionTime);
            timeLock.release();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    private void updateLocalLastActionTime() {
        try {
            timeLock.acquire();
            this.lastLocalKillEventTime.set(System.currentTimeMillis());
            timeLock.release();
        } catch (InterruptedException e) {
            logger.error("", e);
        }
    }

    public void destroyNexus(String player, Token.Team team, String timestamp) {
        if (this.startTime == -1) {
            this.startTime = TimeUtils.convertTimestampToMillis(timestamp);
        }
        Nexus n = this.nexuses.get(team);
        long timestampMillis = TimeUtils.convertTimestampToMillis(timestamp);
        n.setTimestampSeconds((timestampMillis - this.startTime) / 1000);
        Player p;
        if (players.get(player) == null) {
            p = new Player(player, Token.Class.UNKNOWN, Token.Team.UNKNOWN);
        } else {
            p = players.get(player);
        }
        n.destroyNexus(p);
        fireNexusEvent(n);
    }



    public void registerGamestateChangeListener (GamestateChangeListener l) {
        this.GCListeners.add(l);
    }

    public void registerPhaseChangeListener(PhaseChangeListener l) {
        this.phaseListeners.add(l);
    }

    public void registerKillListener(KillListener l) {
        this.killListeners.add(l);
    }

    public void registerNexusListener(NexusListener l) {
        this.nexusListeners.add(l);
    }

    private void fireGamestateEvent(Token.GameState gs, long timestampSeconds) {
        this.GCListeners.stream().forEach(d -> d.fireChangeEvent(new GamestateEvent(gs, timestampSeconds)));
    }

    private void fireGamestateEvent(Token.GameState gameState, long l, Token.Team winTeam) {
        this.GCListeners.stream().forEach(d -> d.fireChangeEvent(new GamestateEvent(gameState, l, winTeam)));
    }

    private void firePhaseChangeEvent(Token.Phase p, long timestampSeconds) {
        this.phaseListeners.stream().forEach(d -> d.fireChangeEvent(new PhaseChangeEvent(p, timestampSeconds)));
    }

    private void fireKillEvent(KillEvent killEvent) {
        this.killListeners.stream().forEach(d -> d.fireChangeEvent(killEvent));
    }

    private void fireNexusEvent(Nexus n) {
        this.nexusListeners.stream().forEach(d -> d.fireChangeEvent(new NexusEvent(n)));
    }

    public void setGCListeners(List<GamestateChangeListener> GCListeners) {
        this.GCListeners = GCListeners;
    }

    public void setPhaseListeners(List<PhaseChangeListener> phaseListeners) {
        this.phaseListeners = phaseListeners;
    }

    public void setKillListeners(List<KillListener> killListeners) {
        this.killListeners = killListeners;
    }

    public void setNexusListeners(List<NexusListener> nexusListeners) {
        this.nexusListeners = nexusListeners;
    }
}
