package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.parser.token.Token;

public class GamestateEvent implements DataEvent<Token.GameState> {

    private Token.GameState value;
    private long timestampMillis;

    private Token.Team winner;

    public GamestateEvent(Token.GameState value, long timestampMillis) {
        this.value = value;
        this.timestampMillis = timestampMillis;
    }

    public GamestateEvent(Token.GameState value, long timestampMillis, Token.Team winTeam) {
        this.value = value;
        this.timestampMillis = timestampMillis;
        this.winner = winTeam;
    }

    public long getTimestampMillis() {
        return timestampMillis;
    }

    public Token.Team getWinner() {
        return winner;
    }

    @Override
    public Token.GameState getValue() {
        return value;
    }
}
