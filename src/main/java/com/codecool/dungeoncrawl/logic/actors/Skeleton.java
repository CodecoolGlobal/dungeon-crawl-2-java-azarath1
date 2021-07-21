package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;

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

    @Override
    public void update() {
        int[][] directions = { {-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : directions) {
            Actor target = getCell().getNeighbor(direction[0], direction[1]).getActor();
            if (target != null && target.getClass() == Player.class) {
                attack(direction[0], direction[1]);
                break;
            }
        }
    }
}
