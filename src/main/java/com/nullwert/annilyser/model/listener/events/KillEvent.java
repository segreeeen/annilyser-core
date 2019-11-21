package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.model.datastructures.Kill;

public class KillEvent implements DataEvent<Kill> {

    private final Kill value;
    private final int blueKills;
    private final int greenKills;
    private final int redKills;
    private final int yellowKills;
    private final int blueDeaths;
    private final int greenDeaths;
    private final int redDeaths;
    private final int yellowDeaths;
    private final int killsTotal;
    private final long killsPerSecond;
    private final long killAverage;
    private final long maxKills;
    private final long minKills;

    public KillEvent(Kill value, int blueKills, int greenKills, int redKills, int yellowKills, int blueDeaths, int greenDeaths, int redDeaths, int yellowDeaths) {
        this.value = value;
        this.blueKills = blueKills;
        this.greenKills = greenKills;
        this.redKills = redKills;
        this.yellowKills = yellowKills;
        this.blueDeaths = blueDeaths;
        this.greenDeaths = greenDeaths;
        this.redDeaths = redDeaths;
        this.yellowDeaths = yellowDeaths;
        this.killsTotal = blueKills + greenKills + redKills + yellowKills;
        this.killAverage = killsTotal / 4;
        this.killsPerSecond = ((long)killsTotal)/value.getTimestampSeconds();
        this.maxKills = Math.max(blueKills, Math.max(greenKills, Math.max(redKills, yellowKills)));
        this.minKills = Math.min(blueKills, Math.min(greenKills, Math.min(redKills, yellowKills)));
    }

    public long getKillsPerSecond() {
        return killsPerSecond;
    }

    public int getKillsTotal() {
        return killsTotal;
    }

    public int getBlueKills() {
        return blueKills;
    }

    public int getGreenKills() {
        return greenKills;
    }

    public int getRedKills() {
        return redKills;
    }

    public int getYellowKills() {
        return yellowKills;
    }

    public int getBlueDeaths() {
        return blueDeaths;
    }

    public int getGreenDeaths() {
        return greenDeaths;
    }

    public int getRedDeaths() {
        return redDeaths;
    }

    public int getYellowDeaths() {
        return yellowDeaths;
    }


    @Override
    public Kill getValue() {
        return value;
    }

    public long getKillAverage() {
        return killAverage;
    }

    public long getMaxKills() {
        return maxKills;
    }

    public long getMinKills() {
        return minKills;
    }
}