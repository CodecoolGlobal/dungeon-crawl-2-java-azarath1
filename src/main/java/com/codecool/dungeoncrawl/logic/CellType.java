package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WEAPON("weapon"),
    KEY("key"),
    FENCE("fence"),
    DOOR("door"),
    STAIRS("stairs"),
    WOOD("wood"),
    WATER("water"),
    ROCK("rock"),
    DRY_BUSH("dryBush"),
    T_REX_SKULL("tRexSkull"),
    WALL("wall");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
