package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.model.datastructures.*;
import com.nullwert.annilyser.parser.token.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class KillEvent implements DataEvent<Kill> {

    private final Kill value;
    private final IStatistics yellow;
    private final IStatistics red;
    private final IStatistics blue;
    private final IStatistics green;
    private final  List<TeamRelation> yellowRelations;
    private final  List<TeamRelation> greenRelations;
    private final  List<TeamRelation> redRelations;
    private final  List<TeamRelation> blueRelations;
    private final List<Player> players;
    private List<IStatistics> klassen;

    @Override
    public Kill getValue() {
        return value;
    }

    public KillEvent(Kill value,
                     Map<Token.Class, Klasse> klassen,
                     List<Player> players,
                     IStatistics yellow,
                     IStatistics red,
                     IStatistics blue,
                     IStatistics green,
                     List<TeamRelation> yellowRelations,
                     List<TeamRelation> greenRelations,
                     List<TeamRelation> redRelations,
                     List<TeamRelation> blueRelations) {
        this.players = players;
        this.value = value;
        this.yellow = yellow;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.yellowRelations = yellowRelations;
        this.greenRelations = greenRelations;
        this.redRelations = redRelations;
        this.blueRelations = blueRelations;
        copyKlassen(klassen);
    }

    private void copyKlassen(Map<Token.Class, Klasse> klassen) {
        this.klassen = klassen.values().stream().map(Klasse::getCopy).collect(Collectors.toList());
    }
}