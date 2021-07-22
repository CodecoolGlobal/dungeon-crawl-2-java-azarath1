package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.props.Weapon;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {
    private boolean godMode = false;
    protected Cell cell;
    private String name;
    private int health = 30;
    private int damage = 5;
    private int turnCount = 0;
    private boolean enemy = false;
    private boolean firsthit = false;
    ArrayList<Actor> inventory = new ArrayList<>();

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.cell.setDefaultActor(this);
    }

    public void addToInventory(Actor item) {
        inventory.add(item);
    }

    public String getInventoryString() {
        String content = "";
        for (int i = 0; i < inventory.size(); i++) {
            if (i == inventory.size()) {
                content = content + inventory.get(i).getName();
            } else {
                content = content + inventory.get(i).getName() + ", ";
            }
        }
        return content;
    }


    public void move(int dx, int dy) {
        turnCount += 1;
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (this.getTileName().equals("necromancer") && turnCount % 3 == 0) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (!checkCollisionWithWall(nextCell) && !checkCollisionWithDoor(nextCell) && !checkCollisionWithMonster(nextCell) && !this.getTileName().equals("ghost")) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (this.getTileName().equals("ghost")) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (checkCollisionWithDoor(nextCell) && getInventoryString().contains("Key")) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (godMode && !checkCollisionWithFence(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
    }

    public void move(Cell targetCell) {
        cell.setActor(null);
        targetCell.setActor(this);
        cell = targetCell;
    }

    public ArrayList<Actor> getInventory() {
        return inventory;
    }

    public void update() {
    }

    public String getName() {
        return name;
    }

    public boolean checkCollisionWithFence(Cell nextCell) {
        return nextCell.getTileName().equals("fence");
    }

    public boolean checkCollisionWithWall(Cell nextCell) {
        return nextCell.getTileName().equals("wall");
    }

    public boolean checkCollisionWithDoor(Cell nextCell) {
        return nextCell.getTileName().equals("door");
    }


    public boolean checkCollisionWithMonster(Cell nextCell) {
        List<String> monsters = List.of("spider", "ghost", "skeleton");
        if (nextCell.getActor() != null) {
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

    public void setCell(Cell newCell) {
        cell = newCell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public void attack(int x, int y) {
        Actor target = cell.getNeighbor(x, y).getActor();
        List<String> monsters = List.of("spider", "ghost", "skeleton");
        if (target != null && target.isEnemy() != this.isEnemy()) {
            target.damageDone(damage);
            if (monsters.contains(target.getTileName()) && !target.isFirsthit()) {
                target.setDamage((target.getDamage()) + 3);
                target.setFirsthit(true);

            }

        }
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

    public int getMostPowerfulWeaponAttack() {
        int last = 0;
        for (int i = 0; i < inventory.size(); i++) {

            if (inventory.get(i) instanceof Weapon && last < inventory.get(i).getDamage()) {
                last = inventory.get(i).getDamage();
            }
        }
        return damage + last;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public void setEnemy() {
        this.enemy = true;
    }

    public void death() {
        getCell().setActor(null);
    }


    public boolean isGodMode() {
        return godMode;
    }

    public void setGodMode() {
        this.godMode = true;
    }

    public boolean isFirsthit() {
        return firsthit;
    }

    public void setFirsthit(boolean firsthit) {
        this.firsthit = firsthit;
    }
}