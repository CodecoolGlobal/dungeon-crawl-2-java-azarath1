package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Actor;

import java.util.ArrayList;

public class Cell implements Drawable {
    private CellType type;
    private Actor actor;
    private Actor defaultActor;
    private GameMap gameMap;
    private int x, y;

    Cell(GameMap gameMap, int x, int y, CellType type) {
        this.gameMap = gameMap;
        this.x = x;
        this.y = y;
        this.type = type;

    }

    public CellType getType() {
        return type;
    }

    public Actor getDefaultActor() {
        return defaultActor;
    }

    public void setDefaultActor(Actor defaultActor) {
        this.defaultActor = defaultActor;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Actor getActor() {
        return actor;
    }

    public Cell getNeighbor(int dx, int dy) {
        return gameMap.getCell(x + dx, y + dy);
    }

    public ArrayList<Cell> getEmptyFloorCells() {
        Cell[][] cells = gameMap.getCells();
        ArrayList<Cell> emptyFloorCells = new ArrayList<>();
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                Cell currentCell = cells[i][j];
                if ((currentCell.getType() == CellType.FLOOR) && (currentCell.getActor() == null)) {
                    emptyFloorCells.add(currentCell);
                }
            }
        }
        return emptyFloorCells;
    }


    @Override
    public String getTileName() {
        return type.getTileName();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
