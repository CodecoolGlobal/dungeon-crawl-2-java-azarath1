package com.codecool.dungeoncrawl.logic;


import com.codecool.dungeoncrawl.logic.actors.*;
import com.codecool.dungeoncrawl.logic.props.Key;
import com.codecool.dungeoncrawl.logic.props.Weapon;
import com.codecool.dungeoncrawl.logic.props.Door;


import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    private static int counter = 0;

    public static GameMap loadMap() {
        String[] maps = {"/map.txt", "/map3.txt"};
        InputStream is = MapLoader.class.getResourceAsStream(maps[counter]);
        counter++;
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case 'f':
                            cell.setType(CellType.FENCE);
                            break;
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case ';':
                            cell.setType(CellType.WOOD);
                            break;
                        case 'ß':
                            cell.setType(CellType.DRY_BUSH);
                            break;
                        case 'K':
                            cell.setType(CellType.ROCK);
                            break;
                        case 'R':
                            cell.setType(CellType.T_REX_SKULL);
                            break;
                        case 'v':
                            cell.setType(CellType.WATER);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Spider(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Ghost(cell);
                            break;
                        case 'n':
                            cell.setType(CellType.FLOOR);
                            new Necromancer(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            map.setPlayer(new Player(cell));
                            break;
                        case 'e':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'X':
                            cell.setType(CellType.EXIT);
                            break;
                        case 'w':
                            cell.setType(CellType.WEAPON);
                            new Weapon(cell, "Sword");
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            new Key(cell, "Key");
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            new Door(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static GameMap loadMap(Player player) {
        String[] maps = {"/map.txt", "/map3.txt"};
        InputStream is = MapLoader.class.getResourceAsStream(maps[counter]);
        counter++;
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case 'f':
                            cell.setType(CellType.FENCE);
                            break;
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case ';':
                            cell.setType(CellType.WOOD);
                            break;
                        case 'ß':
                            cell.setType(CellType.DRY_BUSH);
                            break;
                        case 'K':
                            cell.setType(CellType.ROCK);
                            break;
                        case 'R':
                            cell.setType(CellType.T_REX_SKULL);
                            break;
                        case 'v':
                            cell.setType(CellType.WATER);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.FLOOR);
                            new Skeleton(cell);
                            break;
                        case 'p':
                            cell.setType(CellType.FLOOR);
                            new Spider(cell);
                            break;
                        case 'g':
                            cell.setType(CellType.FLOOR);
                            new Ghost(cell);
                            break;
                        case 'n':
                            cell.setType(CellType.FLOOR);
                            new Necromancer(cell);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            player.setCell(cell);
                            map.setPlayer(player);
                            break;
                        case 'e':
                            cell.setType(CellType.STAIRS);
                            break;
                        case 'X':
                            cell.setType(CellType.EXIT);
                            break;
                        case 'w':
                            cell.setType(CellType.WEAPON);
                            new Weapon(cell, "Sword");
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            new Key(cell, "Key");
                            break;
                        case 'd':
                            cell.setType(CellType.DOOR);
                            new Door(cell);
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        return map;
    }

    public static int getCounter() {
        return counter;
    }

}
