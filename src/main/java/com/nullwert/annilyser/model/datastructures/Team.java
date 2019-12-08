package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

import java.util.*;
import java.util.stream.Collectors;

public class Team extends AbstractPlayerGroup {
    private Token.Team team;
    private Set<Token.Team> enemyTeams;

    public Team(Token.Team team) {
        super(Kind.TEAM);
        this.team = team;
        this.enemyTeams = Token.Team.getEnemyTeams(team);
    }

    @Override
    public void addKill(Kill k) {
        if (k.getVictim().getTeam() != team && k.getKiller().getTeam() != team) {
            return;
        }

        KillStats killStats = new KillStats();
        KillStats deathStats = new KillStats();
        
        if (k.getKiller().getTeam() == team) {
            logKill(k, killStats);
        }

        if (k.getVictim().getTeam() == team) {
            logKill(k, deathStats);
        }

        this.killStats.add(killStats);
        this.deathStats.add(deathStats);
    }

    public List<TeamRelation> getEnemyRelations() {
        List<TeamRelation> relations = new ArrayList<>();

        List<Kill> teamkills = players.stream()
                .map(Player::getKills)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<Kill> teamdeaths = players.stream()
                .map(Player::getDeaths)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Token.Team enemy : enemyTeams) {
            TeamRelation relation = new TeamRelation(this.team, enemy);
            KillStats kills = relation.getKillStats();
            KillStats killedBys = relation.getKilledByEnemyStats();

            relation.setPlayerCount(this.getPlayerCount());

            Long killedEnemiesTotal = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy).count();
            kills.setTotal(killedEnemiesTotal);
            Long killedEnemiesByBow = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            kills.setBow(killedEnemiesByBow);
            Long killedEnemiesMelee = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            kills.setMelee(killedEnemiesMelee);
            Long killedEnemiesNexusAttack = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .count();
            kills.setNexusAttack(killedEnemiesNexusAttack);
            Long killedEnemiesByBowNexusAttack = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            kills.setBowNexusAttack(killedEnemiesByBowNexusAttack);
            Long killedEnemiesMeleeNexusAttack = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            kills.setMeleeNexusAttack(killedEnemiesMeleeNexusAttack);
            Long killedEnemiesNexusDefense = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.DEFENSE)
                    .count();
            kills.setNexusDefense(killedEnemiesNexusDefense);
            Long killedEnemiesByBowNexusDefense = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.DEFENSE)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            kills.setBowNexusDefense(killedEnemiesByBowNexusDefense);
            Long killedEnemiesMeleeNexusDefense = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.DEFENSE)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            kills.setMeleeNexusDefense(killedEnemiesMeleeNexusDefense);

            Long killedByEnemiesTotal = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy).count();
            killedBys.setTotal(killedByEnemiesTotal);
            Long killedByEnemiesByBow = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            killedBys.setBow(killedByEnemiesByBow);
            Long killedByEnemiesMelee = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            killedBys.setMelee(killedByEnemiesMelee);

            Long killedByEnemiesNexusAttack = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .count();
            killedBys.setNexusAttack(killedByEnemiesNexusAttack);
            Long killedByEnemiesByBowNexusAttack = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            killedBys.setBowNexusAttack(killedByEnemiesByBowNexusAttack);
            Long killedByEnemiesMeleeNexusAttack = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            killedBys.setMeleeNexusAttack(killedByEnemiesMeleeNexusAttack);
            Long killedByEnemiesNexusDefense = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.DEFENSE)
                    .count();
            killedBys.setNexusDefense(killedByEnemiesNexusDefense);
            Long killedByEnemiesByBowNexusDefense = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.DEFENSE)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            killedBys.setBowNexusDefense(killedByEnemiesByBowNexusDefense);
            Long killedByEnemiesMeleeNexusDefense = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.DEFENSE)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            killedBys.setMeleeNexusDefense(killedByEnemiesMeleeNexusDefense);
            relations.add(relation);
        }
        return relations;
    }


    @Override
    public String getName() {
        return this.team.toString();
    }
}
