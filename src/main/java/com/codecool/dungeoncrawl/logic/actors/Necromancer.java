package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;

import java.util.ArrayList;

public class Necromancer extends Actor {
    int turnCounter = 0;

    public Necromancer(Cell cell) {
        super(cell);
        this.setEnemy();
        this.setDamage((int) (Math.random() * 2 + 2));
        this.setHealth((int) (Math.random() * 5 + 3));
    }

    @Override
    public String getTileName() {
        return "necromancer";
    }

    @Override
    public void update() {
        boolean attackDone = false;
        int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
        for (int[] direction : directions) {
            Actor target = getCell().getNeighbor(direction[0], direction[1]).getActor();
            if (target != null && target.getClass() == Player.class) {
                attackDone = true;
                attack(direction[0], direction[1]);
                break;
            }
        }
        if (!attackDone) {
            int[] targetPosition = directions[(int) (Math.random() * 4)];
            Cell target = getCell().getNeighbor(targetPosition[0], targetPosition[1]);
            if (target.getActor() == null && target.getType() == CellType.FLOOR)
                move(targetPosition[0], targetPosition[1]);
        }
        if (turnCounter % 3 == 0) {
            teleport();
        }
        turnCounter += 1;
    }

    private void teleport() {
        ArrayList<Cell> emptyFloorCells = cell.getEmptyFloorCells();
        Cell randomCell = emptyFloorCells.get((int) (Math.random() * (emptyFloorCells.size() - 1)));
        move(randomCell);
    }
}

