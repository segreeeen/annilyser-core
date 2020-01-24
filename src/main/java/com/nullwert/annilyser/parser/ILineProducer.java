package com.nullwert.annilyser.parser;

import java.util.concurrent.BlockingQueue;

@FunctionalInterface
public interface ILineProducer {
    BlockingQueue<String> getDoneLines();
}
