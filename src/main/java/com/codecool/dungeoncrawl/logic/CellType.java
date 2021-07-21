package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WEAPON("weapon"),
    KEY("key"),
    FENCE("fence"),
    STAIRS("stairs"),
    WALL("wall");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
