package com.nullwert.annilyser.domain.listener.events;

public class LineEvent implements DataEvent<String> {
    private String line;

    public LineEvent(String line) {
        this.line = line;
    }

    @Override
    public String getValue() {
        return line;
    }
}
