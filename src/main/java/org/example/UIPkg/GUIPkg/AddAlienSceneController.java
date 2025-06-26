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
import org.example.ServerPkg.Model.ForView.*;

import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

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
    private final List<ImageView> playerPositionImages = new ArrayList<>();

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
            loadShipboardImage(validationMessage);
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
                loadShipboardImage(validationMessage);
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
        int absPos;

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

            // Coordinate corrette per centrare sui triangoli
            // Basate sulle immagini fornite: pos 0, -2(16), -4(14), -5(13)
            switch (absPos) {   // todo aggiustare le position del volo di prova
                case 0: // Triangolo in basso a destra (dalle immagini)
                    x = offsetX + flightboardWidth * (750.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (420.0 / blue_board_height);
                    break;
                case 1: // '1' marcato sulla scheda
                    x = offsetX + flightboardWidth * (820.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (400.0 / blue_board_height);
                    break;
                case 2: // Continuando in senso orario
                    x = offsetX + flightboardWidth * (880.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (350.0 / blue_board_height);
                    break;
                case 3:
                    x = offsetX + flightboardWidth * (920.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (280.0 / blue_board_height);
                    break;
                case 4:
                    x = offsetX + flightboardWidth * (940.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (200.0 / blue_board_height);
                    break;
                case 5:
                    x = offsetX + flightboardWidth * (920.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (120.0 / blue_board_height);
                    break;
                case 6: // In alto a destra
                    x = offsetX + flightboardWidth * (880.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (50.0 / blue_board_height);
                    break;
                case 7:
                    x = offsetX + flightboardWidth * (820.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (30.0 / blue_board_height);
                    break;
                case 8:
                    x = offsetX + flightboardWidth * (750.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (20.0 / blue_board_height);
                    break;
                case 9: // In alto al centro
                    x = offsetX + flightboardWidth * (490.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (15.0 / blue_board_height);
                    break;
                case 10:
                    x = offsetX + flightboardWidth * (240.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (20.0 / blue_board_height);
                    break;
                case 11:
                    x = offsetX + flightboardWidth * (170.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (30.0 / blue_board_height);
                    break;
                case 12: // In alto a sinistra
                    x = offsetX + flightboardWidth * (110.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (50.0 / blue_board_height);
                    break;
                case 13: // Posizione -5 dalle immagini (triangolo a sinistra)
                    x = offsetX + flightboardWidth * (80.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (120.0 / blue_board_height);
                    break;
                case 14: // Posizione -4 dalle immagini (triangolo in basso a sinistra)
                    x = offsetX + flightboardWidth * (65.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (200.0 / blue_board_height);
                    break;
                case 15:
                    x = offsetX + flightboardWidth * (80.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (280.0 / blue_board_height);
                    break;
                case 16: // Posizione -2 dalle immagini (triangolo in basso)
                    x = offsetX + flightboardWidth * (110.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (350.0 / blue_board_height);
                    break;
                case 17: // Posizione -1
                    x = offsetX + flightboardWidth * (170.0 / blue_board_width);
                    y = offsetY + flightboardHeight * (400.0 / blue_board_height);
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
        saveButtonStates();
        disableAllButtons();
        showPlayer1ShipboardButton.setVisible(false);
        showOwnShipboardButton.setVisible(true);
        showOwnShipboardButton.setDisable(false);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer1ShipboardButton), validationMessage, showOwnShipboardButton);
    }

    @FXML
    public void onShowPlayer2Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer2ShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer2ShipboardButton), validationMessage, showOwnShipboardButton);
    }

    @FXML
    public void onShowPlayer3Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer3ShipboardButton.setVisible(false);
        showOwnShipboardButton.setDisable(false);
        showOwnShipboardButton.setVisible(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer3ShipboardButton), validationMessage, showOwnShipboardButton);
    }

    @FXML
    public void onShowOwnShipboard() {
        isViewingOtherPlayerShipboard = false;
        showPlayerShipboard(getGuiRoot().getClient().getPlayerName(), validationMessage, showOwnShipboardButton);
        restoreButtonStates();
        resetShowShipboardButtons();

        if (getGuiRoot().getGameCache() != null && getGuiRoot().getGameCache().hasCachedGameView()) {
            updatePlayerShipboardButtons(getGuiRoot().getGameCache().getCachedGameView());
        }
        hideValidationMessage(validationMessage);
    }

    private String getPlayerNameFromButton(Button button) {
        String buttonText = button.getText();
        return buttonText.substring(5, buttonText.indexOf("'s Shipboard"));
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
                    showValidationError("Please select an alien type", validationMessage);
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

                hideValidationMessage(validationMessage);
                statusMessage.setText("Alien added successfully!");
            }
        } catch (NumberFormatException e) {
            showValidationError("Please, insert valid inputs for X and Y", validationMessage);
        }
    }

    @FXML
    public void onSelectPositionClick() throws RemoteException {
        hideValidationMessage(validationMessage);
        int pos = Integer.parseInt(startingPositionField.getText());
        if(pos<=0 && pos>=-3){
            try{
                Message message = getGuiRoot().getClient().getMessageGenerator().generate("select_position", Collections.singletonList(startingPositionField.getText()));
                getGuiRoot().getClient().sendMessage(message);
            } catch (NumberFormatException e) {
                showValidationError("Please, insert valid input for starting position", validationMessage);
            }

            statusMessage.setText("Position selection sent!");
            selectPositionButton.setDisable(true);
        } else {
            showValidationError("Please, insert a valid starting position", validationMessage);
        }

    }

    @FXML
    public void onEndAddAlienClick() throws RemoteException {
        // Salva lo stato prima di cambiare scena
        saveCurrentShipboardState();
        
        // Resto della logica esistente...
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("end_add_alien", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
        getGuiRoot().goToWaitingScene();
    }
}