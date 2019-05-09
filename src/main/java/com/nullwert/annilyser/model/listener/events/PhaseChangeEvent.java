package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.parser.token.Token;

public class PhaseChangeEvent implements DataEvent<Token.Phase> {

    private Token.Phase value;
    private long timestampMillis;

    public PhaseChangeEvent(Token.Phase value, long timestampMillis) {
        this.value = value;
        this.timestampMillis = timestampMillis;
    }

    public long getTimestampMillis() {
        return timestampMillis;
    }

    @Override
    public Token.Phase getValue() {
        return value;
    }
}
