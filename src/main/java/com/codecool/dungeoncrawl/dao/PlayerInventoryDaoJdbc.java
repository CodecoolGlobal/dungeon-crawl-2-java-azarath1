package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.logic.props.Items;
import com.codecool.dungeoncrawl.model.InventoryModel;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerInventoryDaoJdbc implements InventoryDao {
    private DataSource dataSource;

    public PlayerInventoryDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(InventoryModel inventory) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO inventory ( item_name, player_id, object_name, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, inventory.getItem_name());
            statement.setInt(2, getLastId());
            statement.setString(3, inventory.getObjectname());
            statement.setInt(4, inventory.getAmount());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            inventory.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    public int getLastId() {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id FROM player ORDER BY id DESC limit 1";
            Statement prep = conn.createStatement();
            ResultSet rs = prep.executeQuery(sql);
            rs.next();
            return rs.getInt("id");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PlayerModel get(int id) {
        return null;
    }

    @Override
    public List<InventoryModel> getAll(int id) {
        List<InventoryModel> inventoryContent = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT item_name, object_name, amount from player join inventory i on player.id = i.player_id WHERE player.id = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()){
                InventoryModel im = new InventoryModel(rs.getString("item_name"),rs.getString("object_name"),rs.getInt("amount"));
                inventoryContent.add(im);
            }
            return inventoryContent;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
