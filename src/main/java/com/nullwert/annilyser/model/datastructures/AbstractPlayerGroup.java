package com.nullwert.annilyser.model.datastructures;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlayerGroup implements IPlayerGroup, IStatisticRelatable {
    protected List<Player> players;
    protected KillStats killStats;
    protected KillStats deathStats;
    private final String name;
    private final String group;

    protected final Kind kind;

    public AbstractPlayerGroup(Kind kind, String name, String group) {
        this.killStats = new KillStats();
        this.deathStats = new KillStats();
        this.players = new ArrayList<>();
        this.kind = kind;
        this.name = name;
        this.group = group;
    }


    /**
     * The implementation of this method has to ensure that killStats and deathStats are NOT MODIFIED in this method!
     * It is highly advised to use the add() method of the KillStats class, and add the calculated stats in one go at the end of the method.
     *
     * @param k the kill that has to be transformed into stats
     */
    public abstract void addKill(Kill k);

    protected void logKill(Kill k, KillStats killStats) {
        killStats.incTotal();
        switch(k.getAttackmode()) {
            case DEFENSE:
                killStats.incNexusDefense();
                switch (k.getDeathkind()) {
                    case SHOT:
                        killStats.incBow();
                        killStats.incBowNexusDefense();
                        break;
                    case KILLED:
                        killStats.incMelee();
                        killStats.incMeleeNexusDefense();
                        break;
                    default: break;
                }
                break;
            case ATTACK:
                killStats.incNexusAttack();
                switch (k.getDeathkind()) {
                    case SHOT:
                        killStats.incBow();
                        killStats.incBowNexusAttack();
                        break;
                    case KILLED:
                        killStats.incMelee();
                        killStats.incMeleeNexusAttack();
                        break;
                    default: break;
                }
                break;
            case UNKNOWN:
                switch (k.getDeathkind()) {
                    case SHOT:
                        killStats.incBow();
                        break;
                    case KILLED:
                        killStats.incMelee();
                        break;
                    default: break;
                }
                break;
            default:
                break;
        }
    }

    public KillStats getAbsoluteKillstats() {
        return this.killStats;
    }

    public KillStats getAbsoluteDeathstats() {
        return this.deathStats;
    }

    public KillStats getRelativeKillstats() {
        return this.killStats.getRelativeCopy(getPlayerCount());
    }

    public KillStats getRelativeDeathstats() {
        return this.deathStats.getRelativeCopy(getPlayerCount());
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public IStatisticRelatable getCopy() {
        KillStats copyKills = new KillStats().add(killStats);
        KillStats copyDeaths = new KillStats().add(deathStats);
        if (getRelations() != null) {
            return new ImmutableGroupStats(kind, getName(), copyKills, copyDeaths, players.size(), this.group, getRelations());
        } else {
            return new ImmutableGroupStats(kind, getName(), copyKills, copyDeaths, players.size(), this.group);
        }
    }

    public Kind getKind() {
        return kind;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getGroup() {
        return this.group;
    }
}
