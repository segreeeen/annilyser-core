package com.nullwert.annilyser.model.datastructures;

import com.nullwert.annilyser.parser.token.Token;

import java.util.*;
import java.util.stream.Collectors;

public class Team<T extends Token.Team> {
    private Map<String, Player> players;
    private T team;
    private Set<Token.Team> enemyTeams;

    public Team(T team) {
        this.team = team;
        this.enemyTeams = Token.Team.getEnemyTeams(team);
    }

    public List<TeamRelation> getEnemyRelations() {
        List<TeamRelation> relations = new ArrayList<>();

        List<Kill> teamkills = players.values().stream()
                .map(Player::getKills)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<Kill> teamdeaths = players.values().stream()
                .map(Player::getDeaths)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        for (Token.Team enemy : enemyTeams) {
            TeamRelation relation = new TeamRelation(enemy);
            relation.setPlayerCount(this.getPlayerCount());

            Long killedEnemiesTotal = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy).count();
            relation.setKilledEnemiesTotal(killedEnemiesTotal);
            Long killedEnemiesByBow = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            relation.setKilledByEnemiesByBow(killedEnemiesByBow);
            Long killedEnemiesMelee = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            relation.setKilledEnemiesMelee(killedEnemiesMelee);
            Long killedEnemiesNexus = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .count();
            relation.setKilledEnemiesNexus(killedEnemiesNexus);
            Long killedEnemiesByBowNexus = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            relation.setKilledEnemiesByBowNexus(killedEnemiesByBowNexus);
            Long killedEnemiesMeleeNexus = teamkills.stream()
                    .filter(kill -> kill.getVictim().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            relation.setKilledEnemiesMeleeNexus(killedEnemiesMeleeNexus);

            Long killedByEnemiesTotal = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy).count();
            relation.setKilledByEnemiesTotal(killedByEnemiesTotal);
            Long killedByEnemiesByBow = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            relation.setKilledByEnemiesByBow(killedByEnemiesByBow);
            Long killedByEnemiesMelee = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            relation.setKilledByEnemiesMelee(killedByEnemiesMelee);

            Long killedByEnemiesNexus = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .count();
            relation.setKilledByEnemiesNexus(killedByEnemiesNexus);
            Long killedByEnemiesByBowNexus = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.SHOT).count();
            relation.setKilledByEnemiesByBowNexus(killedByEnemiesByBowNexus);
            Long killedByEnemiesMeleeNexus = teamdeaths.stream()
                    .filter(kill -> kill.getKiller().getTeam() == enemy)
                    .filter(kill -> kill.getAttackmode() == Token.Attackmode.ATTACK)
                    .filter(kill -> kill.getDeathkind() == Token.Deathkind.KILLED).count();
            relation.setKilledByEnemiesMeleeNexus(killedByEnemiesMeleeNexus);
            relations.add(relation);
        }
        return relations;
    }

    public int getPlayerCount() {
        return players.size();
    }


}
