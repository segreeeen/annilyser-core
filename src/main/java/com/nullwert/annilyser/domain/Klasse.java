package com.nullwert.annilyser.domain;

import com.nullwert.annilyser.parser.token.Token;

public class Klasse extends AbstractPlayerGroup {
    private Token.Class klasse;

    public Klasse(Token.Class klasse) {
        super(Kind.CLASS, klasse.className, klasse.className.toLowerCase());
        this.klasse = klasse;
    }

    @Override
    public void addKill(Kill k) {

        KillStats kills = new KillStats();
        KillStats deaths = new KillStats();

        if (k.getKiller().getClazz() == klasse) {
            logKill(k, kills);
        }

        if (k.getVictim().getClazz() == klasse) {
            logKill(k, deaths);
        }

        killStats.add(kills);
        deathStats.add(deaths);
    }
}
