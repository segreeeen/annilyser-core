package com.nullwert.annilyser.domain.listener.events;

import com.nullwert.annilyser.parser.token.Token;

public class GamestateEvent implements DataEvent<Token.GameState> {

    private Token.GameState value;
    private long timestampSeconds;

    private Token.Team winner;

    public GamestateEvent(Token.GameState value, long timestampSeconds) {
        this.value = value;
        this.timestampSeconds = timestampSeconds;
    }

    public GamestateEvent(Token.GameState value, long timestampSeconds, Token.Team winTeam) {
        this.value = value;
        this.timestampSeconds = timestampSeconds;
        this.winner = winTeam;
    }

    public long getTimestampSeconds() {
        return timestampSeconds;
    }

    public Token.Team getWinner() {
        return winner;
    }

    @Override
    public Token.GameState getValue() {
        return value;
    }
}
