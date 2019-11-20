package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.parser.token.Token;

public class PhaseChangeEvent implements DataEvent<Token.Phase> {

    private Token.Phase value;
    private long timestampSeconds;

    public PhaseChangeEvent(Token.Phase value, long timestampSeconds) {
        this.value = value;
        this.timestampSeconds = timestampSeconds;
    }

    public long getTimestampSeconds() {
        return timestampSeconds;
    }

    @Override
    public Token.Phase getValue() {
        return value;
    }
}
