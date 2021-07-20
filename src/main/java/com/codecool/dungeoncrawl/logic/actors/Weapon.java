package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Weapon extends Actor{
    private String name;
    public Weapon(Cell cell,String name) {
        super(cell);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
