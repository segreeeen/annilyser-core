package com.nullwert.annilyser.parser;

public class ParserToken {
    public enum Team {
        BLUE("Blue","9"), GREEN("Green","a"), RED("Red","c"), YELLOW("Yellow","e");

        public final String name;
        public final String code;

        Team(String name, String code) {
            this.name = name;
            this.code = code;
        }
    }

    public static Team getByColorString(String color) {
        switch(color) {
            case "9":
                return Team.BLUE;
            case "a":
                return Team.GREEN;
            case "c":
                return Team.RED;
            case "e":
                return Team.YELLOW;
            default: return null;
        }
    }

    public enum GameState {
        STARTED, DONE;

        public static GameState getByStateString(String state) {
            switch(state) {
                case "The game":
                    return GameState.STARTED;
                case "Blue":
                case "Green":
                case "Red":
                case "Yellow":
                    return GameState.DONE;
                default: return null;
            }
        }
    }

    public enum Phase {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);

        public final int phase;

        Phase(int phase) {
            this.phase = phase;
        }

        public static Phase getByPhaseString(String phase) {
            switch(phase) {
                case "1":
                    return Phase.ONE;
                case "2":
                    return Phase.TWO;
                case "3":
                    return Phase.THREE;
                case "4":
                    return Phase.FOUR;
                case "5":
                    return Phase.FIVE;
                default: return null;
            }
        }
    }

    public enum Attackmode {
        DEFENSE, ATTACK;

        public static Attackmode getByModeString(String mode) {
            switch(mode) {
                case "defending":
                    return Attackmode.DEFENSE;
                case "attacking":
                    return Attackmode.ATTACK;
                default: return null;
            }
        }
    }

    public enum Deathkind {
        SHOT, KILLED;

        public static Deathkind getByKindString(String kind) {
            switch(kind) {
                case "shot":
                    return Deathkind.SHOT;
                case "killed":
                    return Deathkind.KILLED;
                default: return null;
            }
        }
    }
}
