package com.nullwert.annilyser.parser;

import java.util.regex.Pattern;

public class ParserRegEx {
    public static final String GENERAL_TIME = "a";
    public static final String GENERAL_THREAD = "b";
    public static final String GENERAL_CHANNEL = "c";
    public static final String GENERAL_MESSAGE = "d";
    public static final String GAMESTART_MODE = "e";
    public static final String GAMESTART_PHASENUMBER = "f";
    public static final String WIN_TEAM = "g";
    public static final String KILL_KILLER_TEAM = "h";
    public static final String KILL_KILLER_NAME = "i";
    public static final String KILL_KILLER_CLASS = "j";
    public static final String KILL_VICTIM_TEAM = "k";
    public static final String KILL_VICTIM_NAME = "l";
    public static final String KILL_VICTIM_CLASS = "m";
    public static final String KILL_ATTACKROLE = "n";
    public static final String LOST_TEAM = "o";
    public static final String KILL_ATTACKED_NEXUS = "p";
    public static final String GENERAL_TEAM = "q";
    public static final String NEXUS_DESTROYED_TEAM = "team";
    public static final String NEXUS_DESTROYED_BY = "feckoff";
    public static final String KILL_HONOUR_OF = "honour";

    public final Pattern GENERAL;
    public final Pattern GAMESTART;
    public final Pattern WIN;
    public final Pattern KILL;
    public final Pattern NEXUS_DESTROYED_LINE1;
    public final Pattern NEXUS_DESTROYED_LINE2;
    public final Pattern NEXUS_DESTROYED_LINE3;


    public ParserRegEx() {
        GENERAL = Pattern.compile(String.format("\\[(?<%s>.*?)\\]\\s\\[(?<%s>.*?)\\]:\\s\\[(?<%s>.*?)\\](\\[(?<%s>.*?)\\])?(?<%s>.*?)",
                GENERAL_TIME, GENERAL_THREAD, GENERAL_CHANNEL, GENERAL_TEAM, GENERAL_MESSAGE));
        GAMESTART = Pattern.compile(String.format("(.*?)(?<%s>(The game|Phase))\\s?(?<%s>\\d?) (has started)(.*?)",
                GAMESTART_MODE, GAMESTART_PHASENUMBER));
        WIN = Pattern.compile(String.format("(.*?)(?<%s>Blue|Red|Green|Yellow) team wins!", WIN_TEAM));
        KILL = Pattern.compile(String.format("(.*?)\u00A7(?<%s>[9ace])(?<%s>.*?)\u00A7[9ace]\\((?<%s>.*?)\\) " +
                        "(killed|shot) (.*?)\u00A7(?<%s>[9ace])(?<%s>.*?)\u00A7[9ace]\\((?<%s>.*?)\\)" +
                        "(( (?<%s>attacking|defending) (?<%s>Blue|Red|Green|Yellow)'s Nexus.)|( in honour of (?<%s>Blue|Red|Green|Yellow)\\.))?",
                KILL_KILLER_TEAM, KILL_KILLER_NAME, KILL_KILLER_CLASS, KILL_VICTIM_TEAM, KILL_VICTIM_NAME, KILL_VICTIM_CLASS, KILL_ATTACKROLE, KILL_ATTACKED_NEXUS, KILL_HONOUR_OF));
        NEXUS_DESTROYED_LINE1 = Pattern.compile(String.format("(.*?)(?<%s>Blue|Red|Green|Yellow) team's", NEXUS_DESTROYED_TEAM));
        NEXUS_DESTROYED_LINE2 = Pattern.compile(String.format("(.*?)nexus has been destroyed"));
        NEXUS_DESTROYED_LINE3 = Pattern.compile(String.format("(.*?)by (?<%s>.*?)", NEXUS_DESTROYED_BY));
    }
}