package com.nullwert.annilyser.model.datastructures;

import java.util.concurrent.atomic.AtomicLong;

public class KillStats {
    private AtomicLong total = new AtomicLong(0);
    private AtomicLong bow = new AtomicLong(0);
    private AtomicLong melee = new AtomicLong(0);
    private AtomicLong nexusDefense = new AtomicLong(0);
    private AtomicLong bowNexusDefense = new AtomicLong(0);
    private AtomicLong meleeNexusDefense = new AtomicLong(0);
    private AtomicLong nexusAttack = new AtomicLong(0);
    private AtomicLong bowNexusAttack = new AtomicLong(0);
    private AtomicLong meleeNexusAttack = new AtomicLong(0);

    public void incTotal() {
        this.total.incrementAndGet();
    }

    public void incBow() {
        this.bow.incrementAndGet();
    }

    public long getNexusDefense() {
        return nexusDefense.get();
    }

    public void setNexusDefense(long nexusDefense) {
        this.nexusDefense.set(nexusDefense);
    }

    public long getBowNexusDefense() {
        return bowNexusDefense.get();
    }

    public void setBowNexusDefense(long bowNexusDefense) {
        this.bowNexusDefense.set(bowNexusDefense);
    }

    public long getMeleeNexusDefense() {
        return meleeNexusDefense.get();
    }

    public void setMeleeNexusDefense(long meleeNexusDefense) {
        this.meleeNexusDefense.set(meleeNexusDefense);
    }

    public long getNexusAttack() {
        return nexusAttack.get();
    }

    public void setNexusAttack(long nexusAttack) {
        this.nexusAttack.set(nexusAttack);
    }

    public long getBowNexusAttack() {
        return bowNexusAttack.get();
    }

    public void setBowNexusAttack(long bowNexusAttack) {
        this.bowNexusAttack.set(bowNexusAttack);
    }

    public long getMeleeNexusAttack() {
        return meleeNexusAttack.get();
    }

    public void setMeleeNexusAttack(long meleeNexusAttack) {
        this.meleeNexusAttack.set(meleeNexusAttack);
    }

    public void incMelee() {
        this.melee.incrementAndGet();
    }

    public void incNexusDefense() {
        this.nexusDefense.incrementAndGet();
    }

    public void incBowNexusDefense() {
        this.bowNexusDefense.incrementAndGet();
    }

    public void incMeleeNexusDefense() {
        this.meleeNexusDefense.incrementAndGet();
    }

    public void incNexusAttack() {
        this.nexusAttack.incrementAndGet();
    }

    public void incBowNexusAttack() {
        this.bowNexusAttack.incrementAndGet();
    }

    public void incMeleeNexusAttack() {
        this.meleeNexusAttack.incrementAndGet();
    }


    public long getTotal() {
        return total.get();
    }

    public void setTotal(long total) {
        this.total.set(total);
    }

    public long getBow() {
        return bow.get();
    }

    public void setBow(long bow) {
        this.bow.set(bow);
    }

    public long getMelee() {
        return melee.get();
    }

    public void setMelee(long melee) {
        this.melee.set(melee);
    }

    public KillStats getRelativeCopy(long playerCount) {
        playerCount = playerCount == 0 ? 1 : playerCount;
        KillStats killStats = new KillStats();
        killStats.setTotal(getTotal()/playerCount);
        killStats.setBow(getBow()/playerCount);
        killStats.setMelee(getMelee()/playerCount);
        killStats.setNexusAttack(getNexusAttack()/playerCount);
        killStats.setNexusDefense(getNexusDefense()/playerCount);
        killStats.setBowNexusAttack(getBowNexusAttack()/playerCount);
        killStats.setBowNexusDefense(getBowNexusDefense()/playerCount);
        killStats.setMeleeNexusAttack(getMeleeNexusAttack()/playerCount);
        killStats.setMeleeNexusDefense(getMeleeNexusDefense()/playerCount);
        return killStats;
    }

    public KillStats add(KillStats stats) {
        setTotal(getTotal()+stats.getTotal());
        setBow(getBow()+stats.getBow());
        setMelee(getMelee()+stats.getMelee());
        setNexusAttack(getNexusAttack()+stats.getNexusAttack());
        setNexusDefense(getNexusDefense()+stats.getNexusDefense());
        setBowNexusAttack(getBowNexusAttack()+stats.getBowNexusAttack());
        setBowNexusDefense(getBowNexusDefense()+stats.getBowNexusDefense());
        setMeleeNexusAttack(getMeleeNexusAttack()+stats.getMeleeNexusAttack());
        setMeleeNexusDefense(getMeleeNexusDefense()+stats.getMeleeNexusDefense());
        return this;
    }
}
