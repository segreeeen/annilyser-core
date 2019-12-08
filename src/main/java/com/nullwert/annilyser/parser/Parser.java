package com.nullwert.annilyser.parser;

import com.nullwert.annilyser.io.FileReader;
import com.nullwert.annilyser.model.DataStorage;
import com.nullwert.annilyser.model.datastructures.Kill;
import com.nullwert.annilyser.model.datastructures.Player;
import com.nullwert.annilyser.model.listener.GamestateChangeListener;
import com.nullwert.annilyser.model.listener.KillListener;
import com.nullwert.annilyser.model.listener.NexusListener;
import com.nullwert.annilyser.model.listener.PhaseChangeListener;
import com.nullwert.annilyser.parser.token.Token;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nullwert.annilyser.parser.ParserRegEx.*;

public class Parser implements Runnable {
    private final BlockingQueue<String> lines;
    private FileReader reader;
    private ParserRegEx regEx;
    private boolean running = true;
    private AtomicInteger nexusCount = new AtomicInteger();
    private String nexusTeam;
    private long clockCount = -1;
    private AtomicInteger dayCount = new AtomicInteger(1);


    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        Parser p = new Parser("F:\\git\\annilyserV2\\annilyser-core\\src\\main\\java\\com\\nullwert\\annilyser\\logsim\\testlog.txt", false);
        exec.submit(p);
    }

    /**
     * @param file
     * @param realtime filereader reads log every x ms when true, reads log line by line when false
     */
    public Parser(String file, boolean realtime) {
        this.regEx = new ParserRegEx();
        this.reader = new FileReader(file, realtime);
        this.lines = this.reader.getDoneLines();
    }

    public void run() {
        System.out.println("Started parser.");
        this.reader.start();
        System.out.println("Started reader.");
        while (running) {
            String s = null;
            try {
                s = this.lines.take();
                parse(s);
            } catch (InterruptedException e) {
                System.out.println("Shutting down parser.");
            }
        }
    }

    public void stop() {
        this.running = false;
        this.reader.stop();
        Thread.currentThread().interrupt();
    }

    private void parse(String s) {
        Matcher generalMessageMatcher = regEx.GENERAL.matcher(s);
        boolean validMessage = generalMessageMatcher.matches();
        if (validMessage) {
            String vMsg = generalMessageMatcher.group(ParserRegEx.GENERAL_MESSAGE);
            String time = generalMessageMatcher.group(ParserRegEx.GENERAL_TIME);
            StringBuilder sb = new StringBuilder();
            time = sb.append(getDay(time)).append(" ").append(time).toString();
            if (parseDeath(vMsg, time)) {
                return;
            } else if (parseGameStatus(vMsg, time)) {
                return;
            } else if (parseNexusDestroyed(vMsg, time)) {
                return;
            }
        }
    }

    private String getDay(String time) {

        if (asMilliSeconds(time) >= this.clockCount) {
            this.clockCount = asMilliSeconds(time);
        } else {
            this.dayCount.incrementAndGet();
            this.clockCount = -1;
        }

        return String.format("%02d", this.dayCount.get());
    }

    private long asMilliSeconds(String line) {
        String[] arr = line.split(":");
        int h = Integer.parseInt(arr[0]), m = Integer.parseInt(arr[1]), s = Integer.parseInt(arr[2]);
        return (long) ((h * 3600 + m * 60 + s) * 1000);
    }

    private boolean parseNexusDestroyed(String vMsg, String time) {

        String team = retrieveGroupResults(regEx.NEXUS_DESTROYED_LINE1, vMsg, ParserRegEx.NEXUS_DESTROYED_TEAM).get(NEXUS_DESTROYED_TEAM);
        boolean line2 = regEx.NEXUS_DESTROYED_LINE2.matcher(vMsg).matches();
        String destroyer = retrieveGroupResults(regEx.NEXUS_DESTROYED_LINE3, vMsg, ParserRegEx.NEXUS_DESTROYED_BY).get(NEXUS_DESTROYED_BY);
        if (team != null) {
            if (nexusCount.get() == 0) {
                nexusTeam = team;
                nexusCount.incrementAndGet();
            }
        } else if (line2) {
            if (nexusCount.get() == 1) {
                nexusCount.incrementAndGet();
            }
        } else if (destroyer != null) {
            if (nexusCount.get() == 2) {
                nexusCount.set(0);
                DataStorage.getInstance().destroyNexus(destroyer, Token.Team.getByString(nexusTeam), time);
            }
        }

        return false;
    }

    private boolean parseGameStatus(String vMsg, String time) {
        String win = retrieveGroupResults(regEx.WIN, vMsg, WIN_TEAM).get(WIN_TEAM);
        if (win != null) {
            Token.GameState state = Token.GameState.getByStateString(win);
            Token.Team team = Token.Team.getByString(win);
            DataStorage.getInstance().setGameState(state, time, team);
            return true;
        }

        String mode = retrieveGroupResults(regEx.GAMESTART, vMsg, ParserRegEx.GAMESTART_MODE).get(GAMESTART_MODE);
        if (mode != null) {
            Token.GameState state = Token.GameState.getByStateString(mode);
            if (state == Token.GameState.PHASE) {
                DataStorage.getInstance().setGameState(state, time);
                String phase = retrieveGroupResults(regEx.GAMESTART, vMsg, ParserRegEx.GAMESTART_PHASENUMBER).get(GAMESTART_PHASENUMBER);
                if (phase != null) {
                    Token.Phase phaseToken = Token.Phase.getByPhaseString(phase);
                    DataStorage.getInstance().setPhase(phaseToken, time);
                    return true;
                }
            } else if (state == Token.GameState.STARTED) {
                DataStorage.getInstance().setGameState(state, time);
            }

        }
        return false;
    }

    private boolean parseDeath(String vMsg, String time) {
        Map<String, String> kill = retrieveGroupResults(regEx.KILL, vMsg, KILL_KILLER_NAME, KILL_KILLER_TEAM, KILL_KILLER_CLASS,
                KILL_VICTIM_NAME, KILL_DEATH_KIND, KILL_VICTIM_TEAM, KILL_VICTIM_CLASS, KILL_ATTACKROLE, KILL_ATTACKED_NEXUS, KILL_HONOUR_OF);
        if (!kill.isEmpty()) {
            String killer_name = kill.get(KILL_KILLER_NAME);
            Token.Team killer_team = Token.Team.getByColorString(kill.get(KILL_KILLER_TEAM));
            String killer_class = kill.get(KILL_KILLER_CLASS);
            Token.Class KClass = Token.Class.getAbbrString(killer_class);
            Token.Deathkind deathKind = Token.Deathkind.getByKindString(kill.get(KILL_DEATH_KIND));
            String victim_name = kill.get(KILL_VICTIM_NAME);
            Token.Team victim_team = Token.Team.getByColorString(kill.get(KILL_VICTIM_TEAM));
            String victim_class = kill.get(KILL_VICTIM_CLASS);
            Token.Class VClass = Token.Class.getAbbrString(victim_class);

            Player killer = DataStorage.getInstance().addPlayer(killer_name, KClass, killer_team);
            Player victim = DataStorage.getInstance().addPlayer(victim_name, VClass, victim_team);

            if (kill.size() == 9) {
                Token.Attackmode attackMode = Token.Attackmode.getByModeString(kill.get(KILL_ATTACKROLE));
                Token.Team attacked_nexus = Token.Team.getByString(kill.get(KILL_ATTACKED_NEXUS));
                DataStorage.getInstance().addKill(killer.getName(), new Kill(false, victim, killer, time, attackMode, deathKind, vMsg));
            } else if (kill.size() == 7) {
                DataStorage.getInstance().addKill(killer.getName(), new Kill(true, victim, killer, time, Token.Attackmode.UNKNOWN, deathKind, vMsg));
            } else if (kill.size() == 6) {
                DataStorage.getInstance().addKill(killer.getName(), new Kill(true, victim, killer, time, Token.Attackmode.UNKNOWN, deathKind, vMsg));
            }

            return true;
        }
        return false;
    }

    private Player getPlayer(String name, Token.Team team, Token.Class clazz, Player p) {
        if (p == null) {
            p = DataStorage.getInstance().addPlayer(name, clazz, team);
        } else if (p.getTeam() == Token.Team.UNKNOWN) {
            p.setTeam(team);
            p.setClass(clazz);
        }
        return p;
    }

    private Map<String, String> retrieveGroupResults(Pattern p, String message, String... group) {
        Matcher m = p.matcher(message);
        boolean validMessage = m.matches();
        if (validMessage) {
            Map<String, String> map = new HashMap<>();
            for (String s : group) {
                String g = m.group(s);
                if (g != null) {
                    map.put(s, g);
                }
            }
            return map;
        }
        return new HashMap<>();
    }

}
