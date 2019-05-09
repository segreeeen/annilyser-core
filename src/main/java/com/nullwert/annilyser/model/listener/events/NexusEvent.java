package com.nullwert.annilyser.model.listener.events;

import com.nullwert.annilyser.model.datastructures.Nexus;

public class NexusEvent implements DataEvent<Nexus> {

    private Nexus value;

    public NexusEvent(Nexus value) {
        this.value = value;
    }

    @Override
    public Nexus getValue() {
        return value;
    }
}
