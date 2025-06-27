package org.example.UIPkg.GUIPkg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ForView.*;
import org.example.ServerPkg.Model.Points;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class ChangeGoodsSceneController extends GuiController implements Initializable {

    private static final String CARDS_JSON_PATH = "/org.example/JsonPkg/cards.json";

    @FXML
    private ImageView currentCardImageView;
    // UI Elements
    @FXML
    private BorderPane borderPane;
    @FXML
    private ImageView shipboardImageView;
    @FXML
    private Pane shipboardContainer;
    @FXML
    private ImageView flightboardImageView;
    @FXML
    private Pane flightboardContainer;

    // Coordinate Input
    @FXML
    private TextField xCoordinateField;
    @FXML
    private TextField yCoordinateField;
    @FXML
    private TextField goodPositionField;

    // Action Buttons
    @FXML
    private Button removeGoodButton;
    @FXML
    private Button addGoodButton;
    @FXML
    private Button endChangeGoodsButton;

    // Player Shipboard View Controls
    @FXML
    private Button showPlayer1ShipboardButton;
    @FXML
    private Button showPlayer2ShipboardButton;
    @FXML
    private Button showPlayer3ShipboardButton;
    @FXML
    private Button showOwnShipboardButton;

    @FXML
    private Button abandonGameButton;
    // Status Messages
    @FXML
    private Label statusMessage;
    @FXML
    private Label validationMessage;

    // Goods Display
    @FXML
    private VBox currentGoodsBox;

    // State Management
    private boolean isViewingOtherPlayerShipboard = false;
    private List<Button> allButtons;
    private List<Boolean> previousButtonStates;
    private final List<Points> storageComponents = new ArrayList<>();
    private String currentlyViewedPlayer = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        validationMessage.setVisible(false);
        statusMessage.setText("Select storage coordinates and good position to manage goods");

        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);

        allButtons = Arrays.asList(removeGoodButton, addGoodButton, endChangeGoodsButton);
        saveButtonStates();
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });
    }

    @Override
    public void setUp(GameView game) {
        Platform.runLater(() -> {
            updateGui(game);
            loadShipboardImage();
            loadFlightboardImage();
            loadCurrentCard(game);
            resetShowShipboardButtons();

            if(game.getException()!=null) {
                showValidationError(game.getException().getMessage());
            }

            if (game != null && game.getPlayers() != null) {
                for (PlayerView player : game.getPlayers()) {
                    if (player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                        loadShipboardTiles(player);
                        break;
                    }
                }
            } else if (super.getGuiRoot().getGameCache() != null) {
                GameView cachedGame = super.getGuiRoot().getGameCache().getCachedGameView();
                for (PlayerView player : cachedGame.getPlayers()) {
                    if (player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                        loadShipboardTiles(player);
                        break;
                    }
                }
            }

            updateCurrentGoodsDisplay();
        });
    }

    private void loadCurrentCard(GameView game) {
        if (currentCardImageView == null) {
            System.err.println("currentCardImageView is null - check FXML binding");
            return;
        }

        try {
            if (game != null && game.getCurrentCard() != null) {
                JSONObject cardJson = findCardJsonById(String.valueOf(game.getCurrentCard().getId()));
                if (cardJson != null) {
                    String imagePath = cardJson.getString("img");
                    InputStream imageStream = getClass().getResourceAsStream(imagePath);
                    if (imageStream != null) {
                        Image cardImage = new Image(imageStream);
                        currentCardImageView.setImage(cardImage);
                        currentCardImageView.setVisible(true);
                    } else {
                        System.err.println("Card image not found: " + imagePath);
                        currentCardImageView.setVisible(false);
                    }
                } else {
                    System.err.println("Card with ID " + game.getCurrentCard().getId() + " not found in JSON");
                    currentCardImageView.setVisible(false);
                }
            } else if (getGuiRoot().getGameCache() != null && getGuiRoot().getGameCache().getCurrentCard() != null) {
                JSONObject cardJson = findCardJsonById(String.valueOf(getGuiRoot().getGameCache().getCurrentCard().getId()));
                if (cardJson != null) {
                    String imagePath = cardJson.getString("img");
                    InputStream imageStream = getClass().getResourceAsStream(imagePath);
                    if (imageStream != null) {
                        Image cardImage = new Image(imageStream);
                        currentCardImageView.setImage(cardImage);
                        currentCardImageView.setVisible(true);
                    } else {
                        System.err.println("Card image not found: " + imagePath);
                        currentCardImageView.setVisible(false);
                    }
                } else {
                    System.err.println("Card with ID " + getGuiRoot().getGameCache().getCurrentCard().getId() + " not found in JSON");
                    currentCardImageView.setVisible(false);
                }
            } else {
                currentCardImageView.setVisible(false);
            }
        } catch (Exception e) {
            System.err.println("Error loading current card: " + e.getMessage());
            e.printStackTrace();
            currentCardImageView.setVisible(false);
        }
    }

    private JSONObject findCardJsonById(String cardId) {
        try (InputStream is = getClass().getResourceAsStream(CARDS_JSON_PATH)) {
            if (is == null) {
                System.err.println("JSON file not found: " + CARDS_JSON_PATH);
                return null;
            }

            JSONArray jsonArray = new JSONArray(new JSONTokener(is));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (String.valueOf(json.getInt("id")).equals(cardId)) {
                    return json;
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading cards JSON file: " + e.getMessage());
        }
        return null;
    }

    private void loadShipboardTiles(PlayerView player) {
        storageComponents.clear();
        if (player != null && player.getShipboardView() != null) {
            List<ComponentsView> components = super.getShipboardComponents(player.getShipboardView());
            for (ComponentsView component : components) {
                if (Objects.equals(component.getType(), "Storage")) {
                    storageComponents.add(new Points(component.getPosX(), component.getPosY()));
                }
                placeComponentOnShipboard(component, component.getPosX(), component.getPosY());
            }

            if (!components.isEmpty()) {
                updateShipBoardGUI(components);
            }
        }
    }

    private void updateCurrentGoodsDisplay() {
        Platform.runLater(() -> {
            currentGoodsBox.getChildren().clear();

            if (getGuiRoot().getGameCache() != null && getGuiRoot().getGameCache().hasCachedGameView()) {
                GameView gameView = getGuiRoot().getGameCache().getCachedGameView();
                if (gameView.getCurrentCard() != null && gameView.getCurrentCard().getGoodsList() != null) {
                    List<GoodsView> goodsList = gameView.getCurrentCard().getGoodsList();
                    for (int i = 0; i < goodsList.size(); i++) {
                        if (goodsList.get(i) != null) {
                            HBox goodEntry = new HBox(5);
                            Label positionLabel = new Label(i + ":");
                            positionLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                            Label goodLabel = new Label(goodsList.get(i).getColour().toString());
                            goodLabel.setStyle("-fx-text-fill: " + getColorStyle(goodsList.get(i).getColour().toString()) + ";");

                            Label statusLabel = new Label(goodsList.get(i).isTaken() ? "(taken)" : "(available)");
                            statusLabel.setStyle("-fx-text-fill: " + (goodsList.get(i).isTaken() ? "#FF6B6B" : "#4ECDC4") + ";");

                            goodEntry.getChildren().addAll(positionLabel, goodLabel, statusLabel);
                            currentGoodsBox.getChildren().add(goodEntry);
                        }
                    }
                }
            }
        });
    }

    private String getColorStyle(String color) {
        switch (color.toLowerCase()) {
            case "red": return "#FF0000";
            case "blue": return "#0000FF";
            case "yellow": return "#FFFF00";
            case "purple": return "#800080";
            default: return "#FFFFFF";
        }
    }

    @FXML
    private void onRemoveGood() throws RemoteException {
        if (isViewingOtherPlayerShipboard) {
            showValidationError("Cannot modify goods while viewing another player's shipboard");
            return;
        }

        try {
            int x = Integer.parseInt(xCoordinateField.getText().trim());
            int y = Integer.parseInt(yCoordinateField.getText().trim());
            int goodPosition = Integer.parseInt(goodPositionField.getText().trim());

            Points targetPoint = new Points(x, y);
            if (!storageComponents.contains(targetPoint)) {
                showValidationError("No storage component found at coordinates (" + x + ", " + y + ")");
                return;
            }

            List<String> args = Arrays.asList(
                    String.valueOf(x),
                    String.valueOf(y),
                    String.valueOf(goodPosition)
            );

            Message message = getGuiRoot().getClient().getMessageGenerator().generate("remove_good", args);
            getGuiRoot().getClient().sendMessage(message);

            statusMessage.setText("Removed good from (" + x + ", " + y + ") at position " + goodPosition);
            clearInputFields();
            updateCurrentGoodsDisplay();

        } catch (NumberFormatException e) {
            showValidationError("Please enter valid numbers for coordinates and position");
        }
    }

    @FXML
    private void onAddGood() throws RemoteException {
        if (isViewingOtherPlayerShipboard) {
            showValidationError("Cannot modify goods while viewing another player's shipboard");
            return;
        }

        try {
            int x = Integer.parseInt(xCoordinateField.getText().trim());
            int y = Integer.parseInt(yCoordinateField.getText().trim());
            int goodPosition = Integer.parseInt(goodPositionField.getText().trim());

            Points targetPoint = new Points(x, y);
            if (!storageComponents.contains(targetPoint)) {
                showValidationError("No storage component found at coordinates (" + x + ", " + y + ")");
                return;
            }

            List<String> args = Arrays.asList(
                    String.valueOf(x),
                    String.valueOf(y),
                    String.valueOf(goodPosition)
            );

            Message message = getGuiRoot().getClient().getMessageGenerator().generate("add_good", args);
            getGuiRoot().getClient().sendMessage(message);

            statusMessage.setText("Added good to (" + x + ", " + y + ") at position " + goodPosition);
            clearInputFields();
            updateCurrentGoodsDisplay();

        } catch (NumberFormatException e) {
            showValidationError("Please enter valid numbers for coordinates and position");
        }
    }

    @FXML
    private void onAbandonGame() throws RemoteException {
        // Mostra dialogo di conferma
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Abandon Game");
        confirmAlert.setHeaderText("Are you sure you want to abandon the game?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("abandon", new ArrayList<>());
            getGuiRoot().getClient().sendMessage(message);
        }
    }

    @FXML
    private void onEndChangeGoods() throws RemoteException {
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("end_change_goods", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
    }

    @FXML
    public void onShowPlayer1Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer1ShipboardButton.setVisible(false);
        showOwnShipboardButton.setVisible(true);
        showOwnShipboardButton.setDisable(false);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer1ShipboardButton));
    }

    @FXML
    public void onShowPlayer2Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer2ShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer2ShipboardButton));
    }

    @FXML
    public void onShowPlayer3Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer3ShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer3ShipboardButton));
    }

    @FXML
    public void onShowOwnShipboard() {
        isViewingOtherPlayerShipboard = false;
        showPlayerShipboard(getGuiRoot().getClient().getPlayerName());
        restoreButtonStates();
        resetShowShipboardButtons();

        if (getGuiRoot().getGameCache() != null && getGuiRoot().getGameCache().hasCachedGameView()) {
            updatePlayerShipboardButtons(getGuiRoot().getGameCache().getCachedGameView());
        }
        hideValidationMessage();
    }

    private void disableAllButtons() {
        for (Button button : allButtons) {
            button.setDisable(true);
        }
    }

    private String getPlayerNameFromButton(Button button) {
        String buttonText = button.getText();
        return buttonText.substring(5, buttonText.indexOf("'s Shipboard"));
    }

    private void showPlayerShipboard(String playerName) {
        if (getGuiRoot().getGameCache() == null || !getGuiRoot().getGameCache().hasCachedGameView()) {
            System.err.println("No GameView available to show shipboard");
            return;
        }

        GameView cachedGame = getGuiRoot().getGameCache().getCachedGameView();

        if (cachedGame.getPlayers() == null || cachedGame.getPlayers().isEmpty()) {
            System.err.println("Empty player list in cached GameView");
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
            System.err.println("Player " + playerName + " not found in current GameView");
            if (cachedGame.getException() == null) {
                showValidationError("Player " + playerName + " not found");
            }
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

        showOwnShipboardButton.setVisible(true);
        showOwnShipboardButton.setDisable(false);
        showValidationError("Now showing " + playerName + "'s shipboard");
    }
    private void showOwnShipboard() {
        isViewingOtherPlayerShipboard = false;
        currentlyViewedPlayer = null;

        // Riabilita i controlli di modifica
        restoreButtonStates();
        enableModificationControls();

        // Carica la propria shipboard
        Platform.runLater(() -> {
            shipboardContainer.getChildren().clear();
            shipboardContainer.getChildren().add(shipboardImageView);
        });

        GameView game = getGuiRoot().getGameCache().getCachedGameView();
        if (game != null) {
            for (PlayerView player : game.getPlayers()) {
                if (player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                    loadShipboardTiles(player);
                    break;
                }
            }
        }

        // Nascondi il pulsante per tornare alla propria shipboard e ripristina gli altri
        resetShowShipboardButtons();
        updatePlayerShipboardButtons(game);

        statusMessage.setText("Viewing your own shipboard");
        hideValidationMessage();
    }

    private void saveButtonStates() {
        previousButtonStates = new ArrayList<>();
        for (Button button : allButtons) {
            previousButtonStates.add(button.isDisable());
        }
    }

    private void restoreButtonStates() {
        if (previousButtonStates != null) {
            for (int i = 0; i < allButtons.size() && i < previousButtonStates.size(); i++) {
                allButtons.get(i).setDisable(previousButtonStates.get(i));
            }
        }
    }

    private void disableModificationControls() {
        removeGoodButton.setDisable(true);
        addGoodButton.setDisable(true);
        xCoordinateField.setDisable(true);
        yCoordinateField.setDisable(true);
        goodPositionField.setDisable(true);
    }

    private void enableModificationControls() {
        removeGoodButton.setDisable(false);
        addGoodButton.setDisable(false);
        xCoordinateField.setDisable(false);
        yCoordinateField.setDisable(false);
        goodPositionField.setDisable(false);
    }

    private void resetShowShipboardButtons() {
        showPlayer1ShipboardButton.setVisible(false);
        showPlayer2ShipboardButton.setVisible(false);
        showPlayer3ShipboardButton.setVisible(false);
        showOwnShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(true);
    }

    public void updatePlayerShipboardButtons(GameView game) {
        if (game == null || game.getPlayers() == null) return;

        List<PlayerView> players = game.getPlayers();
        String currentPlayerName = getGuiRoot().getClient().getPlayerName();

        // Reset tutti i pulsanti
        resetShowShipboardButtons();

        int buttonIndex = 0;
        Button[] buttons = {showPlayer1ShipboardButton, showPlayer2ShipboardButton, showPlayer3ShipboardButton};

        for (int i = 0; i < players.size() && buttonIndex < buttons.length; i++) {
            PlayerView player = players.get(i);
            if (player != null && !player.getName().equals(currentPlayerName)) {
                buttons[buttonIndex].setText("Show " + player.getName() + "'s Shipboard");
                buttons[buttonIndex].setVisible(true);
                buttonIndex++;
            }
        }
    }

    public void loadShipboardImage() {
        try {
            InputStream imageStream;
            int shipBoardLevel = getGuiRoot().getShipBoardLevel();

            // Verifica se c'è una cache del gioco disponibile
            if (getGuiRoot().getGameCache() != null && getGuiRoot().getGameCache().hasCachedGameView()) {
                GameView cachedGame = getGuiRoot().getGameCache().getCachedGameView();
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
            showValidationError("Error loading shipboard image!");
        }
    }

    public void loadFlightboardImage() {
        try {
            String imagePath;
            if(getGuiRoot().getGameMode() == 0) {
                imagePath = "/org.example/cardboard/cardboard-3.jpg";
            } else {
                imagePath = "/org.example/cardboard/cardboard-5.jpg";
            }

            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream != null) {
                Image image = new Image(imageStream);
                flightboardImageView.setImage(image);
            } else {
                System.err.println("Flightboard image not found: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("Error loading flightboard image: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void updateShipBoardGUI(List<ComponentsView> newComponents) {
        for (ComponentsView component : newComponents) {
            placeComponentOnShipboard(component, component.getPosX(), component.getPosY());
        }
    }

    private void placeComponentOnShipboard(ComponentsView component, int x, int y) {
        try {
            Image image = getGuiRoot().getImagesMap().get(component.getId());

            Direction direction = component.getDirection();
            placeImageOnShipboard(image, x, y, direction);
            placeQuantityIndicatorsOnShipboard(component, x, y);
        } catch (Exception e) {
            System.err.println("Error placing component on shipboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void placeImageOnShipboard(Image image, int x, int y, Direction direction) {
        try {
            x = x - 4;
            y = y - 5;

            double cellWidth = shipboardImageView.getFitWidth() / 7.32;
            double cellHeight = shipboardImageView.getFitHeight() / 5.52;

            double posX = x * cellWidth;
            double posY = y * cellHeight;

            ImageView componentImageView = new ImageView(image);

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

    private void placeQuantityIndicatorsOnShipboard(ComponentsView component, int x, int y) {
        try {
            x = x - 4;
            y = y - 5;

            double cellWidth = shipboardImageView.getFitWidth() / 7.32;
            double cellHeight = shipboardImageView.getFitHeight() / 5.52;

            double basePosX = x * cellWidth;
            double basePosY = y * cellHeight;

            double indicatorSize = Math.min(cellWidth, cellHeight) * 0.4;
            double centerX = basePosX + (cellWidth / 2);
            double centerY = basePosY + (cellHeight / 2);

            int positionIndex = 0;
            double[][] positions = {
                    {centerX - indicatorSize/2, centerY - indicatorSize/2},
                    {centerX + indicatorSize*0.3, centerY - indicatorSize/2},
                    {centerX - indicatorSize*0.8, centerY - indicatorSize/2},
                    {centerX - indicatorSize/2, centerY + indicatorSize*0.3},
                    {centerX + indicatorSize*0.3, centerY + indicatorSize*0.3},
                    {centerX - indicatorSize*0.8, centerY + indicatorSize*0.3}
            };

            if (component.getNumAstronauts() > 0) {
                for (int i = 0; i < component.getNumAstronauts() && positionIndex < positions.length; i++) {
                    placeQuantityIndicator("/org.example/cardboard/astronaut.jpg",
                            positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                    positionIndex++;
                }
            }

            if (component.getAlienColour() != null && positionIndex < positions.length) {
                String alienImagePath = component.getAlienColour().toString().toLowerCase().equals("brown") ?
                        "/org.example/cardboard/brownAlien.jpg" : "/org.example/cardboard/purpleAlien.jpg";
                placeQuantityIndicator(alienImagePath,
                        positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                positionIndex++;
            }

            if (component.getNumBattery() > 0) {
                for (int i = 0; i < component.getNumBattery() && positionIndex < positions.length; i++) {
                    placeQuantityIndicator("/org.example/cardboard/battery.jpg",
                            positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                    positionIndex++;
                }
            }

            if (component.getGoods() != null) {
                for (int i = 0; i < component.getGoods().length && positionIndex < positions.length; i++) {
                    if (component.getGoods()[i] != null) {
                        String goodColor = component.getGoods()[i].getColour().toString().toLowerCase();
                        String goodImagePath = "/org.example/cardboard/" + goodColor + "Good.jpg";
                        placeQuantityIndicator(goodImagePath,
                                positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                        positionIndex++;
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Error placing quantity indicators: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void placeQuantityIndicator(String imagePath, double x, double y, double size) {
        try {
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.err.println("Indicator image not found: " + imagePath);
                return;
            }

            Image indicatorImage = new Image(imageStream);
            ImageView indicatorImageView = new ImageView(indicatorImage);

            indicatorImageView.setFitWidth(size);
            indicatorImageView.setFitHeight(size);
            indicatorImageView.setPreserveRatio(true);
            indicatorImageView.setX(x);
            indicatorImageView.setY(y);

            Platform.runLater(() -> shipboardContainer.getChildren().add(indicatorImageView));

        } catch (Exception e) {
            System.err.println("Error placing quantity indicator: " + e.getMessage());
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
        try (InputStream is = getClass().getResourceAsStream("/org.example/JsonPkg/tiles.json")) {
            if (is == null) {
                System.err.println("JSON file not found: /org.example/JsonPkg/tiles.json");
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

    private void clearInputFields() {
        xCoordinateField.clear();
        yCoordinateField.clear();
        goodPositionField.clear();
        hideValidationMessage();
    }

    @Override
    public void updateGui(GameView game) {
        Platform.runLater(() -> {
            try {
                loadShipboardImage();
                loadFlightboardImage();
                updatePlayerShipboardButtons(game);
                updateCurrentGoodsDisplay();

                // Gestisci le differenze della cache del gioco
                GameViewCache.GameViewDifferences differences = getGuiRoot().getGameCache().compareAndUpdate(game);

                if (differences.hasChanges()) {
                    if (!differences.getNewShipboardComponents().isEmpty()) {
                        updateShipBoardGUI(differences.getNewShipboardComponents());
                    }
                    if (!differences.getChangedShipboardComponents().isEmpty()) {
                        updateShipBoardGUI(differences.getChangedShipboardComponents());
                    }
                }

                // Aggiorna solo se non si sta visualizzando la shipboard di un altro giocatore
                if (!isViewingOtherPlayerShipboard) {
                    for (PlayerView player : game.getPlayers()) {
                        if (player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                            loadShipboardTiles(player);
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Error updating GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void showValidationError(String message) {
        Platform.runLater(() -> {
            validationMessage.setText(message);
            validationMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
            validationMessage.setVisible(true);
        });
    }

    private void hideValidationMessage() {
        Platform.runLater(() -> validationMessage.setVisible(false));
    }
}