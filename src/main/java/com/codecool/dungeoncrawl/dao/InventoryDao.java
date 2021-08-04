package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.props.Items;
import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import java.util.ArrayList;
import java.util.List;

public interface InventoryDao {
    void add(InventoryModel inventory);

    void update(PlayerModel player);

    PlayerModel get(int id);

    List<PlayerModel> getAll();
}
