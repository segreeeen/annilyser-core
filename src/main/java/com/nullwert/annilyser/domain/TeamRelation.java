package com.nullwert.annilyser.domain;

import com.nullwert.annilyser.parser.token.Token;


public class TeamRelation implements IRelation<Token.Team>, IStatistic {
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

    @Override
    public Kind getKind() {
        return Kind.TEAM_RELATION;
    }

    @Override
    public String getName() {
        return team.name();
    }

    @Override
    public String getGroup() {
        return team.name();
    }

    @Override
    public int getPlayerCount() {
        return 0;
    }

    public void setPlayerCount(long playerCount) {
        this.playerCount = playerCount;
    }


    public KillStats getKilledByEnemyStats() {
        return killedByEnemyStats;
    }

    public Token.Team getTeam() {
        return team;
    }

    public void setTeam(Token.Team team) {
        this.team = team;
    }

    public KillStats getKillStats() {
        return killStats;
    }

    @Override
    public IKillDeathStats getAbsoluteKillstats()  {
        return killStats;
    }

    @Override
    public IKillDeathStats getAbsoluteDeathstats()  {
        return killedByEnemyStats;
    }

    @Override
    public IKillDeathStats getRelativeKillstats()  {
        return killStats.getRelativeCopy(playerCount);
    }

    @Override
    public IKillDeathStats getRelativeDeathstats()  {
        return killedByEnemyStats.getRelativeCopy(playerCount);
    }

}
