package com.nullwert.annilyser.io;

import java.nio.file.Path;
import java.nio.file.Paths;

public class LogDetector {

    public static Path getLogPath() {
        String workingDirectory = null;
        String osName = System.getProperty("os.name");
        String osNameMatch = osName.toLowerCase();

        if (osNameMatch.contains("linux")) {
            workingDirectory = System.getProperty("user.home") + "\\.minecraft\\logs\\latest.log";
        } else if (osNameMatch.contains("windows")) {
            workingDirectory = System.getenv("AppData") + "\\.minecraft\\logs\\latest.log";
        } else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
            workingDirectory = System.getProperty("user.home");
            workingDirectory += "/Library/Application Support" + "/minecraft/logs/latest.log";
        }
        Path p = Paths.get(workingDirectory);
        if (p.toFile().exists()) {
            return p;
        } else return null;
    }
}