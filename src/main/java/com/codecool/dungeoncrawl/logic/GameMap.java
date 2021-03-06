package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.props.Items;

public class GameMap {
    private int width;
    private int height;
    private Cell[][] cells;

    private Player player;

    public GameMap(int width, int height, CellType defaultCellType) {
        this.width = width;
        this.height = height;
        cells = new Cell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = new Cell(this, x, y, defaultCellType);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean nextLevel() {
        Cell characterCell = player.getCell();
        if (characterCell.getType() == CellType.STAIRS) {
            return true;
        } else return false;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isOnEndTile() {
        Cell characterCell = player.getCell();
        if (characterCell.getType() == CellType.EXIT) {
            return true;
        } else return false;
    }

    public boolean isAlive() {
        if (player.getHealth() < 1) {
            return false;
        } else return true;
    }
}


// notes
//    public ArrayList<ArrayList<Integer>> getFloorCells() {
//        ArrayList<ArrayList<Integer>> emptyFloorCellCoordinates = new ArrayList<>();
//        for (int i = 0; i < cells.length; i++) {
//            for (int j = 0; j < cells[0].length; j++) {
//                if(cells[i][j].getType().getTileName().equals("floor") && cells[i][j].getActor() == null){
//                    ArrayList <Integer> coordinatesToAdd = new ArrayList<>();
//                    coordinatesToAdd.add(cells[i][j].getX());
//                    coordinatesToAdd.add(cells[i][j].getY());
//                    emptyFloorCellCoordinates.add(coordinatesToAdd);
//                }
//            }
//        }
//        System.out.print(emptyFloorCellCoordinates);
//        return emptyFloorCellCoordinates;
//    }