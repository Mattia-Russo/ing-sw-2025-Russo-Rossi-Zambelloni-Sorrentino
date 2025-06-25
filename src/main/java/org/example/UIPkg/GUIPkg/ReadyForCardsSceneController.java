package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ForView.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReadyForCardsSceneController extends GuiController implements Initializable {

    private static final String COMPONENT_JSON_PATH = "/org.example/JsonPkg/tiles.json";

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView flightboardImageView;

    @FXML
    private ImageView shipboardImageView;

    @FXML
    private Pane shipboardContainer;

    @FXML
    private Button showPlayer1ShipboardButton;

    @FXML
    private Button showPlayer2ShipboardButton;

    @FXML
    private Button showPlayer3ShipboardButton;

    @FXML
    private Button showOwnShipboardButton;

    @FXML
    private Label statusMessage;

    private GameViewCache gameViewCache;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        statusMessage.setText("Waiting for cards phase to begin...");
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });

        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);
    }

    @Override
    public void setGui(GUI guiRoot) {
        super.setGui(guiRoot);
        this.gameViewCache = null;
    }

    public void updateGui(GameView game) {
        if (gameViewCache == null) {
            gameViewCache = new GameViewCache(getGuiRoot().getClient().getPlayerName());
        }

        if (game.getException() != null) {
            Platform.runLater(() -> {
                statusMessage.setText(game.getException().getMessage());
                statusMessage.setVisible(true);
            });
            return;
        }

        GameViewCache.GameViewDifferences differences = gameViewCache.compareAndUpdate(game);

        if (differences.hasChanges()) {
            Platform.runLater(() -> {
                if (!differences.getNewShipboardComponents().isEmpty()) {
                    updateShipBoardGUI(differences.getNewShipboardComponents());
                }
                updatePlayerShipboardButtons(game);
            });
        }
    }

    @Override
    public void loadShipboardImage() {
        try {
            InputStream imageStream;
            int shipBoardLevel = getGuiRoot().getShipBoardLevel();

            if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
                GameView cachedGame = gameViewCache.getCachedGameView();
                shipBoardLevel = cachedGame.getShipBoardLevel();
            }

            if(shipBoardLevel == 1) {
                imageStream = getClass().getResourceAsStream("/org.example/cardboard/cardboard-1.jpg");
            } else {
                imageStream = getClass().getResourceAsStream("/org.example/cardboard/cardboard-1b.jpg");
            }

            if (imageStream == null) {
                throw new IllegalArgumentException("Shipboard image not found!");
            }

            Image shipboardImage = new Image(imageStream);
            shipboardImageView.setImage(shipboardImage);
            shipboardImageView.setFitWidth(400);
            shipboardImageView.setFitHeight(300);
            shipboardImageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Error loading shipboard image: " + e.getMessage());
        }
    }

    @Override
    public void loadFlightBoardImage() {
        try {
            InputStream imageStream;
            if(getGuiRoot().getGameMode()==0){
                imageStream = getClass().getResourceAsStream("/org.example/cardboard/cardboard-3.png");
            } else {
                imageStream = getClass().getResourceAsStream("/org.example/cardboard/cardboard-5.png");
            }

            if (imageStream == null) {
                throw new IllegalArgumentException("Flightboard image not found!");
            }

            Image flightboardImage = new Image(imageStream);
            flightboardImageView.setImage(flightboardImage);
            flightboardImageView.setFitWidth(600);
            flightboardImageView.setFitHeight(400);
            flightboardImageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Error loading flightboard image: " + e.getMessage());
        }
    }

    private void updateShipBoardGUI(List<ComponentsView> newComponents) {
        for (ComponentsView component : newComponents) {
            placeComponentOnShipboard(component, component.getPosX(), component.getPosY());
        }
    }

    private void placeComponentOnShipboard(ComponentsView component, int x, int y) {
        try {
            JSONObject componentJson = findComponentJsonById(String.valueOf(component.getId()));
            if (componentJson == null) {
                System.err.println("Component with ID " + component.getId() + " not found in JSON.");
                return;
            }

            String imagePath = componentJson.getString("img");
            Direction direction = component.getDirection();
            placeImageOnShipboard(imagePath, x, y, direction);
        } catch (Exception e) {
            System.err.println("Error placing component on shipboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void placeImageOnShipboard(String imagePath, int x, int y, Direction direction) {
        try {
            x = x - 4;
            y = y - 5;

            double cellWidth = shipboardImageView.getFitWidth() / 7.32;
            double cellHeight = shipboardImageView.getFitHeight() / 5.52;

            double posX = x * cellWidth;
            double posY = y * cellHeight;

            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.err.println("Image not found: " + imagePath);
                return;
            }

            Image componentImage = new Image(imageStream);
            ImageView componentImageView = new ImageView(componentImage);

            componentImageView.setFitWidth(cellWidth * 0.94);
            componentImageView.setFitHeight(cellHeight * 0.94);
            componentImageView.setPreserveRatio(true);

            componentImageView.setX(posX + (cellWidth * 0.2));
            componentImageView.setY(posY + (cellHeight * 0.2));

            rotate(direction, componentImageView);

            Platform.runLater(() -> shipboardContainer.getChildren().add(componentImageView));

        } catch (Exception e) {
            System.err.println("Error positioning image on shipboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void rotate(Direction direction, ImageView imageView) {
        switch (direction) {
            case NORTH: imageView.setRotate(0); break;
            case WEST: imageView.setRotate(-90); break;
            case EAST: imageView.setRotate(90); break;
            case SOUTH: imageView.setRotate(180); break;
            default: imageView.setRotate(0); break;
        }
    }

    private JSONObject findComponentJsonById(String componentId) {
        try (InputStream is = getClass().getResourceAsStream(COMPONENT_JSON_PATH)) {
            if (is == null) {
                System.err.println("JSON file not found: " + COMPONENT_JSON_PATH);
                return null;
            }

            JSONArray jsonArray = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (String.valueOf(json.getInt("id")).equals(componentId)) {
                    return json;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
        return null;
    }

    public void updatePlayerShipboardButtons(GameView game) {
        Platform.runLater(() -> {
            String currentPlayerName = getGuiRoot().getClient().getPlayerName();
            List<PlayerView> players = game.getPlayers();

            showPlayer1ShipboardButton.setVisible(false);
            showPlayer2ShipboardButton.setVisible(false);
            showPlayer3ShipboardButton.setVisible(false);

            int buttonIndex = 0;
            Button[] buttons = {showPlayer1ShipboardButton, showPlayer2ShipboardButton, showPlayer3ShipboardButton};

            for (PlayerView player : players) {
                if (!player.getName().equals(currentPlayerName) && buttonIndex < 3) {
                    buttons[buttonIndex].setText("Show " + player.getName() + "'s Shipboard");
                    buttons[buttonIndex].setVisible(true);
                    buttonIndex++;
                }
            }
        });
    }

    @FXML
    public void onShowPlayer1Shipboard() {
        showPlayerShipboard(getPlayerNameFromButton(showPlayer1ShipboardButton));
    }

    @FXML
    public void onShowPlayer2Shipboard() {
        showPlayerShipboard(getPlayerNameFromButton(showPlayer2ShipboardButton));
    }

    @FXML
    public void onShowPlayer3Shipboard() {
        showPlayerShipboard(getPlayerNameFromButton(showPlayer3ShipboardButton));
    }

    @FXML
    public void onShowOwnShipboard() {
        showPlayerShipboard(getGuiRoot().getClient().getPlayerName());
        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);

        if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
            updatePlayerShipboardButtons(gameViewCache.getCachedGameView());
        }
    }

    private String getPlayerNameFromButton(Button button) {
        String buttonText = button.getText();
        return buttonText.substring(5, buttonText.indexOf("'s Shipboard"));
    }

    private void showPlayerShipboard(String playerName) {
        if (gameViewCache == null || !gameViewCache.hasCachedGameView()) {
            System.err.println("No GameView available to show shipboard");
            return;
        }

        GameView cachedGame = gameViewCache.getCachedGameView();
        if (cachedGame.getException() != null) {
            return;
        }

        PlayerView targetPlayer = null;
        for (PlayerView player : cachedGame.getPlayers()) {
            if (player.getName().equals(playerName)) {
                targetPlayer = player;
                break;
            }
        }

        if (targetPlayer == null) {
            System.err.println("Player " + playerName + " not found");
            return;
        }

        Platform.runLater(() -> {
            shipboardContainer.getChildren().clear();
            shipboardContainer.getChildren().add(shipboardImageView);
        });

        if (targetPlayer.getShipboardView() != null) {
            List<ComponentsView> playerComponents = getShipboardComponents(targetPlayer.getShipboardView());
            updateShipBoardGUI(playerComponents);
        }

        if (!playerName.equals(getGuiRoot().getClient().getPlayerName())) {
            showOwnShipboardButton.setVisible(true);
            showOwnShipboardButton.setDisable(false);
            statusMessage.setText("Now showing " + playerName + "'s shipboard");
        }
    }
}
