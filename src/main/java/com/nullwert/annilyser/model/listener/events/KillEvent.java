package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.model.datastructures.*;
import com.nullwert.annilyser.parser.token.Token;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KillEvent implements DataEvent<Kill> {

    private final Kill value;

    public List<IStatistic> getTeams() {
        return teams;
    }

    public List<IStatistic> getPlayers() {
        return players;
    }

    public List<IStatistic> getKlassen() {
        return klassen;
    }

    private final List<IStatistic> teams;
    private final List<IStatistic> players;
    private List<IStatistic> klassen;

    @Override
    public Kill getValue() {
        return value;
    }

    public KillEvent(Kill value,
                     Map<Token.Class, Klasse> klassen,
                     List<IStatistic> players,
                     List<IStatistic> teams) {
        this.players = players;
        this.value = value;
        this.teams = teams;
        copyKlassen(klassen);
    }

    private void copyKlassen(Map<Token.Class, Klasse> klassen) {
        this.klassen = klassen.values().stream().map(Klasse::getCopy).collect(Collectors.toList());
    }
}