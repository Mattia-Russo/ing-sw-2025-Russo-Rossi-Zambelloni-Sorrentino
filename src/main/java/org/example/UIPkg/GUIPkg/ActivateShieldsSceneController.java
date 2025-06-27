package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

public class ActivateShieldsSceneController extends GuiController implements Initializable {
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

    @FXML
    private TextField xCoordinateField;

    @FXML
    private TextField yCoordinateField;

    @FXML
    private Button activateShieldButton;

    @FXML
    private Button useBatteriesButton;

    @FXML
    private Button endActivateShieldButton;

    @FXML
    private Button abandonGameButton;

    @FXML
    private Label statusMessage;

    @FXML
    private Label validationMessage;

    @FXML
    private Button showPlayer1ShipboardButton;

    @FXML
    private Button showPlayer2ShipboardButton;

    @FXML
    private Button showPlayer3ShipboardButton;

    @FXML
    private Button showOwnShipboardButton;

    @FXML
    private VBox activatedShieldsBox;

    @FXML
    private VBox usedBatteriesBox;

    @FXML
    private Label activatedShieldsLabel;

    @FXML
    private Label usedBatteriesLabel;

    private boolean isViewingOtherPlayerShipboard = false;
    private List<Button> allButtons;
    private List<Boolean> previousButtonStates;
    private List<Points> ShieldsCells;
    private List<Points> batteryStorageCells;
    private List<Points> activatedShields;
    private List<Points> usedBatteries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        validationMessage.setVisible(false);
        statusMessage.setText("Insert coordinates to activate shields or use batteries");

        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);

        ShieldsCells = new ArrayList<>();
        batteryStorageCells = new ArrayList<>();
        activatedShields = new ArrayList<>();
        usedBatteries = new ArrayList<>();

        updateSummaryLabels();
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });

        allButtons = Arrays.asList(activateShieldButton, useBatteriesButton, endActivateShieldButton);
        saveButtonStates();
    }

    @Override
    public void setGui(GUI guiRoot) {
        super.setGui(guiRoot);
    }

    @Override
    public void setUp(GameView game) {
        Platform.runLater(() -> {
            updateGui(game);
            loadShipboardImage();
            loadFlightboardImage();
            resetShowShipboardButtons();

            if (game != null && game.getPlayers() != null && !game.getPlayers().isEmpty()) {
                for (PlayerView player : game.getPlayers()) {
                    if(player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                        loadShipboardTiles(player);
                        break;
                    }
                }
            } else if (super.getGuiRoot().getGameCache() != null) {
                for(PlayerView player : super.getGuiRoot().getGameCache().getCachedGameView().getPlayers()) {
                    if(player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                        loadShipboardTiles(player);
                        break;
                    }
                }
            }
        });
    }

    private void loadShipboardTiles(PlayerView player) {
        if (player != null && player.getShipboardView() != null) {
            List<ComponentsView> existingComponents = new ArrayList<>(super.getShipboardComponents(player.getShipboardView()));
            for (ComponentsView component : existingComponents) {
                // Verifica il tipo di componente e aggiunge alle liste appropriate
                if ("Shield".equals(component.getType())) {
                    ShieldsCells.add(new Points(component.getPosX(), component.getPosY()));
                } else if ("BatteryStorage".equals(component.getType())) {
                    batteryStorageCells.add(new Points(component.getPosX(), component.getPosY()));
                }
                placeComponentOnShipboard(component, component.getPosX(), component.getPosY());
            }

            if (!existingComponents.isEmpty()) {
                updateShipBoardGUI(existingComponents);
            }
        }
    }

    public void loadFlightboardImage() {
        try {
            String imagePath;
            if(getGuiRoot().getGameMode()==0){
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

    @Override
    public void updateGui(GameView game) {
        Platform.runLater(() -> {
            try {
                loadShipboardImage();
                loadFlightboardImage();

                for(String playerName : getGuiRoot().getPlayers()) {
                    if(!playerName.equals(getGuiRoot().getClient().getPlayerName())) {
                        if(showPlayer1ShipboardButton.getText()==null || showPlayer1ShipboardButton.getText().isEmpty()){
                            showPlayer1ShipboardButton.setText("Show " + playerName + "'s Shipboard");
                        } else if (showPlayer2ShipboardButton.getText()==null || showPlayer2ShipboardButton.getText().isEmpty()){
                            showPlayer2ShipboardButton.setText("Show " + playerName + "'s Shipboard");
                        } else {
                            showPlayer3ShipboardButton.setText("Show " + playerName + "'s Shipboard");
                        }
                    }
                }

                GameViewCache.GameViewDifferences differences = getGuiRoot().getGameCache().compareAndUpdate(game);

                if (differences.hasChanges()) {
                    if (!differences.getNewShipboardComponents().isEmpty()) {
                        updateShipBoardGUI(differences.getNewShipboardComponents());
                    }
                    if (!differences.getChangedShipboardComponents().isEmpty()) {
                        updateShipBoardGUI(differences.getChangedShipboardComponents());
                    }
                }

                updatePlayerShipboardButtons(game);

            } catch (Exception e) {
                System.err.println("Error updating GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void onAbandonGame() throws RemoteException {
        // Mostra dialogo di conferma
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Abandon Game");
        confirmAlert.setHeaderText("Are you sure you want to abandon the game?");

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("abandon_game", new ArrayList<>());
            getGuiRoot().getClient().sendMessage(message);
        }
    }

    @Override
    public void updatePlayerShipboardButtons(GameView game) {
        List<PlayerView> players = game.getPlayers();
        String currentPlayerName = getGuiRoot().getClient().getPlayerName();

        int buttonIndex = 1;
        for (PlayerView player : players) {
            if (!player.getName().equals(currentPlayerName)) {
                Button button = switch (buttonIndex) {
                    case 1 -> showPlayer1ShipboardButton;
                    case 2 -> showPlayer2ShipboardButton;
                    case 3 -> showPlayer3ShipboardButton;
                    default -> null;
                };

                if (button != null) {
                    button.setText("Show " + player.getName() + "'s Shipboard");
                    button.setVisible(true);
                    buttonIndex++;
                }
            }
        }
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

    private void saveButtonStates() {
        previousButtonStates = new ArrayList<>();
        for (Button button : allButtons) {
            previousButtonStates.add(button.isDisable());
        }
    }

    private void disableAllButtons() {
        for (Button button : allButtons) {
            button.setDisable(true);
        }
    }

    private void restoreButtonStates() {
        for (int i = 0; i < allButtons.size(); i++) {
            allButtons.get(i).setDisable(previousButtonStates.get(i));
        }
    }

    public void loadShipboardImage() {
        try {
            InputStream imageStream;
            int shipBoardLevel = getGuiRoot().getShipBoardLevel();

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

    private void resetShowShipboardButtons() {
        showOwnShipboardButton.setDisable(false);
        showPlayer1ShipboardButton.setDisable(false);
        showPlayer3ShipboardButton.setDisable(false);
        showPlayer2ShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(false);
        showPlayer1ShipboardButton.setVisible(true);
        showPlayer2ShipboardButton.setVisible(false);
        showPlayer3ShipboardButton.setVisible(false);
        if(getGuiRoot().getPlayers().size()>=3){
            showPlayer2ShipboardButton.setVisible(true);
            if(getGuiRoot().getPlayers().size()==4){
                showPlayer3ShipboardButton.setVisible(true);
            }
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
            placeQuantityIndicatorsOnShipboard(component, x, y);
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

    private void updateSummaryLabels() {
        Platform.runLater(() -> {
            activatedShieldsLabel.setText("Activated Shields (" + activatedShields.size() + "):");
            usedBatteriesLabel.setText("Used Batteries (" + usedBatteries.size() + "):");

            // Clear existing summary items
            activatedShieldsBox.getChildren().clear();
            usedBatteriesBox.getChildren().clear();

            // Add activated shields to summary
            for (Points shields : activatedShields) {
                Label shieldsLabel = new Label("Shield at (" + shields.getX() + ", " + shields.getY() + ")");
                shieldsLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
                activatedShieldsBox.getChildren().add(shieldsLabel);
            }

            // Add used batteries to summary
            for (Points battery : usedBatteries) {
                Label batteryLabel = new Label("Battery at (" + battery.getX() + ", " + battery.getY() + ")");
                batteryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
                usedBatteriesBox.getChildren().add(batteryLabel);
            }
        });
    }

    @FXML
    public void onActivateShield() throws RemoteException {
        if (isViewingOtherPlayerShipboard) {
            showValidationError("Cannot activate shields while viewing another player's shipboard");
            return;
        }

        try {
            int x = Integer.parseInt(xCoordinateField.getText().trim());
            int y = Integer.parseInt(yCoordinateField.getText().trim());

            Points targetPoint = new Points(x, y);

            if (!ShieldsCells.contains(targetPoint)) {
                showValidationError("No shield found at coordinates (" + x + ", " + y + ")");
                return;
            }

            if (activatedShields.contains(targetPoint)) {
                showValidationError("Shield at (" + x + ", " + y + ") already activated");
                return;
            }

            List<String> args = Arrays.asList(String.valueOf(x), String.valueOf(y));
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("activate_shields", args);
            getGuiRoot().getClient().sendMessage(message);

            activatedShields.add(targetPoint);
            updateSummaryLabels();
            hideValidationMessage();
            statusMessage.setText("Shield activated at (" + x + ", " + y + ")");

            // Clear input fields
            xCoordinateField.clear();
            yCoordinateField.clear();

        } catch(NumberFormatException e){
            showValidationError("Please enter valid numbers for X and Y coordinates");
        }
    }

    @FXML
    public void onUseBatteries() throws RemoteException {
        if (isViewingOtherPlayerShipboard) {
            showValidationError("Cannot use batteries while viewing another player's shipboard");
            return;
        }

        try {
            int x = Integer.parseInt(xCoordinateField.getText().trim());
            int y = Integer.parseInt(yCoordinateField.getText().trim());

            Points targetPoint = new Points(x, y);

            if (!batteryStorageCells.contains(targetPoint)) {
                showValidationError("No battery storage found at coordinates (" + x + ", " + y + ")");
                return;
            }

            if (usedBatteries.contains(targetPoint)) {
                showValidationError("Batteries at (" + x + ", " + y + ") already used");
                return;
            }

            List<String> args = Arrays.asList(String.valueOf(x), String.valueOf(y));
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("use_batteries", args);
            getGuiRoot().getClient().sendMessage(message);

            usedBatteries.add(targetPoint);
            updateSummaryLabels();
            hideValidationMessage();
            statusMessage.setText("Batteries used at (" + x + ", " + y + ")");

            // Clear input fields
            xCoordinateField.clear();
            yCoordinateField.clear();

        } catch(NumberFormatException e){
            showValidationError("Please enter valid numbers for X and Y coordinates");
        }
    }

    @FXML
    public void onEndActivateShields() throws RemoteException {
        // Salva lo stato prima di cambiare scena
        saveCurrentShipboardState();
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("end_activate_shields", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
    }
}
