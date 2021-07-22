package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;

import java.util.List;

public abstract class Actor implements Drawable {
    protected Cell cell;
    private String name;
    private int health = 10;
    private int damage = 5;
    private int turnCount = 0;
    private boolean enemy = false;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.cell.setDefaultActor(this);
    }



    public void move(int dx, int dy) {
        turnCount += 1;
        Cell nextCell = cell.getNeighbor(dx, dy);
        if(this.getTileName().equals("necromancer") && turnCount % 3 == 0){
//            Cell nextCellToTeleport = cell.getTeleportLocation(dx, dy);
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if(!checkCollisionWithWall(nextCell) && !checkCollisionWithMonster(nextCell) && !this.getTileName().equals("ghost")) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }else if(this.getTileName().equals("ghost")){
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void move(Cell targetCell){
        cell.setActor(null);
        targetCell.setActor(this);
        cell = targetCell;
    }


    public void update() {}

    public String getName() {
        return name;
    }

    public boolean checkCollisionWithWall(Cell nextCell){
        return nextCell.getTileName().equals("wall");
    }

    public boolean checkCollisionWithMonster(Cell nextCell){
        List<String> monsters = List.of("spider", "ghost", "skeleton");
        if(nextCell.getActor() != null){
            String actorType = nextCell.getActor().getTileName();
            return monsters.contains(actorType);
        }
        return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public void attack(int x, int y) {
        Actor target = cell.getNeighbor(x, y).getActor();
        if (target != null && target.isEnemy() != this.isEnemy()) target.damageDone(damage);
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void damageDone(int damage) {
        health -= damage;
        if (health <= 0) death();
    }

    public boolean isEnemy() { return enemy; }

    public void setEnemy() {
        this.enemy = true;
    }

    public void death(){
//        getCell().setActor(null);
    }
}