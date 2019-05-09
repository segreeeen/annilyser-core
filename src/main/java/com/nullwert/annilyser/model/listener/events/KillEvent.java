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

}