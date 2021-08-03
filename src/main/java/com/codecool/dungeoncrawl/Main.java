package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.props.Weapon;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    boolean deathTrigger = false;
    Scene menu;
    Scene deathScene;
    Stage primaryStage;
    String CHAR_NAME;
    int CANVAS_WIDTH = 22;
    int CANVAS_HEIGHT = 17;
    GameMap map = MapLoader.loadMap();
    Canvas canvas = new Canvas(
            CANVAS_WIDTH * Tiles.TILE_WIDTH,
            CANVAS_HEIGHT * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    Label formNameLabel = new Label();
    Label healthLabel = new Label();
    Label inventoryLabel = new Label();
    Label attackLabel = new Label();
    Label nameLabel = new Label();
    Label playerLabel = new Label();
    Label modeLabel = new Label();
    Button pickupBtn = new Button("Pick Up");
    KeyCombination saveShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //MAIN MENU
        GridPane mainMenu = new GridPane();
        mainMenu.setPadding(new Insets(10));
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        mainMenu.setVgap(10);
        Image image = new Image("File:src/main/resources/Dungeon Crawler by.png");
        mainMenu.getChildren().add(new ImageView(image));
        mainMenu.add(formNameLabel, 0, 1);
        formNameLabel.setText("Name Your Character:");
        formNameLabel.setTextFill(Color.web("#5EB500", 0.9));
        TextField userTextField = new TextField();
        userTextField.setAlignment(Pos.CENTER);
        mainMenu.add(userTextField, 0, 2);
        Button startButton = new Button("Start Game");
        mainMenu.add(startButton, 0, 3);
        startButton.setAlignment(Pos.CENTER);
        startButton.setFocusTraversable(false);
        menu = new Scene(mainMenu, 600, 630);
        primaryStage.setScene(menu);

        //GAME LEVEL
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
        ui.add(pickupBtn, 1, 8);
        pickupBtn.setFocusTraversable(false);
        pickupBtn.setOnAction(this::handle);

        //*Borderpane
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setLeft(ui);

        //*Game Scene
        Scene scene = new Scene(borderPane);
        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                CHAR_NAME = userTextField.getText();
                primaryStage.setScene(scene);
                playerLabel.setText("Player: ");
                ui.add(playerLabel, 1, 0);
                playerLabel.setTextFill(Color.web("#872E7F", 0.9));
                nameLabel.setText(CHAR_NAME);
                nameLabel.setTextFill(Color.web("#872E7F", 0.9));
                ui.add(nameLabel, 1, 1);
                if (CHAR_NAME.equals("Konrád") ||
                        CHAR_NAME.equals("Gergő") ||
                        CHAR_NAME.equals("Roli")) {
                    map.getPlayer().setHealth(999);
                    map.getPlayer().setGodMode();
                    modeLabel.setText("CHEAT MODE ON");
                    ui.add(modeLabel, 1, 9);
                    modeLabel.setTextFill(Color.web("#FF00E8", 0.9));
                    refresh();
                }
            }
        });
        //TODO: add ui window for SAVE
        scene.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (saveShortcut.match(event)) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setContentText("This is Save");
                    a.show();
                }
            }

        });

        //Death Scene
        if (deathTrigger) {
            GridPane deathShow = new GridPane();
            deathShow.setPadding(new Insets(10));
            deathShow.setAlignment(Pos.CENTER);
            deathShow.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            deathShow.setVgap(3);
            deathShow.add(new Label("GAME OVER"), 0, 0);
            Button resButton = new Button("Restart");
            deathShow.add(resButton, 0, 1);
            deathShow.setAlignment(Pos.CENTER);
            deathShow.setFocusTraversable(false);
            primaryStage.getScene().setRoot(deathShow);
        }

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeons & Demos");
        primaryStage.show();
    }

    private void handle(ActionEvent actionEvent) {
        if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getType() == CellType.WEAPON) {
            map.getPlayer().addToInventory(map.getPlayer().getCell().getItem());
            map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).setType(CellType.FLOOR);
            map.getPlayer().setDamage(map.getPlayer().getMostPowerfulWeaponAttack());
            refresh();
        }
        if (map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getType() == CellType.KEY) {
            map.getPlayer().addToInventory(map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).getItem());
            map.getCell(map.getPlayer().getX(), map.getPlayer().getY()).setType(CellType.FLOOR);
            refresh();
        }


    }
    //---KEY controls
    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case W:
                map.getPlayer().move(0, -1);
                map.getPlayer().attack(0, -1);
                refresh();
                break;
            case S:
                map.getPlayer().move(0, 1);
                map.getPlayer().attack(0, 1);
                refresh();
                break;
            case A:
                map.getPlayer().move(-1, 0);
                map.getPlayer().attack(-1, 0);
                refresh();
                break;
            case D:
                map.getPlayer().move(1, 0);
                map.getPlayer().attack(1, 0);
                refresh();
                break;
            case SPACE:
                pickupBtn.fire();
                keyEvent.consume();
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
            Player savedPlayer = map.getPlayer();
            map = MapLoader.loadMap(savedPlayer);
            refresh();
        }

        if(!map.isAlive()) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            this.deathTrigger = true;
            a.setContentText("GAME OVER");
            a.show();
        }

        if(map.isOnEndTile()){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("You Won! Congratulations!");
            a.show();
        }

        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        inventoryLabel.setText("Inventory: " + map.getPlayer().getInventoryString());
        attackLabel.setText("Strength: " + map.getPlayer().getDamage());
    }
}
