package com.codecool.dungeoncrawl.logic;

import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.actors.Skeleton;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    GameMap gameMap;


    @BeforeEach
    void setUp() {
        gameMap = new GameMap(3, 3, CellType.FLOOR);
    }

    @Test
    void moveUpdatesCells() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.move(1, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
        assertEquals(null, gameMap.getCell(1, 1).getActor());
        assertEquals(player, gameMap.getCell(2, 1).getActor());
    }

    @Test
    void cannotMoveIntoWall() {
        Player player = new Player(gameMap.getCell(1, 1));
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
        Player player = new Player(gameMap.getCell(1, 1));
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
        Player player = new Player(gameMap.getCell(1, 1));
        gameMap.getCell(2, 1).setType(CellType.FENCE);
        player.move(1, 0);
        assertEquals(1, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void godModeMoveThroughWalls() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setGodMode();
        gameMap.getCell(2, 1).setType(CellType.WALL);
        player.move(1, 0);
        assertEquals(2, player.getX());
        assertEquals(1, player.getY());
    }

    @Test
    void playerIsInGodMode() {
        Player player = new Player(gameMap.getCell(1, 1));
        player.setGodMode();
        assertTrue(player.isGodMode());
    }


}