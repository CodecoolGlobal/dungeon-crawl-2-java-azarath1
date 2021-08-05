package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.props.Items;
import com.codecool.dungeoncrawl.logic.props.Key;
import com.codecool.dungeoncrawl.logic.props.Weapon;
import com.codecool.dungeoncrawl.model.InventoryModel;

import java.util.ArrayList;
import java.util.List;

public class Player extends Characters {
    private String name;

    public Player(Cell cell) {
        super(cell);
        this.setHealth((int) (Math.random() * 10 + 5));
        this.setDamage(5);
    }
    public ArrayList<Items> reConstructInventory(List<InventoryModel> incInventory){
        ArrayList<Items> newInventory = new ArrayList<>();
        for (int i = 0; i < incInventory.size(); i++) {
            if (incInventory.get(i).getObjectname() == "Weapon"){
                newInventory.add(new Weapon(incInventory.get(i).getItem_name(),incInventory.get(i).getAmount()));
            }else{
                newInventory.add(new Key(incInventory.get(i).getItem_name()));
            }

        }
        return newInventory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getTileName() {
        return "player";
    }
}
