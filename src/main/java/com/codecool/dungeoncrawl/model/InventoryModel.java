package com.codecool.dungeoncrawl.model;

import com.codecool.dungeoncrawl.logic.props.Items;
import com.codecool.dungeoncrawl.logic.props.Key;
import com.codecool.dungeoncrawl.logic.props.Weapon;

public class InventoryModel extends BaseModel{
    private String item_name;
    private String objectname;
    private int amount;

    public InventoryModel(Items item){
        item_name = item.getName();
        objectname = decideObjectType(item);
        amount = decideAmount(item);
    }



    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getObjectname() {
        return objectname;
    }

    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String decideObjectType(Items item){
        if(item instanceof Weapon){
            return "Weapon";
        }else if(item instanceof Key){
            return "Key";
        }else{
            throw new IllegalArgumentException("Not valid item type");

        }
    }
    public int decideAmount(Items item){
        if(item instanceof Weapon){
            return ((Weapon) item).getDamage();
        }else if(item instanceof Key){
            return 1;
        }else{
            throw new IllegalArgumentException("Not valid item type");

        }
    }
}
