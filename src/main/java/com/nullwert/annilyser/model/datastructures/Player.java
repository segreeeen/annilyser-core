package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Player extends AbstractPlayerGroup {
    private final String name;
    private Token.Class clazz;
    private Token.Team team;
    private final CopyOnWriteArrayList<Kill> killsDeaths;

    public Player(String name, Token.Class clazz, Token.Team team) {
        super(Kind.PLAYER, name, team.toString().toLowerCase());
        this.name = name;
        this.clazz = clazz;
        this.team = team;
        this.killsDeaths = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public String getGroup() {
        return this.team.toString().toLowerCase();
    }

    @Override
    public List<IRelation> getRelations() {
        return null;
    }

    public Token.Class getClazz() {
        return clazz;
    }

    public Token.Team getTeam() {
        return team;
    }

    public void addKill(Kill k) {
        killsDeaths.add(k);

        KillStats kills = new KillStats();
        KillStats deaths = new KillStats();

        if (k.getKiller() == this) {
            logKill(k, kills);
        }

        if (k.getVictim() == this) {
            logKill(k, deaths);
        }

        killStats.add(kills);
        deathStats.add(deaths);
    }

    public List<Kill> getKills() {
        return killsDeaths.stream().filter(k -> k.getKiller() == this).collect(Collectors.toList());
    }

    public List<Kill> getDeaths() {
        return killsDeaths.stream().filter(k -> k.getVictim() == this).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", clazz=" + clazz +
                ", team=" + team +
                ", killsDeaths=" + killsDeaths;
    }

    public void setTeam(Token.Team team) {
        this.team = team;
    }

    public void setClass(Token.Class aClass) {
        this.clazz = aClass;
    }
}
