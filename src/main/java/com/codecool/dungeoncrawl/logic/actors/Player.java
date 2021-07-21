package com.codecool.dungeoncrawl.logic.actors;

import com.codecool.dungeoncrawl.logic.Cell;

import java.util.ArrayList;

public class Player extends Actor {
    ArrayList<Actor>inventory = new ArrayList<>();
    public Player(Cell cell) {
        super(cell);
        this.setHealth((int) (Math.random() * 10 + 5));
        this.setDamage(5);
    }
    public void addToInventory(Actor item){
        inventory.add(item);
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println(inventory.get(i).getName());
        }
    }
    public String getInventoryString(){
        String content = "";
        for (int i = 0; i < inventory.size(); i++) {
            if(i == inventory.size()){
                content= content + inventory.get(i).getName();
            }else{
                content= content + inventory.get(i).getName()+ ", ";
            }

        }
        return content;
    }
    public String getTileName() {
        return "player";
    }
}
