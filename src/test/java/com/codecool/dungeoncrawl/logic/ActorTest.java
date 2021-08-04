package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap;
    Player player;

    @BeforeEach
    void setUp() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
        player = new Player(gameMap.getCell(1, 1));
    }

    @Test
    void moveUpdatesCells() {
        player.move(1, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        gameMap.getCell(2, 1).setType(CellType.WALL);
        player.move(1, 0);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveOutOfMap() {
        Player player = new Player(gameMap.getCell(2, 1));
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void cannotMoveIntoAnotherActor() {
        Skeleton skeleton = new Skeleton(gameMap.getCell(2, 1));
        player.move(1, 0);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
        assertEquals(2, skeleton.getX());
        assertEquals(1, skeleton.getY());
        assertEquals(skeleton, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoFence() {
        gameMap.getCell(2, 1).setType(CellType.FENCE);
        player.move(1, 0);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void godModeMoveThroughWalls() {
        player.setGodMode();
        gameMap.getCell(2, 1).setType(CellType.WALL);
        player.move(1, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void playerIsInGodMode() {
        player.setGodMode();
        assertTrue(player.isGodMode());
    }

    @Test
    void playerSetHealthTo() {
        player.setHealth(999);
        assertEquals(999, player.getHealth());
    }


}