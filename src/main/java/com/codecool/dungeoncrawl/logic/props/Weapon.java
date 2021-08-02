package com.codecool.dungeoncrawl.logic.props;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

import java.util.Random;

public class Weapon extends Items {
    private String name;
    private int Damage;
    Random random = new Random();

    public Weapon(Cell cell, String name) {
        super(cell);
        setName(name);
        setDamage(random.nextInt(10));
    }

    public int getDamage() {
        return Damage;
    }

    public void setDamage(int damage) {
        Damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTileName() {
        return "weapon";
    }
}
