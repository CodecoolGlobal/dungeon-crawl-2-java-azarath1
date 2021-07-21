package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Ghost extends Actor {
    public Ghost(Cell cell) {
        super(cell);
        this.setEnemy();
        this.setDamage((int) (Math.random() * 4 + 2));
        this.setHealth((int) (Math.random() * 6 + 3));
    }

    @Override
    public String getTileName() {
        return "ghost";
    }
}
