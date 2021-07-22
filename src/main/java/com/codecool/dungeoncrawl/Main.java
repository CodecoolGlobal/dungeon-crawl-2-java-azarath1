package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.actors.Weapon;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    Scene menu;
    String CHAR_NAME;
    int CANVAS_WIDTH = 22;
    int CANVAS_HEIGHT = 17;
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            CANVAS_WIDTH * Tiles.TILE_WIDTH,
            CANVAS_HEIGHT * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label attackLabel = new Label();
    Label nameLabel = new Label();
    Label playerLabel = new Label();
    Label modeLabel = new Label();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //MAIN MENU
        GridPane mainMenu = new GridPane();
        mainMenu.setPadding(new Insets(10));
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setBackground(new Background(new BackgroundFill(Color.DIMGREY, CornerRadii.EMPTY, Insets.EMPTY)));
        mainMenu.setVgap(10);
        mainMenu.add(new Label("Name Your Character: "), 0, 0);
        TextField userTextField = new TextField();
        userTextField.setAlignment(Pos.CENTER);
        mainMenu.add(userTextField, 0, 1);
        Button startButton = new Button("Start Game");
        mainMenu.add(startButton, 0, 2);
        startButton.setAlignment(Pos.CENTER);
        startButton.setFocusTraversable(false);
        menu = new Scene(mainMenu, 400, 200);
        primaryStage.setScene(menu);

        //GAME LEVEL 1 SCENE
        //*GridPane
        GridPane ui = new GridPane();
        ui.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(20));
        ui.setVgap(4);
        ui.add(new Label(""), 1, 2);
        ui.add(healthLabel, 1, 2);
        healthLabel.setTextFill(Color.web("#5EB500", 0.9));
        ui.add(new Label(""), 1, 4);
        ui.add(attackLabel, 1, 4);
        attackLabel.setTextFill(Color.web("#DF2302", 0.9));
        ui.add(new Label(""), 1, 6);
        ui.add(inventoryLabel, 1, 6);
        inventoryLabel.setTextFill(Color.web("#DF2302", 0.9));

        //*Pick up Button
        Button submit = new Button("Pick Up");
        ui.add(submit, 1, 8);
        submit.setFocusTraversable(false);
        submit.setOnAction(this::handle);

        //*Borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setLeft(ui);

        //*Game Scene LEVEL 1
        Scene scene = new Scene(borderPane);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                CHAR_NAME = userTextField.getText();
                primaryStage.setScene(scene);
                playerLabel.setText("Player: ");
                ui.add(playerLabel, 1, 0);
                playerLabel.setTextFill(Color.web("#F4FF01", 0.9));
                nameLabel.setText(CHAR_NAME);
                nameLabel.setTextFill(Color.web("#F4FF01", 0.9));
                ui.add(nameLabel, 1, 1);
                if (CHAR_NAME.equals("Konrád") ||
                        CHAR_NAME.equals("Gergő") ||
                        CHAR_NAME.equals("Roli")) {
                    map.getPlayer().setHealth(999);
                    map.getPlayer().setGodMode();
                    modeLabel.setText("GOD MODE ON");
                    ui.add(modeLabel, 1, 9);
                    modeLabel.setTextFill(Color.web("#FF00E8", 0.9));
                    refresh();
                }
            }
        });

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeon & Demos");
        primaryStage.show();
    }

    private void handle(ActionEvent actionEvent) {
        if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getType() == CellType.WEAPON) {
            map.getPlayer().addToInventory(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getDefaultActor());
            map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).setType(CellType.FLOOR);
            map.getPlayer().setDamage(map.getPlayer().getMostPowerfulWeaponAttack());
            refresh();
        }
        if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getType() == CellType.KEY) {
            map.getPlayer().addToInventory(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getDefaultActor());
            map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).setType(CellType.FLOOR);
            refresh();
        }


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
                map.getPlayer().move(1, 0);
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

        if (map.nextLevel()) {
            map = MapLoader.loadMap();
            refresh();
        }

        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        inventoryLabel.setText("Inventory: " + map.getPlayer().getInventoryString());
        attackLabel.setText("Strength: " + map.getPlayer().getDamage());
    }
}
