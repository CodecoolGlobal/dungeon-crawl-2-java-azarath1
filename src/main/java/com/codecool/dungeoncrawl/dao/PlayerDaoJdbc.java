package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoJdbc implements PlayerDao {
    private DataSource dataSource;
    private int lastId;

    public PlayerDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(PlayerModel player) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO player (player_name, hp, x, y) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, player.getPlayerName());
            statement.setInt(2, player.getHp());
            statement.setInt(3, player.getX());
            statement.setInt(4, player.getY());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            player.setId(resultSet.getInt(1));
            lastId = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PlayerModel player) {

    }

    @Override
    public PlayerModel get(int id) {

        return null;
    }

    @Override
    public List<PlayerModel> getAll() {
        List<PlayerModel> tmp = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            String sql = "SELECT id, player_name,hp,x,y FROM player";
            Statement prep = conn.createStatement();
            ResultSet rs = prep.executeQuery(sql);
            while (rs.next()) {
                PlayerModel tplayer = new PlayerModel(rs.getInt("id"),rs.getString("player_name"),rs.getInt("x"),rs.getInt("y"),rs.getInt("hp"));
                tmp.add(tplayer);
            }
            return tmp;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getLastId() {
        return lastId;
    }

}
