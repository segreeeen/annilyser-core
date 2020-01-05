package com.nullwert.annilyser.model.datastructures;

import java.util.List;

public class ImmutableGroupStats implements IStatisticRelatable {

    private final KillStats killStats;
    private final KillStats deathStats;
    private final int playerCount;
    private final Kind kind;
    private final String name;
    private final String group;
    private List<IStatistic> relations;

    protected ImmutableGroupStats(Kind kind, String name, KillStats killStats, KillStats deathStats, int playerCount, String group) {
        this.kind = kind;
        this.name = name;
        this.killStats = killStats;
        this.deathStats = deathStats;
        this.playerCount = playerCount;
        this.group = group;
    }

    protected ImmutableGroupStats(Kind kind, String name, KillStats killStats, KillStats deathStats, int playerCount, String group, List<IStatistic> relations) {
        this(kind, name, killStats, deathStats, playerCount, group);
        this.relations = relations;
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
    public String getGroup() {
        return this.group;
    }

    @Override
    public KillStats getAbsoluteKillstats() {
        return killStats;
    }

    @Override
    public KillStats getAbsoluteDeathstats() {
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

    @Override
    public List<IStatistic> getRelations() {
        return this.relations;
    }
}
