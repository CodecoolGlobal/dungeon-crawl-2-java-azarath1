package com.codecool.dungeoncrawl.logic.props;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Characters;

public class Door extends Items {
    private boolean isOpen = false;
    private String name;

    public Door(Cell cell) {
        super(cell);
        this.cell.setDoor(this);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public String getTileName() {
        return "door";
    }
}
