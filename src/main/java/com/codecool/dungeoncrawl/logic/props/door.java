package com.codecool.dungeoncrawl.logic.props;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public class door extends Actor {
    private boolean isOpen = false;
    private String name;

    public door(Cell cell) {
        super(cell);
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
