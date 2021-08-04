package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.Drawable;
import com.codecool.dungeoncrawl.logic.props.Weapon;

import java.util.ArrayList;
import java.util.List;

public abstract class Actor implements Drawable {

    protected Cell cell;
    private String name;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
        this.cell.setDefaultActor(this);
    }
    public void update() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}