package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.List;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(!checkCollisionWithWall(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public boolean checkCollisionWithWall(Cell nextCell){
        return nextCell.getTileName().equals("wall");
    }

    public boolean checkCollisionWithMonster(Cell nextCell){
        List<String> monsters = List.of("spider", "ghost", "skeleton");
        if(nextCell.getActor() != null){
            String actorType = nextCell.getActor().getTileName();
            System.out.println(nextCell.getActor().getTileName());
            return monsters.contains(actorType);
        }
        return false;
    }

    public int getHealth() {
        return health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }
}