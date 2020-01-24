package com.nullwert.annilyser.domain;

import com.nullwert.annilyser.domain.listener.GamestateChangeListener;
import com.nullwert.annilyser.domain.listener.KillListener;
import com.nullwert.annilyser.domain.listener.NexusListener;
import com.nullwert.annilyser.domain.listener.PhaseChangeListener;
import com.nullwert.annilyser.parser.Line;
import com.nullwert.annilyser.parser.token.Token;

public interface IGame {
    public Player addPlayer(String name, Token.Class clazz, Token.Team team);

    public void setGameState(Token.GameState gameState, String time);

    public void setGameState(Token.GameState state, String time, Token.Team winTeam);

    public void setPhase(Token.Phase phase, String time);

    public void addKill(Kill kill);

    public void addLine(Line s);

    public void destroyNexus(String player, Token.Team team, String timestamp);

    public void registerGamestateChangeListener (GamestateChangeListener l);

    public void registerPhaseChangeListener(PhaseChangeListener l);

    public void registerKillListener(KillListener l);

    public void registerNexusListener(NexusListener l);


}
