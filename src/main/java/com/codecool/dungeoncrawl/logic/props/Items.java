package com.codecool.dungeoncrawl.logic.props;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.actors.Actor;

public abstract class Items extends Actor {
    public Items(Cell cell) {
        super(cell);
        this.cell = cell;
        this.cell.setItem(this);

    }

}
