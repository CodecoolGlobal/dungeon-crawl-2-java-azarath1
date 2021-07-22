package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Key extends Actor {
    private String name;

    public Key(Cell cell, String name) {
        super(cell);
        setName(name);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTileName() {
        return "key";
    }
}
