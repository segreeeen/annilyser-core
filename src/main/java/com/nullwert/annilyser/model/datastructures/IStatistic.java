package com.nullwert.annilyser.model.datastructures;

import java.util.List;

public interface IStatistic {
    public Kind getKind();

    public String getName();

    public String getGroup();

    public IKillDeathStats getAbsoluteKillstats();

    public IKillDeathStats getAbsoluteDeathstats();

    public IKillDeathStats getRelativeKillstats();

    public IKillDeathStats getRelativeDeathstats();

    public int getPlayerCount();

    public List<IRelation> getRelations();



}
