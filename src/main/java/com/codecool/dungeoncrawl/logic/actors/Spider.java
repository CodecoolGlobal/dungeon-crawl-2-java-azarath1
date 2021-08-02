package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

public class Spider extends Charachters {
    public Spider(Cell cell) {
        super(cell);
        this.setEnemy();
        this.setDamage((int) (Math.random() * 3 + 2));
        this.setHealth((int) (Math.random() * 8 + 4));
    }

    @Override
    public String getTileName() {
        return "spider";
    }
}
