package com.nullwert.annilyser.parser.token;

import java.util.HashSet;
import java.util.Set;

public class Token {

    public enum Class {
        ACR("Acrobat"),
        ALC("Alchemist"),
        ARC("Archer"),
        ASN("Assassin"),
        BAR("Bard"),
        BER("Berserker"),
        BLO("Bloodmage"),
        BUI("Builder"),
        CIV("Civilian"),
        DAS("Dasher"),
        DEF("Defender"),
        ENC("Enchanter"),
        ENG("Engineer"),
        FAR("Farmer"),
        HAN("Handyman"),
        HEA("Healer"),
        HUN("Hunter"),
        ICE("Iceman"),
        IMM("Immobilizer"),
        LUM("Lumberjack"),
        MER("Mercenary"),
        MIN("Miner"),
        NIN("Ninja"),
        PYR("Pyro"),
        RIF("Rift Walker"),
        ROB("Robin Hood"),
        SCP("Scorpio"),
        SCO("Scout"),
        SNI("Sniper"),
        SPI("Spider"),
        SPY("Spy"),
        SUC("Succubus"),
        SWA("Swapper"),
        THO("Thor"),
        TIN("Tinkerer"),
        TRA("Transporter"),
        VAM("Vampire"),
        WAR("Warrior"),
        WIZ("Wizard"),
        UNKNOWN("Unknown");

        public final String className;

        Class(String className) {
            this.className = className;
        }

        public static Class getAbbrString(String abb) {
            switch (abb) {
                case "ACR":
                    return ACR;
                case "ALC":
                    return ALC;
                case "ARC":
                    return ARC;
                case "ASN":
                    return ASN;
                case "BAR":
                    return BAR;
                case "BER":
                    return BER;
                case "BLO":
                    return BLO;
                case "BUI":
                    return BUI;
                case "CIV":
                    return CIV;
                case "DAS":
                    return DAS;
                case "DEF":
                    return DEF;
                case "ENC":
                    return ENC;
                case "ENG":
                    return ENG;
                case "FAR":
                    return FAR;
                case "HAN":
                    return HAN;
                case "HEA":
                    return HEA;
                case "HUN":
                    return HUN;
                case "ICE":
                    return ICE;
                case "IMM":
                    return IMM;
                case "LUM":
                    return LUM;
                case "MER":
                    return MER;
                case "MIN":
                    return MIN;
                case "NIN":
                    return NIN;
                case "PYR":
                    return PYR;
                case "RIF":
                    return RIF;
                case "ROB":
                    return ROB;
                case "SCP":
                    return SCP;
                case "SCO":
                    return SCO;
                case "SNI":
                    return SNI;
                case "SPI":
                    return SPI;
                case "SPY":
                    return SPY;
                case "SUC":
                    return SUC;
                case "SWA":
                    return SWA;
                case "THO":
                    return THO;
                case "TIN":
                    return TIN;
                case "TRA":
                    return TRA;
                case "VAM":
                    return VAM;
                case "WAR":
                    return WAR;
                case "WIZ":
                    return WIZ;
                default:
                    return null;
            }
        }
    }

    public enum Team {
        BLUE, GREEN, RED, YELLOW, UNKNOWN;

        public static Set<Team> getEnemyTeams(Team team) {
            Set<Team> set = new HashSet<Team>();
            switch (team) {
                case YELLOW:
                    set.add(RED);
                    set.add(BLUE);
                    set.add(GREEN);
                    break;
                case GREEN:
                    set.add(RED);
                    set.add(BLUE);
                    set.add(YELLOW);
                    break;
                case BLUE:
                    set.add(RED);
                    set.add(YELLOW);
                    set.add(GREEN);
                    break;
                case RED:
                    set.add(YELLOW);
                    set.add(BLUE);
                    set.add(GREEN);
                    break;
                default:
                    return null;
            }

            return set;
        }

        public static Team getByColorString(String color) {
            switch (color) {
                case "9":
                    return Team.BLUE;
                case "a":
                    return Team.GREEN;
                case "c":
                    return Team.RED;
                case "e":
                    return Team.YELLOW;
                default:
                    return null;
            }
        }

        public static Team getByString(String team) {
            switch (team) {
                case "Blue":
                    return Team.BLUE;
                case "Green":
                    return Team.GREEN;
                case "Red":
                    return Team.RED;
                case "Yellow":
                    return Team.YELLOW;
                default:
                    return null;
            }
        }
    }

    public enum GameState {
        IDLE, STARTED, DONE, PHASE;

        public static GameState getByStateString(String state) {
            switch (state) {
                case "The game":
                    return GameState.STARTED;
                case "Phase":
                    return GameState.PHASE;
                case "Blue":
                case "Green":
                case "Red":
                case "Yellow":
                    return GameState.DONE;
                default:
                    return null;
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
            switch (phase) {
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
                default:
                    return null;
            }
        }
    }

    public enum Attackmode {
        DEFENSE, ATTACK, UNKNOWN;

        public static Attackmode getByModeString(String mode) {
            switch (mode) {
                case "defending":
                    return Attackmode.DEFENSE;
                case "attacking":
                    return Attackmode.ATTACK;
                default:
                    return null;
            }
        }
    }

    public enum Deathkind {
        SHOT, KILLED;

        public static Deathkind getByKindString(String kind) {
            switch (kind) {
                case "shot":
                    return Deathkind.SHOT;
                case "killed":
                    return Deathkind.KILLED;
                default:
                    return null;
            }
        }
    }
}
