package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;


public class TeamRelation implements IRelation<Token.Team> {
    private Token.Team team;
    private Token.Team enemy;
    private long playerCount;
    private KillStats killStats = new KillStats();
    private KillStats killedByEnemyStats = new KillStats();

    public TeamRelation(Token.Team team, Token.Team enemy) {
        this.team = team;
        this.enemy = enemy;
    }

    public Token.Team getEnemy() {
        return enemy;
    }

    public void setEnemy(Token.Team enemy) {
        this.enemy = enemy;
    }

    public long getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(long playerCount) {
        this.playerCount = playerCount;
    }

    public KillStats getKillStats() {
        return killStats;
    }

    public KillStats getKillStatsRelative() {
        return killStats.getRelativeCopy(playerCount);
    }

    public KillStats getKilledByEnemyStats() {
        return killedByEnemyStats;
    }

    public KillStats getKilledByEnemyStatsRelative() {
        return killedByEnemyStats.getRelativeCopy(playerCount);
    }

    public void setKillStats(KillStats killStats) {
        this.killStats = killStats;
    }

    public void setKilledByEnemyStats(KillStats killedByEnemyStats) {
        this.killedByEnemyStats = killedByEnemyStats;
    }

    public Token.Team getTeam() {
        return team;
    }

    public void setTeam(Token.Team team) {
        this.team = team;
    }
}
