package com.nullwert.annilyser.model.listener;

import com.nullwert.annilyser.model.listener.events.DataEvent;

public interface DataListener<T extends DataEvent> {
    void fireChangeEvent(T e);
}
