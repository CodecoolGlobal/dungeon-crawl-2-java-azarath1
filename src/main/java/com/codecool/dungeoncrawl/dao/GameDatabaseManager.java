package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.actors.Player;

import com.codecool.dungeoncrawl.logic.props.Items;
import com.codecool.dungeoncrawl.model.InventoryModel;

import com.codecool.dungeoncrawl.model.GameState;

import com.codecool.dungeoncrawl.model.PlayerModel;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDatabaseManager {
    private PlayerDao playerDao;
    private GameStateDao gameStateDao;
    private PlayerInventoryDaoJdbc inventoryDao;

    public GameDatabaseManager() throws SQLException {
        setup();
    }

    public void setup() throws SQLException {
        DataSource dataSource = connect();
        playerDao = new PlayerDaoJdbc(dataSource);
        gameStateDao = new GameStateDaoJdbc(dataSource);
        inventoryDao = new PlayerInventoryDaoJdbc(dataSource);
    }

    public void saveGameState(String currentMap, PlayerModel player) throws SQLException {
        GameState model = new GameState(currentMap, player);
        gameStateDao.add(model);
    }

    public PlayerModel savePlayer(Player player) {
        PlayerModel model = new PlayerModel(player);
        playerDao.add(model);
        return model;


    }

    public void saveInventory(ArrayList<Items> inventory) {
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("Setting up inventory");
            InventoryModel inventoryModel = new InventoryModel(inventory.get(i));
            inventoryDao.add(inventoryModel);
        }

    }
    public List<PlayerModel> getAllPlayer(){
        System.out.println(playerDao.getAll());
        return playerDao.getAll();
    }
    public PlayerModel getPlayer(int id){
        return playerDao.get(id);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        String dbName = System.getenv("PSQL_DB_NAME");
        String user = System.getenv("PSQL_USER_NAME");
        String password = System.getenv("PSQL_PASSWORD");

        dataSource.setDatabaseName(dbName);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        System.out.println("Trying to connect");
        dataSource.getConnection().close();
        System.out.println("Connection ok.");

        return dataSource;
    }
}
