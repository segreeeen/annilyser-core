package com.nullwert.annilyser.model;

import com.nullwert.annilyser.model.datastructures.Kill;
import com.nullwert.annilyser.model.datastructures.Nexus;
import com.nullwert.annilyser.model.datastructures.Player;
import com.nullwert.annilyser.model.listener.*;
import com.nullwert.annilyser.model.listener.events.*;
import com.nullwert.annilyser.parser.token.Token;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

final public class DataStorage {

    private ExecutorService executor = Executors.newCachedThreadPool();
    private Map<String, Player> players;
    private Map<Token.Team, Nexus> nexuses;
    private Token.GameState gameState;
    private Token.Phase phase;
    private List<GamestateChangeListener> GCListeners;
    private List<PhaseChangeListener> phaseListeners;
    private List<KillListener> killListeners;
    private List<DataListener> deathListeners;
    private List<NexusListener> nexusListeners;
    private AtomicInteger blueKills;
    private AtomicInteger greenKills;
    private AtomicInteger redKills;
    private AtomicInteger yellowKills;
    private AtomicInteger blueDeaths;
    private AtomicInteger greenDeaths;
    private AtomicInteger redDeaths;
    private AtomicInteger yellowDeaths;
    private AtomicInteger totalKills;
    private long startTime;
    private Semaphore timeLock = new Semaphore(1);
    private Semaphore localTimeLock = new Semaphore(1);
    private AtomicLong lastKillEventTime;
    private AtomicLong lastLocalKillEventTime;
    private boolean firstKill;

    private static class Loader {
        static final DataStorage INSTANCE = new DataStorage();
    }

    private DataStorage() {
        GCListeners = new ArrayList<>();
        phaseListeners = new ArrayList<>();
        killListeners = new ArrayList<>();
        deathListeners = new ArrayList<>();
        nexusListeners = new ArrayList<>();
        init();
    }

    private void init() {
        this.players = new ConcurrentHashMap<>();
        this.nexuses = new ConcurrentHashMap<>();
        this.nexuses.put(Token.Team.BLUE, new Nexus(Token.Team.BLUE));
        this.nexuses.put(Token.Team.GREEN, new Nexus(Token.Team.GREEN));
        this.nexuses.put(Token.Team.RED, new Nexus(Token.Team.RED));
        this.nexuses.put(Token.Team.YELLOW, new Nexus(Token.Team.YELLOW));
        this.blueKills = new AtomicInteger();
        this.greenKills = new AtomicInteger();
        this.redKills = new AtomicInteger();
        this.yellowKills = new AtomicInteger();
        this.blueDeaths = new AtomicInteger();
        this.greenDeaths = new AtomicInteger();
        this.redDeaths = new AtomicInteger();
        this.yellowDeaths = new AtomicInteger();
        this.totalKills = new AtomicInteger();
        this.gameState = Token.GameState.IDLE;
        this.phase = Token.Phase.ONE;
        this.startTime = -1;
        this.lastKillEventTime = new AtomicLong(0);
        this.lastLocalKillEventTime = new AtomicLong(0);
        this.firstKill = true;
    }

    public void reset() {
        init();
    }

    public static DataStorage getInstance() {
        return Loader.INSTANCE;
    }

    public Player getPlayer(String name) {
        return players.get(name);
    }

    public Player addPlayer(String name, Token.Class clazz, Token.Team team) {
        Player p = new Player(name, clazz, team);
        players.put(name, p);
        return p;
    }

    public void setGameState(Token.GameState gameState, String time) {
        if (this.gameState == Token.GameState.PHASE) {
            if (gameState != Token.GameState.PHASE) {
                if (this.startTime == -1) {
                    this.startTime = this.convertTimestampToMillis(time);
                }
                this.gameState = gameState;
                fireGamestateEvent(gameState, (convertTimestampToMillis(time) - this.startTime) / 1000);
            }
        } else {
            if (gameState == Token.GameState.STARTED) {
                reset();
                this.startTime = this.convertTimestampToMillis(time);

                System.out.println(this.startTime);
            } else if (gameState == Token.GameState.DONE) {
                this.startTime = -1;
            }
            this.gameState = gameState;
            fireGamestateEvent(gameState, (convertTimestampToMillis(time) - this.startTime) / 1000);
        }
    }

