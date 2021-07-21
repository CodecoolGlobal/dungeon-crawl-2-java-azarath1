package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Weapon;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    int CANVAS_WIDTH = 20;
    int CANVAS_HEIGHT = 15;
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            CANVAS_WIDTH * Tiles.TILE_WIDTH,
            CANVAS_HEIGHT * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(20));
        ui.setVgap(2);

        ui.add(new Label(""), 1, 1);
        ui.add(healthLabel, 1, 1);
        ui.add(new Label("Inventory: "), 1, 3);
        ui.add(inventoryLabel,1,3);


        //Pick up Button

        Button submit = new Button("Pick Up");
        ui.add(submit, 1, 5);
        submit.setFocusTraversable(false);
        submit.setOnAction(this::handle);

        BorderPane borderPane = new BorderPane();

        borderPane.setCenter(canvas);
        borderPane.setLeft(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Dungeon Crawl");
        primaryStage.show();
    }

    private void handle(ActionEvent actionEvent) {
        if(map.getCell(map.getPlayer().getX(),map.getPlayer().getY()).getType() == CellType.WEAPON){
            map.getPlayer().addToInventory(map.getCell(map.getPlayer().getX(),map.getPlayer().getY()).getDefaultActor());
            map.getCell(map.getPlayer().getX(),map.getPlayer().getY()).setType(CellType.FLOOR);
        }
        if(map.getCell(map.getPlayer().getX(),map.getPlayer().getY()).getType() == CellType.KEY){
            map.getPlayer().addToInventory(map.getCell(map.getPlayer().getX(),map.getPlayer().getY()).getDefaultActor());
            map.getCell(map.getPlayer().getX(),map.getPlayer().getY()).setType(CellType.FLOOR);
        }
        inventoryLabel.setText("");
        inventoryLabel.setText("Inventory: "+map.getPlayer().getInventoryString());
    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                map.getPlayer().attack(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                map.getPlayer().attack(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                map.getPlayer().attack(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1,0);
                map.getPlayer().attack(1, 0);
                refresh();
                break;
        }
    }

    private void refresh() {
        int startX = map.getPlayer().getX() - CANVAS_WIDTH / 2;
        if (startX < 0) startX = 0;
        if (startX + CANVAS_WIDTH >= map.getWidth()) startX = map.getWidth() - CANVAS_WIDTH;
        int endX = startX + CANVAS_WIDTH;
        int startY = map.getPlayer().getY() - CANVAS_HEIGHT / 2;
        if (startY < 0) startY = 0;
        if (startY + CANVAS_HEIGHT >= map.getHeight()) startY = map.getHeight() - CANVAS_HEIGHT;
        int endY = startY + CANVAS_HEIGHT;
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        List<Actor> actors = new ArrayList<>();
        for (int i = 0; i < map.getWidth(); i++)
            for (int j = 0; j < map.getHeight(); j++)
                if (map.getCell(i, j).getActor() != null)
                    actors.add(map.getCell(i, j).getActor());
        for (Actor a : actors)
            a.update();
        int canvasX = 0;
        for (int x = startX; x < endX; x++) {
            int canvasY = 0;
            for (int y = startY; y < endY; y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y, canvasX, canvasY);
                } else {
                    Tiles.drawTile(context, cell, x, y, canvasX, canvasY);
                }
                canvasY++;
            }
            canvasX++;
        }
        healthLabel.setText("Health: " + map.getPlayer().getHealth());
    }
}
