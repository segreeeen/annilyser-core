package com.nullwert.annilyser.model.datastructures;

public class ImmutableGroupStats implements IStatistics{

    private final KillStats killStats;
    private final KillStats deathStats;
    private final int playerCount;

    protected ImmutableGroupStats(KillStats killStats, KillStats deathStats, int playerCount) {
        this.killStats = killStats;
        this.deathStats = deathStats;
        this.playerCount = playerCount;
    }

    @Override
    public KillStats getTotalKillstats() {
        return killStats;
    }

    @Override
    public KillStats getTotalDeathstats() {
        return deathStats;
    }

    @Override
    public KillStats getRelativeKillstats() {
        return killStats.getRelativeCopy(playerCount);
    }

    @Override
    public KillStats getRelativeDeathstats() {
        return deathStats.getRelativeCopy(playerCount);
    }

    @Override
    public int getPlayerCount() {
        return playerCount;
    }

}
