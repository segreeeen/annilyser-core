package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

public class TeamRelation {
    private Token.Team enemy;
    private long playerCount;
    private long killedEnemiesTotal;
    private long killedEnemiesByBow;
    private long killedEnemiesMelee;
    private long killedEnemiesNexus;
    private long killedEnemiesByBowNexus;
    private long killedEnemiesMeleeNexus;
    private long killedByEnemiesTotal;
    private long killedByEnemiesByBow;
    private long killedByEnemiesMelee;
    private long killedByEnemiesNexus;
    private long killedByEnemiesByBowNexus;
    private long killedByEnemiesMeleeNexus;

    public Token.Team getEnemy() {
        return enemy;
    }

    public void setEnemy(Token.Team enemy) {
        this.enemy = enemy;
    }

    public long getKilledEnemiesTotal() {
        return killedEnemiesTotal;
    }

    public void setKilledEnemiesTotal(long killedEnemiesTotal) {
        this.killedEnemiesTotal = killedEnemiesTotal;
    }

    public long getKilledEnemiesByBow() {
        return killedEnemiesByBow;
    }

    public void setKilledEnemiesByBow(long killedEnemiesByBow) {
        this.killedEnemiesByBow = killedEnemiesByBow;
    }

    public long getKilledEnemiesMelee() {
        return killedEnemiesMelee;
    }

    public void setKilledEnemiesMelee(long killedEnemiesMelee) {
        this.killedEnemiesMelee = killedEnemiesMelee;
    }

    public long getKilledEnemiesNexus() {
        return killedEnemiesNexus;
    }

    public void setKilledEnemiesNexus(long killedEnemiesNexus) {
        this.killedEnemiesNexus = killedEnemiesNexus;
    }

    public long getKilledEnemiesByBowNexus() {
        return killedEnemiesByBowNexus;
    }

    public void setKilledEnemiesByBowNexus(long killedEnemiesByBowNexus) {
        this.killedEnemiesByBowNexus = killedEnemiesByBowNexus;
    }

    public long getKilledEnemiesMeleeNexus() {
        return killedEnemiesMeleeNexus;
    }

    public void setKilledEnemiesMeleeNexus(long killedEnemiesMeleeNexus) {
        this.killedEnemiesMeleeNexus = killedEnemiesMeleeNexus;
    }

    public long getKilledByEnemiesByBow() {
        return killedByEnemiesByBow;
    }

    public void setKilledByEnemiesByBow(long killedByEnemiesByBow) {
        this.killedByEnemiesByBow = killedByEnemiesByBow;
    }

    public long getKilledByEnemiesMelee() {
        return killedByEnemiesMelee;
    }

    public void setKilledByEnemiesMelee(long killedByEnemiesMelee) {
        this.killedByEnemiesMelee = killedByEnemiesMelee;
    }

    public long getKilledByEnemiesByBowNexus() {
        return killedByEnemiesByBowNexus;
    }

    public void setKilledByEnemiesByBowNexus(long killedByEnemiesByBowNexus) {
        this.killedByEnemiesByBowNexus = killedByEnemiesByBowNexus;
    }

    public long getKilledByEnemiesMeleeNexus() {
        return killedByEnemiesMeleeNexus;
    }

    public void setKilledByEnemiesMeleeNexus(long killedByEnemiesMeleeNexus) {
        this.killedByEnemiesMeleeNexus = killedByEnemiesMeleeNexus;
    }

    public TeamRelation(Token.Team enemy) {
        this.enemy = enemy;
    }

    public long getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(long playerCount) {
        this.playerCount = playerCount;
    }

    public long getKilledByEnemiesNexus() {
        return killedByEnemiesNexus;
    }

    public void setKilledByEnemiesNexus(long killedByEnemiesNexus) {
        this.killedByEnemiesNexus = killedByEnemiesNexus;
    }

    public long getKilledByEnemiesTotal() {
        return killedByEnemiesTotal;
    }

    public void setKilledByEnemiesTotal(long killedByEnemiesTotal) {
        this.killedByEnemiesTotal = killedByEnemiesTotal;
    }
}
