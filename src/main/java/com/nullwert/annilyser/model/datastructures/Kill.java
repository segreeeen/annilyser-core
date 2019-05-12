package com.nullwert.annilyser.model.datastructures;

public class Kill {
    private final Player killer;
    private final Player victim;
    private String timestamp;
    private long timestampSeconds;
    private final boolean attacking;
    private final boolean honourable;
    private final boolean defending;

    public Kill(boolean honourable, Player victim, String timestamp, boolean attacking, boolean defending, Player killer) {
        this.victim = victim;
        this.timestamp = timestamp;
        this.attacking = attacking;
        this.defending = defending;
        this.honourable = honourable;
        this.killer = killer;
    }
    public boolean isAttacking() {
        return attacking;
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

    @Override
    public String toString() {
        return "Kill{" +
                "victim=" + victim.getName() +
                ", timestamp='" + timestamp + '\'' +
                ", attacking=" + attacking +
                ", honourable=" + honourable +
                ", defending=" + defending +
                '}';
    }
}
