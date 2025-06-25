package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.MessagePkg.Message;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ForView.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class AddAlienSceneController extends GuiController implements Initializable {

    private static final String COMPONENT_JSON_PATH = "/org.example/JsonPkg/tiles.json";

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView shipboardImageView;

    @FXML
    private Pane shipboardContainer;

    @FXML
    private TextField startingPositionField;

    @FXML
    private RadioButton purpleAlienRadio;

    @FXML
    private RadioButton brownAlienRadio;

    @FXML
    private Button addAlienButton;

    @FXML
    private Button endAddAlienButton;

    @FXML
    private Label validationMessage;

    @FXML
    private Label statusMessage;

    @FXML
    private Button selectPositionButton;

    @FXML
    private Button showPlayer1ShipboardButton;

    @FXML
    private Button showPlayer2ShipboardButton;

    @FXML
    private Button showPlayer3ShipboardButton;

    @FXML
    private Button showOwnShipboardButton;

    // Aggiungi questo campo FXML nella classe
    @FXML
    private ImageView flightboardImageView;

    @FXML
    private Pane flightboardContainer;

    @FXML
    private TextField alienPosX;

    @FXML
    private TextField alienPosY;

    // Variabile per tenere traccia se stiamo visualizzando la shipboard di un altro giocatore
    private boolean isViewingOtherPlayerShipboard = false;
    private List<Button> allButtons;
    private List<Boolean> previousButtonStates;

    private ToggleGroup alienTypeGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        setupFieldValidation();
        validationMessage.setVisible(false);
        statusMessage.setText("Add aliens to your shipboard");

        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });

        // Setup radio button group
        alienTypeGroup = new ToggleGroup();
        purpleAlienRadio.setToggleGroup(alienTypeGroup);
        brownAlienRadio.setToggleGroup(alienTypeGroup);

        allButtons = Arrays.asList(selectPositionButton, addAlienButton, endAddAlienButton);

        saveButtonStates();
    }

    @Override
    public void setGui(GUI guiRoot) {
        super.setGui(guiRoot);
    }

    // Aggiungi queste modifiche al AddAlienSceneController
    @Override
    public void setUp(GameView game) {
        Platform.runLater(() -> {
            updateGui(game);
            loadShipboardImage();
            loadFlightboardImage();
            resetShowShipboardButtons();
            
            // Aggiungi questa parte per caricare immediatamente le tile esistenti
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
                placeComponentOnShipboard(component, component.getPosX(), component.getPosY());
            }

            // Aggiorna la GUI con i componenti esistenti
            if (!existingComponents.isEmpty()) {
                updateShipBoardGUI(existingComponents);
            }
        }
    }

    // Aggiungi questo metodo per caricare l'immagine della flightboard
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

    // Modifica il metodo updateGui per includere il caricamento della flightboard
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
                }
                
                // Aggiorna i pulsanti per visualizzare le shipboard degli altri giocatori
                updatePlayerShipboardButtons(game);
                
            } catch (Exception e) {
                System.err.println("Error updating GUI: " + e.getMessage());
                e.printStackTrace();
            }
        });
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
            System.err.println("Nessuna GameView disponibile per mostrare la shipboard");
            return;
        }

        GameView cachedGame = getGuiRoot().getGameCache().getCachedGameView();

        // Verifica che ci siano giocatori nella GameView
        if (cachedGame.getPlayers() == null || cachedGame.getPlayers().isEmpty()) {
            System.err.println("Lista giocatori vuota nella GameView cached");
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
            System.err.println("Giocatore " + playerName + " non trovato nella GameView corrente");
            // Non mostrare errore all'utente se è una GameView con eccezione
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

    private void setupFieldValidation() {
        startingPositionField.textProperty().addListener((_, oldValue, newValue) -> {
            // Allow only numbers from 0 to -3
            if (!newValue.matches("-?[0-3]?")) {
                startingPositionField.setText(oldValue);
            }
        });
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

    private boolean validateInputs(int x, int y){
        if (getGuiRoot().getShipBoardLevel()==1) {
            return x >= 5 && x <= 9 && y >= 5 && y <= 9 && (x != 5 || y != 5) && (x != 5 || y != 6) && (x != 6 || y != 5)
                    && (x != 9 || y != 5) && (x != 9 || y != 6) && (x != 7 || y != 9);
        }else {
            return x >= 4 && x <= 10 && y >= 5 && y <= 9 && (x != 4 || y != 5) && (x != 4 || y != 6) && (x != 5 || y != 5) && (x != 7 || y != 9)
                    && (x != 7 || y != 5) && (x != 10 || y != 5) && (x != 10 || y != 6) && (x != 9 || y != 5);
        }
    }

    @FXML
    public void onAddAlienClick() throws RemoteException {
        try {
            String posX = alienPosX.getText().trim();
            String posY = alienPosY.getText().trim();

            if (validateInputs(Integer.parseInt(alienPosX.getText().trim()), Integer.parseInt(alienPosY.getText().trim()))) {
                RadioButton selectedAlien = (RadioButton) alienTypeGroup.getSelectedToggle();
                if (selectedAlien == null) {
                    showValidationError("Please select an alien type");
                    return;
                }

                String messageType;
                if (selectedAlien == purpleAlienRadio) {
                    messageType = "add_purple_alien";  // Nota: purple invece di purple
                } else {
                    messageType = "add_brown_alien";
                }

                List<String> args = Arrays.asList(posX, posY);
                Message message = getGuiRoot().getClient().getMessageGenerator().generate(messageType, args);
                getGuiRoot().getClient().sendMessage(message);

                hideValidationMessage();
                statusMessage.setText("Alien added successfully!");
            }
        } catch (NumberFormatException e) {
            showValidationError("Please, insert valid inputs for X and Y");
        }
    }

    @FXML
    public void onSelectPositionClick() throws RemoteException {
        hideValidationMessage();

        Message message = getGuiRoot().getClient().getMessageGenerator().generate("select_position", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);

        statusMessage.setText("Position selection sent!");
    }

    @FXML
    public void onEndAddAlienClick() throws RemoteException {
        // Salva lo stato prima di cambiare scena
        saveCurrentShipboardState();
        
        // Resto della logica esistente...
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("end_add_alien", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
    }
}