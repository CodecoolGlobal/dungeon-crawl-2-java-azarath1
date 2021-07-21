package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Skeleton extends Actor {
    public Skeleton(Cell cell) {
        super(cell);
        this.setEnemy();
        this.setDamage((int) (Math.random() * 2 + 2));
        this.setHealth((int) (Math.random() * 5 + 3));
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
