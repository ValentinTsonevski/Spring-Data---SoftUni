package com.example.football.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static Path TOWNS_JSON_PATH = Path.of("src/main/resources/files/json/towns.json");
    public static Path TEAMS_JSON_PATH = Path.of("src/main/resources/files/json/teams.json");
    public static Path STATS_XML_PATH = Path.of("src/main/resources/files/xml/stats.xml");
    public static Path PLAYER_XML_PATH = Path.of("src/main/resources/files/xml/players.xml");
}
