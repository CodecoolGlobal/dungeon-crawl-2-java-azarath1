package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;

public class Player extends Characters {
    ArrayList<Actor> inventory = new ArrayList<>();

    public Player(Cell cell) {
        super(cell);
        this.setHealth((int) (Math.random() * 10 + 5));
        this.setDamage(5);
    }

    public String getTileName() {
        return "player";
    }
}
