package com.nullwert.annilyser.model.datastructures;

public interface IStatistics {

    public KillStats getTotalKillstats();

    public KillStats getTotalDeathstats();

    public KillStats getRelativeKillstats();

    public KillStats getRelativeDeathstats();

    public int getPlayerCount();

}
