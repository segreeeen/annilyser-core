package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

public interface IPlayerGroup {
    void addKill(Kill k);

    void addPlayer(Player p);
}
