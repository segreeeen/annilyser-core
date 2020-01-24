package com.nullwert.annilyser.parser;

import com.nullwert.annilyser.domain.*;
import com.nullwert.annilyser.domain.listener.LineListener;
import com.nullwert.annilyser.domain.listener.events.LineEvent;
import com.nullwert.annilyser.parser.token.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.nullwert.annilyser.parser.ParserRegEx.*;

public class Parser {

    private final IGame game;
    private ParserRegEx regEx;

    private AtomicInteger nexusCount = new AtomicInteger();
    private String nexusTeam;
    private long clockCount = -1;
    private AtomicInteger dayCount = new AtomicInteger(1);

    private Logger logger = LoggerFactory.getLogger(Parser.class);

    public Parser(IGame game) {
        this.game = game;
        this.regEx = new ParserRegEx();
    }

    void parse(String s) {
        Matcher generalMessageMatcher = regEx.GENERAL.matcher(s);
        boolean validMessage = generalMessageMatcher.matches();
        if (validMessage) {
            String vMsg = generalMessageMatcher.group(ParserRegEx.GENERAL_MESSAGE);
            String time = generalMessageMatcher.group(ParserRegEx.GENERAL_TIME);

            StringBuilder sb = new StringBuilder();
            time = sb.append(getDay(time)).append(" ").append(time).toString();
            Line line = new Line(TimeUtils.convertTimestampToMillis(time), vMsg, s);
            this.game.addLine(line);
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
                game.destroyNexus(destroyer, Token.Team.getByString(nexusTeam), time);
            }
        }

        return false;
    }

    private boolean parseGameStatus(String vMsg, String time) {
        String win = retrieveGroupResults(regEx.WIN, vMsg, WIN_TEAM).get(WIN_TEAM);
        if (win != null) {
            Token.GameState state = Token.GameState.getByStateString(win);
            Token.Team team = Token.Team.getByString(win);
            GameController.getInstance().setGameState(state, time, team);
            return true;
        }

        String mode = retrieveGroupResults(regEx.GAMESTART, vMsg, ParserRegEx.GAMESTART_MODE).get(GAMESTART_MODE);
        if (mode != null) {
            Token.GameState state = Token.GameState.getByStateString(mode);
            if (state == Token.GameState.PHASE) {
                GameController.getInstance().setGameState(state, time);
                String phase = retrieveGroupResults(regEx.GAMESTART, vMsg, ParserRegEx.GAMESTART_PHASENUMBER).get(GAMESTART_PHASENUMBER);
                if (phase != null) {
                    Token.Phase phaseToken = Token.Phase.getByPhaseString(phase);
                    GameController.getInstance().setPhase(phaseToken, time);
                    return true;
                }
            } else if (state == Token.GameState.STARTED) {
                GameController.getInstance().setGameState(state, time);
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

            Player killer = GameController.getInstance().addPlayer(killer_name, KClass, killer_team);
            Player victim = GameController.getInstance().addPlayer(victim_name, VClass, victim_team);

            if (kill.size() == 9) {
                Token.Attackmode attackMode = Token.Attackmode.getByModeString(kill.get(KILL_ATTACKROLE));
                Token.Team attacked_nexus = Token.Team.getByString(kill.get(KILL_ATTACKED_NEXUS));
                GameController.getInstance().addKill(new Kill(false, victim, killer, time, attackMode, deathKind, vMsg));
            } else if (kill.size() == 7) {
                GameController.getInstance().addKill(new Kill(true, victim, killer, time, Token.Attackmode.UNKNOWN, deathKind, vMsg));
            } else if (kill.size() == 6) {
                GameController.getInstance().addKill(new Kill(true, victim, killer, time, Token.Attackmode.UNKNOWN, deathKind, vMsg));
            }

            return true;
        }
        return false;
    }

    private Player getPlayer(String name, Token.Team team, Token.Class clazz, Player p) {
        if (p == null) {
            p = GameController.getInstance().addPlayer(name, clazz, team);
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
