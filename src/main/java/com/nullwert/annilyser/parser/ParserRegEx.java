package com.nullwert.annilyser.parser;

import java.util.regex.Pattern;

public class ParserRegEx {
    static final String GENERAL_TIME = "a";
    static final String GENERAL_THREAD = "b";
    static final String GENERAL_CHANNEL = "c";
    static final String GENERAL_MESSAGE = "d";
    static final String GAMESTART_MODE = "e";
    static final String GAMESTART_PHASENUMBER = "f";
    static final String WIN_TEAM = "g";
    static final String KILL_KILLER_TEAM = "h";
    static final String KILL_KILLER_NAME = "i";
    static final String KILL_KILLER_CLASS = "j";
    static final String KILL_VICTIM_TEAM = "k";
    static final String KILL_VICTIM_NAME = "l";
    static final String KILL_VICTIM_CLASS = "m";
    static final String KILL_ATTACKROLE = "n";
    static final String LOST_TEAM = "o";
    static final String KILL_ATTACKED_NEXUS = "p";
    static final String GENERAL_TEAM = "q";
    static final String NEXUS_DESTROYED_TEAM = "team";
    static final String NEXUS_DESTROYED_BY = "feckoff";
    static final String KILL_HONOUR_OF = "honour";
    static final String KILL_DEATH_KIND = "deathkind";

    final Pattern GENERAL;
    final Pattern GAMESTART;
    final Pattern WIN;
    final Pattern KILL;
    final Pattern NEXUS_DESTROYED_LINE1;
    final Pattern NEXUS_DESTROYED_LINE2;
    final Pattern NEXUS_DESTROYED_LINE3;


    public ParserRegEx() {
        GENERAL = Pattern.compile(String.format("\\[(?<%s>.*?)\\]\\s\\[(?<%s>.*?)\\]:\\s\\[(?<%s>.*?)\\](\\[(?<%s>.*?)\\])?(?<%s>.*?)",
                GENERAL_TIME, GENERAL_THREAD, GENERAL_CHANNEL, GENERAL_TEAM, GENERAL_MESSAGE));
        GAMESTART = Pattern.compile(String.format("(.*?)(?<%s>(The game|Phase))\\s?(?<%s>\\d?) (has started)(.*?)",
                GAMESTART_MODE, GAMESTART_PHASENUMBER));
        WIN = Pattern.compile(String.format("(.*?)(?<%s>Blue|Red|Green|Yellow) team wins!", WIN_TEAM));
        KILL = Pattern.compile(String.format("(.*?)\u00A7(?<%s>[9ace])(?<%s>.*?)\u00A7[9ace]\\((?<%s>.*?)\\) " +
                        "(?<%s>killed|shot) (.*?)\u00A7(?<%s>[9ace])(?<%s>.*?)\u00A7[9ace]\\((?<%s>.*?)\\)" +
                        "(( (?<%s>attacking|defending) (?<%s>Blue|Red|Green|Yellow)'s Nexus.)|( in honour of (?<%s>Blue|Red|Green|Yellow)\\.))?",
                KILL_KILLER_TEAM, KILL_KILLER_NAME, KILL_KILLER_CLASS, KILL_DEATH_KIND, KILL_VICTIM_TEAM, KILL_VICTIM_NAME, KILL_VICTIM_CLASS, KILL_ATTACKROLE, KILL_ATTACKED_NEXUS, KILL_HONOUR_OF));
        NEXUS_DESTROYED_LINE1 = Pattern.compile(String.format("(.*?)(?<%s>Blue|Red|Green|Yellow) team's", NEXUS_DESTROYED_TEAM));
        NEXUS_DESTROYED_LINE2 = Pattern.compile(String.format("(.*?)nexus has been destroyed"));
        NEXUS_DESTROYED_LINE3 = Pattern.compile(String.format("(.*?)by (?<%s>.*?)", NEXUS_DESTROYED_BY));
    }
}