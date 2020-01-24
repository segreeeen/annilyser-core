package com.nullwert.annilyser.domain.listener;

import com.nullwert.annilyser.domain.listener.events.DataEvent;

public interface DataListener<T extends DataEvent> {
    void fireChangeEvent(T e);
}
