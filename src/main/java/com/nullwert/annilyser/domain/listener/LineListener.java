package com.nullwert.annilyser.domain.listener;

import com.nullwert.annilyser.domain.listener.events.LineEvent;

public interface LineListener extends DataListener<LineEvent> {

    @Override
    public void fireChangeEvent(LineEvent e);

}
