package com.nullwert.annilyser.domain;

public interface IKillDeathStats {
    long getTotal();

    long getBow();

    long getMelee();

    long getNexusDefense();

    long getBowNexusDefense();

    long getMeleeNexusDefense();

    long getNexusAttack();

    long getBowNexusAttack();

    long getMeleeNexusAttack();
}
