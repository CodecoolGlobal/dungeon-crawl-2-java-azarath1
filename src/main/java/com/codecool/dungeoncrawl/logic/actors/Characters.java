package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.props.Items;
import com.codecool.dungeoncrawl.logic.props.Weapon;

import java.util.ArrayList;
import java.util.List;

public abstract class Characters extends Actor{
    private int health = 30;
    private int damage = 10;
    private boolean godMode = false;
    private int turnCount = 0;
    private boolean enemy = false;
    private boolean firsthit = false;
    ArrayList<Items> inventory = new ArrayList<>();

    public Characters(Cell cell) {
        super(cell);
        this.cell.setCharacter(this);
    }
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void addToInventory(Items item) {
        inventory.add(item);
    }
    public void removeFromInventory(Items item){
        inventory.remove(item);
    }
    public void attack(int x, int y) {
        Actor target = cell.getNeighbor(x, y).getActor();
        List<String> monsters = List.of("spider", "ghost", "skeleton","necromancer");
        if (!(target instanceof Items)&&target != null && ((Characters) target).isEnemy() != this.isEnemy()) {
            System.out.println("Hit");
            ((Characters) target).damageDone(damage);
            if (monsters.contains(target.getTileName()) && !((Characters) target).isFirsthit()) {
                ((Characters) target).setDamage((((Characters) target).getDamage()) + 3);
                ((Characters) target).setFirsthit(true);

            }

        }
    }
    public void move(int dx, int dy) {
        turnCount += 1;
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (this.getTileName().equals("necromancer") && turnCount % 3 == 0) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (!checkCollisionWithWall(nextCell) && !checkCollisionWithDoor(nextCell) && !checkCollisionWithMonster(nextCell) && !this.getTileName().equals("ghost") && !checkCollisionWithFence(nextCell)) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        } else if (this.getTileName().equals("ghost")) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        else if (checkCollisionWithDoor(nextCell) && nextCell.getDoor().isOpen()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
        else if (checkCollisionWithDoor(nextCell) && getInventoryString().contains("Key") && !nextCell.getDoor().isOpen()) {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
            cell.getDoor().setOpen(true);
           Items removable = inventory.stream()
                    .filter(x -> x.getName() == "Key")
                    .findFirst()
                    .get();
           removeFromInventory(removable);
        }
        else if (godMode && !checkCollisionWithFence(nextCell)) {
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

    public ArrayList<Items> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Items> inventory) {
        this.inventory = inventory;
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
            if(inventory.get(i) instanceof Weapon && ((Weapon) inventory.get(i)).getDamage() > last){
                last = ((Weapon) inventory.get(i)).getDamage();
                System.out.println(last);
            }
        }
        return damage + last;
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
    @Override
    public String getTileName() {
        return "EmptyCharacter";
    }

}
