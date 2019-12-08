package com.nullwert.annilyser.model.datastructures;

public class ImmutableGroupStats implements IStatistics{

    private final KillStats killStats;
    private final KillStats deathStats;
    private final int playerCount;
    private final Kind kind;
    private final String name;

    protected ImmutableGroupStats(Kind kind, String name, KillStats killStats, KillStats deathStats, int playerCount) {
        this.kind = kind;
        this.name = name;
        this.killStats = killStats;
        this.deathStats = deathStats;
        this.playerCount = playerCount;
    }

    @Override
    public Kind getKind() {
        return kind;
    }

    @Override
    public String getName() {
        return name;
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
