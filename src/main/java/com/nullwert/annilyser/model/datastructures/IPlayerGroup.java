package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

public interface IPlayerGroup {
    public void addKill(Kill k);

    public void addPlayer(Player p);
}
