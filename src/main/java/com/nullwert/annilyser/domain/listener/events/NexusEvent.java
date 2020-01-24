package com.nullwert.annilyser.domain.listener.events;

import com.nullwert.annilyser.domain.Nexus;

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
