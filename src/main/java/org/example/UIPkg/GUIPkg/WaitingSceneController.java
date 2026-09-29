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
import java.util.*;

public class WaitingSceneController extends GuiController implements Initializable {

    private static final String COMPONENT_JSON_PATH = "/org.example/JsonPkg/tiles.json";
    private static final String CARDS_JSON_PATH = "/org.example/JsonPkg/cards.json";

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView shipboardImageView;

    @FXML
    private Pane shipboardContainer;

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
    private ImageView flightboardImageView;

    @FXML
    private Pane flightboardContainer;

    @FXML
    private ImageView currentCardImageView;

    // Variabile per tenere traccia se stiamo visualizzando la shipboard di un altro giocatore
    private boolean isViewingOtherPlayerShipboard = false;
    private final List<ImageView> playerPositionImages = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        validationMessage.setVisible(false);
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
    }

    // Aggiungi queste modifiche al AddAlienSceneController
    @Override
    public void setUp(GameView game) {
        Platform.runLater(() -> {
            updateGui(game);
            loadShipBoardImage();
            loadFlightBoardImage();
            loadCurrentCard(game);
            resetShowShipboardButtons();

            if(game.getException()!=null) {
                showValidationError(game.getException().getMessage());
            }

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
    public void loadFlightBoardImage() {
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

    // Modifica il metodo updateGui per includere il caricamento della flightboard
    @Override
    public void updateGui(GameView game) {
        Platform.runLater(() -> {
            try {
                loadShipBoardImage();
                loadFlightBoardImage();
                loadCurrentCard(game);

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
                    if (!differences.getPlayersWithChangedPositions().isEmpty()) {
                        updatePlayerPositions(differences.getPlayersWithChangedPositions(), game);
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

    private void updatePlayerPositions(List<PlayerView> playersWithChangedPositions, GameView game) {
        // Rimuovi prima tutte le immagini delle posizioni esistenti
        clearPlayerPositions();

        // Aggiungi tutti i giocatori con posizioni valide alla flightboard
        for (PlayerView player : game.getPlayers()) {
            if (player.isPosValid()) {
                placePlayerOnFlightboard(player, game.getGameMode());
            }
        }
    }


    private void placePlayerOnFlightboard(PlayerView player, int gameMode) {
        if (!player.isPosValid()) {
            return;
        }

        // Calcola la posizione sulla flightboard (x, y sono il centro desiderato della posizione)
        double[] position = calculateFlightboardPosition(player.getPosition(), gameMode);
        double x = position[0];
        double y = position[1];

        String imagePath = getPlayerColorImagePath(player.getRocketColour());

        try {
            InputStream playerImagePath = getClass().getResourceAsStream(imagePath);
            assert playerImagePath != null;
            Image playerImage = new Image(playerImagePath);
            ImageView playerImageView = new ImageView(playerImage);

            // Imposta le dimensioni dell'immagine
            playerImageView.setFitWidth(20);
            playerImageView.setFitHeight(20);
            playerImageView.setPreserveRatio(true);

            // *** MODIFICA QUI per centrare l'immagine (già discussa) ***
            // Ottieni le dimensioni effettive dopo aver impostato fitWidth/Height
            double markerWidth = playerImageView.getFitWidth();
            double markerHeight = playerImageView.getFitHeight();

            // Posiziona l'immagine sottraendo metà delle sue dimensioni
            playerImageView.setLayoutX(x - (markerWidth / 2));
            playerImageView.setLayoutY(y - (markerHeight / 2));

            // Non è necessaria alcuna rotazione per un cerchio.

            // Aggiungi l'immagine al container della flightboard
            flightboardContainer.getChildren().add(playerImageView);

            // Tieni traccia dell'immagine per poterla rimuovere successivamente
            playerPositionImages.add(playerImageView);

        } catch (Exception e) {
            System.err.println("Errore nel caricare l'immagine del giocatore: " + imagePath);
            e.printStackTrace();
        }
    }


    private String getPlayerColorImagePath(String color) {
        return switch (color) {
            case "RED" -> "/org.example/cardboard/redCircle.jpg";
            case "GREEN" -> "/org.example/cardboard/greenCircle.jpg";
            case "YELLOW" -> "/org.example/cardboard/yellowCircle.jpg";
            case "BLUE" -> "/org.example/cardboard/blueCircle.jpg";
            default -> "/org.example/cardboard/redCircle.jpg"; // Default fallback
        };
    }

    private double[] calculateFlightboardPosition(int playerPosition, int gameMode) {
        double x = 0;
        double y = 0;
        int absPos = 0;

        // Dimensioni reali dell'immagine della nave blu: cardboard-3.jpg
        double blue_board_width = 985.0;
        double blue_board_height = 546.0;

        // Dimensioni reali dell'immagine della nave viola: cardboard-5.jpg
        double purple_board_width = 1055.0;
        double purple_board_height = 639.0;

        // Ottieni le dimensioni reali e gli offset dell'ImageView della flightboard
        double flightboardWidth = flightboardImageView.getFitWidth();
        double flightboardHeight = flightboardImageView.getFitHeight();
        double offsetX = flightboardImageView.getLayoutX();
        double offsetY = flightboardImageView.getLayoutY();

        if (gameMode == 0) { // Nave blu (18 posizioni) - senso orario
            absPos = ((playerPosition % 18) + 18) % 18;

            switch (absPos) {
                case 0:
                    x = offsetX + flightboardWidth * (560.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (40.0 / blue_board_height);
                    break;
                case 1:
                    x = offsetX + flightboardWidth * (640.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (70.0 / blue_board_height);
                    break;
                case 2:
                    x = offsetX + flightboardWidth * (710.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (100.0 / blue_board_height);
                    break;
                case 3:
                    x = offsetX + flightboardWidth * (780.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (160.0 / blue_board_height);
                    break;
                case 4:
                    x = offsetX + flightboardWidth * (830.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (280.0 / blue_board_height);
                    break;
                case 5:
                    x = offsetX + flightboardWidth * (780.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (360.0 / blue_board_height);
                    break;
                case 6:
                    x = offsetX + flightboardWidth * (710.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (420.0 / blue_board_height);
                    break;
                case 7:
                    x = offsetX + flightboardWidth * (640.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (450.0 / blue_board_height);
                    break;
                case 8:
                    x = offsetX + flightboardWidth * (560.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (480.0 / blue_board_height);
                    break;
                case 9:
                    x = offsetX + flightboardWidth * (480.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (480.0 / blue_board_height);
                    break;
                case 10:
                    x = offsetX + flightboardWidth * (420.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (450.0 / blue_board_height);
                    break;
                case 11:
                    x = offsetX + flightboardWidth * (360.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (410.0 / blue_board_height);
                    break;
                case 12:
                    x = offsetX + flightboardWidth * (280.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (360.0 / blue_board_height);
                    break;
                case 13:
                    x = offsetX + flightboardWidth * (230.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (280.0 / blue_board_height);
                    break;
                case 14:
                    x = offsetX + flightboardWidth * (280.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (160.0 / blue_board_height);
                    break;
                case 15:
                    x = offsetX + flightboardWidth * (360.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (100.0 / blue_board_height);
                    break;
                case 16:
                    x = offsetX + flightboardWidth * (420.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (70.0 / blue_board_height);
                    break;
                case 17:
                    x = offsetX + flightboardWidth * (480.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (40.0 / blue_board_height);
                    break;
            }
        } else if (gameMode == 1) { // Nave viola (24 posizioni) - senso orario
            absPos = ((playerPosition % 24) + 24) % 24;

            // Coordinate per la nave viola - aggiustate per centrare sui triangoli
            switch (absPos) {
                case 0: // Triangolo subito a sinistra del '1' marcato
                    x = offsetX + flightboardWidth * (650.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (80.0 / purple_board_height);
                    break;
                case 1: // '1' marcato sulla scheda viola
                    x = offsetX + flightboardWidth * (730.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (100.0 / purple_board_height);
                    break;
                case 2:
                    x = offsetX + flightboardWidth * (800.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (130.0 / purple_board_height);
                    break;
                case 3:
                    x = offsetX + flightboardWidth * (870.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (200.0 / purple_board_height);
                    break;
                case 4:
                    x = offsetX + flightboardWidth * (900.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (280.0 / purple_board_height);
                    break;
                case 5:
                    x = offsetX + flightboardWidth * (900.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (360.0 / purple_board_height);
                    break;
                case 6:
                    x = offsetX + flightboardWidth * (870.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (430.0 / purple_board_height);
                    break;
                case 7:
                    x = offsetX + flightboardWidth * (800.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (460.0 / purple_board_height);
                    break;
                case 8:
                    x = offsetX + flightboardWidth * (730.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (500.0 / purple_board_height);
                    break;
                case 9:
                    x = offsetX + flightboardWidth * (650.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (510.0 / purple_board_height);
                    break;
                case 10:
                    x = offsetX + flightboardWidth * (560.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (520.0 / purple_board_height);
                    break;
                case 11:
                    x = offsetX + flightboardWidth * (470.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (520.0 / purple_board_height);
                    break;
                case 12: // Centro in alto
                    x = offsetX + flightboardWidth * (390.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (510.0 / purple_board_height);
                    break;
                case 13:
                    x = offsetX + flightboardWidth * (330.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (500.0 / purple_board_height);
                    break;
                case 14:
                    x = offsetX + flightboardWidth * (280.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (460.0 / purple_board_height);
                    break;
                case 15:
                    x = offsetX + flightboardWidth * (230.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (430.0 / purple_board_height);
                    break;
                case 16:
                    x = offsetX + flightboardWidth * (180.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (360.0 / purple_board_height);
                    break;
                case 17:
                    x = offsetX + flightboardWidth * (180.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (280.0 / purple_board_height);
                    break;
                case 18:
                    x = offsetX + flightboardWidth * (230.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (200.0 / purple_board_height);
                    break;
                case 19:
                    x = offsetX + flightboardWidth * (280.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (130.0 / purple_board_height);
                    break;
                case 20:
                    x = offsetX + flightboardWidth * (330.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (100.0 / purple_board_height);
                    break;
                case 21:
                    x = offsetX + flightboardWidth * (390.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (80.0 / purple_board_height);
                    break;
                case 22:
                    x = offsetX + flightboardWidth * (470.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (70.0 / purple_board_height);
                    break;
                case 23:
                    x = offsetX + flightboardWidth * (560.0 / purple_board_width);
                    y = offsetY + flightboardHeight * (70.0 / purple_board_height);
                    break;
            }
        }

        return new double[]{x, y};
    }

    private void clearPlayerPositions() {
        // Rimuovi tutte le immagini delle posizioni dei giocatori precedenti
        for (ImageView playerImage : playerPositionImages) {
            flightboardContainer.getChildren().remove(playerImage);
        }
        playerPositionImages.clear();
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
        showPlayer1ShipboardButton.setVisible(false);
        showOwnShipboardButton.setVisible(true);
        showOwnShipboardButton.setDisable(false);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer1ShipboardButton));
    }

    @FXML
    public void onShowPlayer2Shipboard() {
        isViewingOtherPlayerShipboard = true;
        showPlayer2ShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer2ShipboardButton));
    }

    @FXML
    public void onShowPlayer3Shipboard() {
        isViewingOtherPlayerShipboard = true;
        showPlayer3ShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer3ShipboardButton));
    }

    @FXML
    public void onShowOwnShipboard() {
        isViewingOtherPlayerShipboard = false;
        showPlayerShipboard(getGuiRoot().getClient().getPlayerName());
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

    public void loadShipBoardImage() {
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

            // Lista per tenere traccia delle posizioni occupate
            int positionIndex = 0;
            double[][] positions = {
                    {centerX - indicatorSize/2, centerY - indicatorSize/2},           // Centro
                    {centerX + indicatorSize*0.3, centerY - indicatorSize/2},         // Destra del centro
                    {centerX - indicatorSize*0.8, centerY - indicatorSize/2},         // Sinistra del centro
                    {centerX - indicatorSize/2, centerY + indicatorSize*0.3},         // Sotto il centro
                    {centerX + indicatorSize*0.3, centerY + indicatorSize*0.3},       // Destra-sotto
                    {centerX - indicatorSize*0.8, centerY + indicatorSize*0.3}        // Sinistra-sotto
            };

            // Astronauti
            if (component.getNumAstronauts() > 0) {
                for (int i = 0; i < component.getNumAstronauts() && positionIndex < positions.length; i++) {
                    placeQuantityIndicator("/org.example/cardboard/astronaut.jpg",
                            positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                    positionIndex++;
                }
            }

            // Alieni
            if (component.getAlienColour() != null && !component.getType().equals("LifeSupportSystem") && positionIndex < positions.length) {
                String alienImagePath = component.getAlienColour().toString().equalsIgnoreCase("brown") ?
                        "/org.example/cardboard/brownAlien.jpg" : "/org.example/cardboard/purpleAlien.jpg";
                placeQuantityIndicator(alienImagePath,
                        positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                positionIndex++;
            }

            // Batterie
            if (component.getNumBattery() > 0) {
                for (int i = 0; i < component.getNumBattery() && positionIndex < positions.length; i++) {
                    placeQuantityIndicator("/org.example/cardboard/battery.jpg",
                            positions[positionIndex][0], positions[positionIndex][1], indicatorSize);
                    positionIndex++;
                }
            }

            // Goods
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
}