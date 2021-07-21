package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Drawable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Tiles {
    public static int TILE_WIDTH = 32;

    private static Image tileset = new Image("/tiles.png", 543 * 2, 543 * 2, true, false);
    private static Map<String, Tile> tileMap = new HashMap<>();

    public static class Tile {
        public final int x, y, w, h;

        Tile(int i, int j) {
            x = i * (TILE_WIDTH + 2);
            y = j * (TILE_WIDTH + 2);
            w = TILE_WIDTH;
            h = TILE_WIDTH;
        }
    }

    static {
        //environment
        tileMap.put("empty", new Tile(0, 0));
        tileMap.put("wall", new Tile(10, 17));
        tileMap.put("floor", new Tile(2, 0));
        tileMap.put("fence", new Tile(0, 18));
        //player
        tileMap.put("player", new Tile(27, 0));
        //monsters
        tileMap.put("skeleton", new Tile(29, 6));
        tileMap.put("spider", new Tile(28, 5));
        tileMap.put("ghost", new Tile(27, 6));
        //Items
        tileMap.put("weapon", new Tile(0, 26));
        tileMap.put("key", new Tile(16, 23));
    }

    public static void drawTile(GraphicsContext context, Drawable d, int x, int y, int canvasX, int canvasY) {
        Tile tile = tileMap.get(d.getTileName());
        context.drawImage(tileset, tile.x, tile.y, tile.w, tile.h,
                canvasX * TILE_WIDTH, canvasY * TILE_WIDTH, TILE_WIDTH, TILE_WIDTH);
    }
}
