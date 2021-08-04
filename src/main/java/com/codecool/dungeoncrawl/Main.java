package com.codecool.dungeoncrawl;

import com.codecool.dungeoncrawl.dao.GameDatabaseManager;

import com.codecool.dungeoncrawl.logic.Cell;
import com.codecool.dungeoncrawl.logic.CellType;
import com.codecool.dungeoncrawl.logic.actors.Player;
import com.codecool.dungeoncrawl.logic.GameMap;
import com.codecool.dungeoncrawl.logic.MapLoader;
import com.codecool.dungeoncrawl.logic.actors.Actor;
import com.codecool.dungeoncrawl.logic.props.Items;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;
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
    GameDatabaseManager db = new GameDatabaseManager();
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
    GridPane ui = new GridPane();
    int mapNum = 0;

    public Main() throws SQLException {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
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
        Button loadButton = new Button("Load Game");
        mainMenu.add(startButton, 0, 3);
        mainMenu.add(loadButton, 0, 4);
        startButton.setAlignment(Pos.CENTER);
        startButton.setFocusTraversable(false);
        loadButton.setAlignment(Pos.CENTER);
        loadButton.setFocusTraversable(false);
        menu = new Scene(mainMenu, 600, 630);
        primaryStage.setScene(menu);

        //GAME LEVEL
        //*GridPane
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
        loadButton.setOnAction(actionEvent -> {
            Stage confirmWindow = new Stage();
            confirmWindow.initModality(Modality.APPLICATION_MODAL);
            confirmWindow.setTitle("Load Game");
            Label loadLabel = new Label();
            loadLabel.setText("Select a save:");
            Button loadBtn = new Button("Load");
            Button cancelBtn = new Button("Cancel");
            ListView<String> saveContainers = new ListView<>();
            saveContainers.getItems().addAll("Test1", "Test2");
            saveContainers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            //Button events
            loadBtn.setOnAction(value -> {
                ObservableList<String> options = saveContainers.getSelectionModel().getSelectedItems();

            });
            //Button events
            cancelBtn.setOnAction(e -> {
                clearVision();
                confirmWindow.close();
            });

            //set stage modal - add elements to stage set scene etc...
            VBox layout = new VBox(10);
            layout.getChildren().addAll(loadLabel, saveContainers, loadBtn, cancelBtn);
            layout.setAlignment(Pos.CENTER);
            Scene scene1 = new Scene(layout, 500, 300);
            confirmWindow.setScene(scene1);
            confirmWindow.showAndWait();
        });
        //*Game Scene
        Scene scene = new Scene(borderPane);
        startButton.setOnAction(e -> {
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
                map.getPlayer().setName(CHAR_NAME);
                modeLabel.setText("CHEAT MODE ON");
                ui.add(modeLabel, 1, 9);
                modeLabel.setTextFill(Color.web("#FF00E8", 0.9));
                refresh();
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (saveShortcut.match(event)) {
                saveModal();
            }
        });

        refresh();
        scene.setOnKeyPressed(this::onKeyPressed);
        primaryStage.setTitle("Dungeons & Demos");
        primaryStage.show();
    }

    // Clearing blur effect on canvas and panes
    private void clearVision() {
        ColorAdjust adjZero = new ColorAdjust(0, 0, 0, 0);
        GaussianBlur blurOff = new GaussianBlur(0);
        adjZero.setInput(blurOff);
        canvas.setEffect(adjZero);
        ui.setEffect(adjZero);
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
        shoutxy();
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

    //SAVING MODAL
    private void saveModal() {
        //Background modifier for blur effect
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        canvas.setEffect(adj);
        ui.setEffect(adj);

        //new modal creation
        Stage confirmWindow = new Stage();
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.setTitle("Save Game");
        Label saveLabel = new Label();
        saveLabel.setText("Save your progress:");
        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");
        TextField saveName = new TextField();

        //Button events
        confirmWindow.setOnCloseRequest(e -> {
            clearVision();
            confirmWindow.close();
        });

        saveBtn.setOnAction(value -> {
            map.getPlayer().setName(nameLabel.getText());
            map.getPlayer().setInventory(map.getPlayer().getInventory());
            mapNum = MapLoader.getCounter();
            try {
                db.saveGameState(getCurrentMap(mapNum), db.savePlayer(map.getPlayer()));
                tests(map.getPlayer().getInventory());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            clearVision();
            confirmWindow.close();
        });

        cancelBtn.setOnAction(e -> {
            clearVision();
            confirmWindow.close();
        });

        //set stage modal - add elements to stage set scene etc...
        VBox layout = new VBox(10);
        layout.getChildren().addAll(saveLabel, saveName, saveBtn, cancelBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 250, 200);
        confirmWindow.setScene(scene1);
        confirmWindow.showAndWait();
    }

    private String getCurrentMap(int mapNum) {
        if (mapNum == 1) {
            return "/map.txt";
        } else return "/map3.txt";
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

        if (!map.isAlive()) {
            this.deathTrigger = true;
            deathModal();
        }

        if (map.isOnEndTile()) {
            winModal();
        }

        healthLabel.setText("Health: " + map.getPlayer().getHealth());
        inventoryLabel.setText("Inventory: " + map.getPlayer().getInventoryString());
        attackLabel.setText("Strength: " + map.getPlayer().getDamage());
    }

    public void shoutxy() {
        System.out.println(map.getPlayer().getX() + " " + map.getPlayer().getY());
    }

    public void tests(ArrayList<Items> inventory) throws SQLException {
        GameDatabaseManager gm = new GameDatabaseManager();
        gm.saveInventory(inventory);
    }

    private void deathModal() {
        //Background modifier for blur effect
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        adj.setInput(blur);
        context.setEffect(adj);
        ui.setEffect(adj);

        //new modal creation
        Stage deathWindow = new Stage();
        deathWindow.initModality(Modality.APPLICATION_MODAL);
        deathWindow.setTitle("DIED");
        Button terminateBtn = new Button("TERMINATE");

        //Button events
        terminateBtn.setOnAction(e -> {
            clearVision();
            deathWindow.close();
            Platform.exit();

        });
        //set stage modal - add elements to stage set scene etc...
        VBox layout = new VBox(10);
        layout.setBackground(new Background(new BackgroundFill(Color.rgb(100, 0, 0), CornerRadii.EMPTY, Insets.EMPTY)));
        Image deathImg = new Image("File:src/main/resources/skul.png");
        ImageView img = new ImageView(deathImg);
        img.setFitHeight(50);
        img.setPreserveRatio(true);
        layout.getChildren().addAll(terminateBtn, img);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 250, 200);
        deathWindow.setScene(scene1);
        deathWindow.showAndWait();
    }

    private void winModal() {
        //Background modifier for blur effect
        ColorAdjust adj = new ColorAdjust(0, -0.9, -0.5, 0);
        GaussianBlur blur = new GaussianBlur(55);
        adj.setInput(blur);
        context.setEffect(adj);
        ui.setEffect(adj);

        //new modal creation
        Stage winWindow = new Stage();
        winWindow.initModality(Modality.APPLICATION_MODAL);
        winWindow.setTitle("WON");
        Label wonLabel = new Label();
        wonLabel.setText("Nice work! \nYou found the ancient knowledge of ZERO WIDTH NON-JOINER");
        Button noiceBtn = new Button("But, that's nothing!");

        //Button events
        noiceBtn.setOnAction(e -> {
            clearVision();
            winWindow.close();
            Platform.exit();

        });
        //set stage modal - add elements to stage set scene etc...
        VBox layout = new VBox(10);
        layout.getChildren().addAll(wonLabel, noiceBtn);
        layout.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(layout, 600, 200);
        winWindow.setScene(scene1);
        winWindow.showAndWait();
    }
}
