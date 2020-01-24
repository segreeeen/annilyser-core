package com.nullwert.annilyser.domain;

import java.util.List;

public interface IStatistic {
    Kind getKind();

    String getName();

    String getGroup();

    int getPlayerCount();

    IKillDeathStats getAbsoluteKillstats();

    IKillDeathStats getAbsoluteDeathstats();

    IKillDeathStats getRelativeKillstats();

    IKillDeathStats getRelativeDeathstats();

    default List<IStatistic> getRelations() {
        return null;
    }

}
