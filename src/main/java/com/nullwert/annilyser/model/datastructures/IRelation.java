package com.nullwert.annilyser.model.datastructures;

public interface IRelation<T> {
    T getEnemy();

    long getPlayerCount();

    KillStats getKillStats();

    KillStats getKillStatsRelative();

    KillStats getKilledByEnemyStats();

    KillStats getKilledByEnemyStatsRelative();
}
