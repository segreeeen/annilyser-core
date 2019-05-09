package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player {
    private final String name;
    private Token.Class clazz;
    private Token.Team team;
    private final CopyOnWriteArrayList<Kill> kills;
    private final CopyOnWriteArrayList<Kill> deaths;

    public Player(String name, Token.Class clazz, Token.Team team) {
        this.name = name;
        this.clazz = clazz;
        this.team = team;
        this.kills = new CopyOnWriteArrayList<>();
        this.deaths = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Token.Class getClazz() {
        return clazz;
    }

    public Token.Team getTeam() {
        return team;
    }

    public CopyOnWriteArrayList<Kill> getKills() {
        return kills;
    }

    public CopyOnWriteArrayList<Kill> getDeaths() {
        return deaths;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", clazz=" + clazz +
                ", team=" + team +
                ", kills=" + kills +
                ", deaths=" + deaths +
                '}';
    }

    public void setTeam(Token.Team team) {
        this.team = team;
    }

    public void setClass(Token.Class aClass) {
        this.clazz = aClass;
    }
}
