package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractPlayerGroup implements IPlayerGroup, IStatistics {
    protected List<Player> players;
    protected KillStats killStats;
    protected KillStats deathStats;

    public AbstractPlayerGroup() {
        this.killStats = new KillStats();
        this.deathStats = new KillStats();
        this.players = new ArrayList<>();
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

    public KillStats getTotalKillstats() {
        return this.killStats;
    }

    public KillStats getTotalDeathstats() {
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

    public IStatistics getCopy() {
        KillStats copyKills = new KillStats().add(killStats);
        KillStats copyDeaths = new KillStats().add(killStats);
        return new ImmutableGroupStats(copyKills, copyDeaths, players.size());
    }
}
