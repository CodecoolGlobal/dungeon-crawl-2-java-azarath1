package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.props.Items;

import java.util.ArrayList;

public class Player extends Characters {
    private String name;

    public Player(Cell cell) {
        super(cell);
        this.setHealth((int) (Math.random() * 10 + 5));
        this.setDamage(5);
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getTileName() {
        return "player";
    }
}