    public void setGameState(Token.GameState state, String time, Token.Team winTeam) {
        this.gameState = state;
        fireGamestateEvent(gameState, (convertTimestampToMillis(time) - this.startTime) / 1000, winTeam);
    }

    public void setPhase(Token.Phase phase, String time) {
        if (this.startTime == -1) {
            this.startTime = this.convertTimestampToMillis(time);
        }
        this.phase = phase;
        firePhaseChangeEvent(phase, (convertTimestampToMillis(time) - this.startTime) / 1000);
    }

    public void addKill(String playerName, Kill kill) {
        if (this.startTime == -1) {
            this.startTime = this.convertTimestampToMillis(kill.getTimestamp());
        }
        if (firstKill) {
            this.lastKillEventTime.set(this.startTime);
            this.lastLocalKillEventTime.set(System.currentTimeMillis());
            firstKill = false;
        }
        players.get(playerName).getKills().add(kill);
        kill.getVictim().getDeaths().add(kill);

        kill.setTimestampSeconds((convertTimestampToMillis(kill.getTimestamp()) - this.startTime) / 1000);
        switch (kill.getKiller().getTeam()) {
            case BLUE:
                blueKills.incrementAndGet();
                blueDeaths.get();
                break;
            case GREEN:
                greenKills.incrementAndGet();
                greenDeaths.get();
                break;
            case RED:
                redKills.incrementAndGet();
                redDeaths.get();
                break;
            case YELLOW:
                yellowKills.incrementAndGet();
                yellowDeaths.get();
                break;
            default:
                break;
        }

        switch (kill.getVictim().getTeam()) {
            case BLUE:
                blueKills.get();
                blueDeaths.incrementAndGet();
                break;
            case GREEN:
                greenKills.get();
                greenDeaths.incrementAndGet();
                break;
            case RED:
                redKills.get();
                redDeaths.incrementAndGet();
                break;
            case YELLOW:
                yellowKills.get();
                yellowDeaths.incrementAndGet();
                break;
            default:
                break;
        }

        addKill(kill, blueKills.get(), greenKills.get(), redKills.get(), yellowKills.get(), blueDeaths.get(), greenDeaths.get(), redDeaths.get(), yellowDeaths.get());
    }


    private void addKill(Kill k, int blueKills, int greenKills, int redKills, int yellowKills, int blueDeaths, int greenDeaths, int redDeaths, int yellowDeaths) {
        System.out.println("Somebody got killed.");
        KillEvent killEvent = new KillEvent(k, blueKills, greenKills, redKills, yellowKills, blueDeaths, greenDeaths, redDeaths, yellowDeaths);
        fireKillEvent(killEvent);
        updateLastActionTime(k.getTimestampSeconds());
        updateLocalLastActionTime();
    }

    private void updateLastActionTime(long actionTime) {
        try {
            timeLock.acquire();
            this.lastKillEventTime.set(actionTime);
            timeLock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getLastActionTime() {
        try {
            long time;
            timeLock.acquire();
            time = this.lastKillEventTime.get();
            timeLock.release();
            return time;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void updateLocalLastActionTime() {
        try {
            timeLock.acquire();
            this.lastLocalKillEventTime.set(System.currentTimeMillis());
            timeLock.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private long getLocalLastActionTime() {
        try {
            long time;
            timeLock.acquire();
            time = this.lastLocalKillEventTime.get();
            timeLock.release();
            return time;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void destroyNexus(String player, Token.Team team, String timestamp) {
        if (this.startTime == -1) {
            this.startTime = this.convertTimestampToMillis(timestamp);
        }
        Nexus n = this.nexuses.get(team);
        long timestampMillis = convertTimestampToMillis(timestamp);
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

    private long convertTimestampToMillis(String timeStamp) {
        final SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormatHour.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }


}
