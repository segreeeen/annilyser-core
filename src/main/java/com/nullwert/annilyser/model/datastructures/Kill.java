package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

public class Kill {
    private final Player killer;
    private final Player victim;
    private String timestamp;
    private long timestampSeconds;
    private final boolean honourable;
    private final Token.Attackmode attackmode;
    private final Token.Deathkind deathkind;


    private final String logLine;

    public Kill(boolean honourable, Player victim,  Player killer, String timestamp, Token.Attackmode attackmode, Token.Deathkind deathkind, String logLine) {
        this.victim = victim;
        this.timestamp = timestamp;
        this.attackmode = attackmode;
        this.deathkind = deathkind;
        this.honourable = honourable;
        this.killer = killer;
        this.logLine = logLine;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Player getVictim() {
        return victim;
    }

    public Player getKiller() {
        return killer;
    }

    public long getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(long timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public boolean isHonourable() {
        return honourable;
    }

    public Token.Attackmode getAttackmode() {
        return attackmode;
    }

    public Token.Deathkind getDeathkind() {
        return deathkind;
    }

    public String getLogLine() {
        return logLine;
    }

    @Override
    public String toString() {
        return "Kill{" +
                "victim=" + victim.getName() +
                ", timestamp='" + timestamp + '\'' +
                ", attacking=" + attackmode +
                ", honourable=" + honourable +
                '}';
    }
}
