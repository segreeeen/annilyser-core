package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

public class Nexus {

    private Token.Team team;
    private boolean alive = true;
    private Player destroyer = null;
    private long timestampSeconds;

    public Nexus(Token.Team team) {
        this.team = team;
    }

    public void destroyNexus(Player destroyedBy) {
        alive = false;
        destroyer = destroyedBy;
    }

    public boolean isAlive() {
        return alive;
    }

    public Player getDestroyer() {
        return destroyer;
    }

    public long getTimestampSeconds() {
        return timestampSeconds;
    }

    public void setTimestampSeconds(long timestampSeconds) {
        this.timestampSeconds = timestampSeconds;
    }

    public Token.Team getTeam() {
        return team;
    }

    @Override
    public String toString() {
        return "Nexus{alive="+alive+", destroyer="+destroyer+"}";
    }
}
