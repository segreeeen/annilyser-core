package com.nullwert.annilyser.parser;

public class Line {
    private final String originalMessage;
    private final long time;
    private final String parsedMessage;

    public Line(long time, String parsedMessage, String originalMessage) {
        this.time = time;
        this.parsedMessage = parsedMessage;
        this.originalMessage = originalMessage;
    }

    public long getTime() {
        return time;
    }

    public String getParsedMessage() {
        return parsedMessage;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }
}
