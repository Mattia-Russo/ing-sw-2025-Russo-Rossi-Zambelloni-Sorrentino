package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class BuildShipSceneController extends GuiController implements Initializable {

    private static final String COMPONENT_JSON_PATH = "/org.example/JsonPkg/tiles.json";

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView shipboardImageView;

    @FXML
    private ImageView currentComponentImageView;

    @FXML
    private Pane shipboardContainer;

    @FXML
    private TextField xPositionField;

    @FXML
    private TextField yPositionField;

    @FXML
    private Button pickComponentButton;

    @FXML
    private Button discardComponentButton;

    @FXML
    private Button rotateLeftButton;

    @FXML
    private Button rotateRightButton;

    @FXML
    private Button placeComponentButton;

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
    private TextField discoveredIndexField;

    @FXML
    private Button pickDiscoveredButton;

    @FXML
    private VBox discoveredComponentsPanel;

    @FXML
    private HBox discoveredComponentsContainer;

    @FXML
    private Button bookComponentButton;

    @FXML
    private TextField bookedIndexField;

    @FXML
    private Button pickBookedButton;

    @FXML
    private Button turnTimerButton;

    @FXML
    private Label timerMessage;

    private GameViewCache gameViewCache;
    private List<Points> occupiedCells;
    private List<Button> allButtons;
    private List<Boolean> previousButtonStates;
    private ComponentsView[] localBookedComponents = new ComponentsView[2];
    private ImageView[] bookedComponentImages = new ImageView[2];
    private boolean isViewingOtherPlayerShipboard = false;
    private String currentDisplayedPlayer;

    @Override
    public void setGui(GUI guiRoot) {
        super.setGui(guiRoot);
        this.gameViewCache = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        validationMessage.setVisible(false);
        setupFieldValidation();
        occupiedCells = new ArrayList<>();
        localBookedComponents = new ComponentsView[2];
        bookedComponentImages = new ImageView[2];

        //Platform.runLater(this::loadShipboardImage);
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });
        this.gameViewCache = null;

        allButtons = List.of(
                pickComponentButton, discardComponentButton, rotateLeftButton, rotateRightButton,
                placeComponentButton, pickDiscoveredButton, bookComponentButton, pickBookedButton
        );

        discardComponentButton.setDisable(true);
        rotateLeftButton.setDisable(true);
        rotateRightButton.setDisable(true);
        placeComponentButton.setDisable(true);
        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);
        pickDiscoveredButton.setDisable(true);
        bookComponentButton.setDisable(true);

        turnTimerButton.setVisible(false);
        turnTimerButton.setDisable(true);
    }

    private void saveButtonStates() {
        previousButtonStates = new ArrayList<>();
        for (Button button : allButtons) {
            previousButtonStates.add(button.isDisable());
        }
    }

    private void restoreButtonStates() {
        for (int i = 0; i < allButtons.size(); i++) {
            allButtons.get(i).setDisable(previousButtonStates.get(i));
        }
    }

    private void disableAllButtons() {
        for (Button button : allButtons) {
            button.setDisable(true);
        }
    }

    public void updateGui(GameView game) {

        if (gameViewCache == null) {
            gameViewCache = new GameViewCache(getGuiRoot().getClient().getPlayerName());

            // Imposta la visibilità del pulsante turn timer solo la prima volta
            Platform.runLater(() -> {
                boolean timerEnabled = (game.getGameMode() != 0);
                turnTimerButton.setVisible(timerEnabled);
                turnTimerButton.setDisable(!timerEnabled);
            });
        }

        if (game.getException() != null) {
            Platform.runLater(() -> {
                timerMessage.setText(game.getException().getMessage());
                timerMessage.setVisible(true);

                // Nascondere il messaggio dopo 30 secondi
                new Thread(() -> {
                    try {
                        Thread.sleep(30000);
                        Platform.runLater(() -> timerMessage.setVisible(false));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            });

            // Se c'è un'eccezione, non aggiornare il resto della GUI
            // per evitare problemi con dati incompleti
            return;
        }

        // Confronta la nuova GameView con quella cached
        GameViewCache.GameViewDifferences differences = gameViewCache.compareAndUpdate(game);

        // Se ci sono differenze, aggiorna la GUI
        if (differences.hasChanges()) {
            Platform.runLater(() -> {
                // Aggiorna la shipboard con i nuovi componenti del giocatore
                if (!differences.getNewShipboardComponents().isEmpty()) {
                    updateShipBoardGUI(differences.getNewShipboardComponents());
                }

                if (differences.isCurrentTileChanged()) {
                    updateCurrentTileGUI(differences.getNewCurrentTile());
                }
            });
        }

        GameView cachedGame = gameViewCache.getCachedGameView();
        updateDiscoveredComponentsGUI(cachedGame.getComponentsDiscovered());
    }

    private void updateCurrentTileGUI(ComponentsView currentTile) {
        if (currentTile == null) {

            clearCurrentTileFromGUI();

            discardComponentButton.setDisable(true);
            rotateLeftButton.setDisable(true);
            rotateRightButton.setDisable(true);
            placeComponentButton.setDisable(true);
            bookComponentButton.setDisable(true);

            if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
                updatePlayerShipboardButtons(gameViewCache.getCachedGameView());
            }
        } else {

            displayCurrentTileOnGUI(currentTile);

            //discardComponentButton.setDisable(false);
            rotateLeftButton.setDisable(false);
            rotateRightButton.setDisable(false);
            placeComponentButton.setDisable(false);

            boolean canBook = (localBookedComponents[0] == null || localBookedComponents[1] == null);
            bookComponentButton.setDisable(!canBook);
        }
    }

    private void displayCurrentTileOnGUI(ComponentsView currentTile) {
        try {
            // Cerca il JSON del componente
            JSONObject componentJson = findComponentJsonById(String.valueOf(currentTile.getId()));
            if (componentJson == null) {
                System.err.println("CurrentTile con ID " + currentTile.getId() + " non trovato nel JSON.");
                return;
            }

            String imagePath = componentJson.getString("img");

            // Crea l'immagine dal path
            Image image = new Image(getClass().getResourceAsStream(imagePath));

            // Imposta l'immagine nell'ImageView del componente corrente
            currentComponentImageView.setImage(image);

            // Ottieni la direzione del componente e applica la rotazione
            Direction direction = currentTile.getDirection();

            // Applica la rotazione in base alla direzione
            switch (direction) {
                case NORTH:
                    currentComponentImageView.setRotate(0);
                    break;
                case WEST:
                    currentComponentImageView.setRotate(-90); // 90 gradi a sinistra
                    break;
                case EAST:
                    currentComponentImageView.setRotate(90);  // 90 gradi a destra
                    break;
                case SOUTH:
                    currentComponentImageView.setRotate(180); // 180 gradi
                    break;
                default:
                    currentComponentImageView.setRotate(0);
                    System.out.println("Direzione non riconosciuta, impostata rotazione a 0 gradi");
                    break;
            }
        } catch (Exception e) {
            System.err.println("Errore durante la visualizzazione del currentTile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void clearCurrentTileFromGUI() {
        Platform.runLater(() -> {
            if (currentComponentImageView != null) {
                currentComponentImageView.setImage(null);
            }
        });
        System.out.println("CurrentTile rimosso dalla GUI");
    }


    private void updateShipBoardGUI(List<ComponentsView> newComponents) {
        for (ComponentsView component : newComponents) {
            placeComponentOnShipboard(component, component.getPosX(), component.getPosY());

            Points p = new Points(component.getPosX(), component.getPosY());
            if(!occupiedCells.contains(p)){
              occupiedCells.add(p);
            }
        }

        updateBookedComponentsDisplay();
    }

    private void updateDiscoveredComponentsGUI(List<ComponentsView> newDiscoveredComponents) {
        Platform.runLater(() -> {

            if (!newDiscoveredComponents.isEmpty()) {
                discoveredComponentsPanel.setVisible(true);
            }

            GameView cachedGame = gameViewCache.getCachedGameView();
            List<ComponentsView> allDiscoveredComponents = cachedGame.getComponentsDiscovered();

            discoveredComponentsContainer.getChildren().clear();

            for (int i = 0; i < allDiscoveredComponents.size(); i++) {
                ComponentsView component = allDiscoveredComponents.get(i);
                addDiscoveredComponentToPanel(component, i);
            }
        });
    }


    private void addDiscoveredComponentToPanel(ComponentsView component, int index) {
        try {
            JSONObject componentJson = findComponentJsonById(String.valueOf(component.getId()));
            if (componentJson == null) {
                System.err.println("Componente scoperto con ID " + component.getId() + " non trovato nel JSON.");
                return;
            }

            String imagePath = componentJson.getString("img");

            // Crea l'immagine del componente
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.err.println("Immagine non trovata: " + imagePath);
                return;
            }

            Image componentImage = new Image(imageStream);
            ImageView componentImageView = new ImageView(componentImage);

            // Dimensiona l'immagine
            componentImageView.setFitWidth(60);
            componentImageView.setFitHeight(60);
            componentImageView.setPreserveRatio(true);

            // Crea la label con l'indice
            Label indexLabel = new Label(String.valueOf(index));
            indexLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px; " +
                    "-fx-background-color: #333; -fx-padding: 2; -fx-border-radius: 3;");

            // Container per immagine + indice
            VBox componentContainer = new VBox(5);
            componentContainer.setAlignment(javafx.geometry.Pos.CENTER);
            componentContainer.getChildren().addAll(componentImageView, indexLabel);

            // Aggiungi al pannello principale
            discoveredComponentsContainer.getChildren().add(componentContainer);
        } catch (Exception e) {
            System.err.println("Errore durante l'aggiunta del componente scoperto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void placeComponentOnShipboard(ComponentsView component, int x, int y) {
        try {
            // Cerca il JSON del componente con l'ID corrispondente
            JSONObject componentJson = findComponentJsonById(String.valueOf(component.getId()));

            if (componentJson == null) {
                System.err.println("Componente con ID " + component.getId() + " non trovato nel file JSON.");
                return;
            }

            // Recupera il percorso dell'immagine dal JSON
            String imagePath = componentJson.getString("img");
            Direction direction = component.getDirection();

            placeImageOnShipboard(imagePath, x, y, direction);
        } catch (Exception e) {
            System.err.println("Errore durante il piazzamento del componente sulla shipboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupFieldValidation() {
        // Imposta i campi X e Y per accettare solo numeri
        xPositionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                xPositionField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        yPositionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                yPositionField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        discoveredIndexField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                discoveredIndexField.setText(newValue.replaceAll("\\D", ""));
            }
        });

        bookedIndexField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[01]?")) {
                bookedIndexField.setText(oldValue);
            }
        });
    }

    public void loadShipboardImage() {
        try {
            InputStream imageStream;

            int shipBoardLevel = getGuiRoot().getShipBoardLevel();
            if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
                GameView cachedGame = gameViewCache.getCachedGameView();
                shipBoardLevel = cachedGame.getShipBoardLevel();
            }
            if(shipBoardLevel==1) {
                imageStream=getClass().getResourceAsStream("/org.example/cardboard/cardboard-1.jpg");
            }else{
                imageStream=getClass().getResourceAsStream("/org.example/cardboard/cardboard-1b.jpg");
            }
            if (imageStream == null) {
                throw new IllegalArgumentException("Immagine della navicella non trovata!");
            }

            Image shipboardImage = new Image(imageStream);
            shipboardImageView.setImage(shipboardImage);
            shipboardImageView.setFitWidth(400);
            shipboardImageView.setFitHeight(300);
            shipboardImageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine della navicella: " + e.getMessage());
            showValidationError("Errore nel caricamento dell'immagine della navicella!");
        }
    }

    private JSONObject findComponentJsonById(String componentId) {
        try (InputStream is = getClass().getResourceAsStream(COMPONENT_JSON_PATH)) {
            if (is == null) {
                System.err.println("File JSON non trovato nel percorso specificato: " + COMPONENT_JSON_PATH);
                return null;
            }

            JSONArray jsonArray = new JSONArray(new JSONTokener(is));

            // Cerca il componente con l'ID corrispondente nel JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                if (String.valueOf(json.getInt("id")).equals(componentId)) {
                    return json; // Restituisce il componente trovato
                }
            }
        } catch (Exception e) {
            System.err.println("Errore durante la lettura del file JSON: " + e.getMessage());
        }
        return null;
    }

    private void placeImageOnShipboard(String imagePath, int x, int y, Direction direction) {
        try {
            x=x-4;
            y=y-5;

            // Calcola le dimensioni e la posizione nella griglia
            double cellWidth = shipboardImageView.getFitWidth() / 7.32;
            double cellHeight = shipboardImageView.getFitHeight() / 5.52;

            double posX = x * cellWidth;
            double posY = y * cellHeight;

            // Carica l'immagine
            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.err.println("Immagine non trovata nel percorso: " + imagePath);
                return;
            }

            Image componentImage = new Image(imageStream);
            ImageView componentImageView = new ImageView(componentImage);

            // Dimensiona e posiziona l'immagine correttamente
            componentImageView.setFitWidth(cellWidth * 0.94);
            componentImageView.setFitHeight(cellHeight * 0.94);
            componentImageView.setPreserveRatio(true);

            componentImageView.setX(posX + (cellWidth * 0.2));
            componentImageView.setY(posY + (cellHeight * 0.2));

            switch (direction) {
                case NORTH:
                    componentImageView.setRotate(0);
                    break;
                case WEST:
                    componentImageView.setRotate(-90);
                    break;
                case EAST:
                    componentImageView.setRotate(90);
                    break;
                case SOUTH:
                    componentImageView.setRotate(180);
                    break;
                default:
                    componentImageView.setRotate(0);
                    System.out.println("Direzione non riconosciuta, impostata rotazione a 0 gradi");
                    break;
            }

            Platform.runLater(() -> shipboardContainer.getChildren().add(componentImageView));

        } catch (Exception e) {
            System.err.println("Errore durante il posizionamento dell'immagine sulla shipboard: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void showValidationError(String message) {
        Platform.runLater(() -> {
            validationMessage.setText(message);
            validationMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
            validationMessage.setVisible(true);
        });
    }

    private void hideValidationMessage() {
        Platform.runLater(() -> {
            validationMessage.setVisible(false);
        });
    }

    public void resetGameViewCache() {
        if (gameViewCache != null) {
            gameViewCache.resetCache();
        }
    }

    public void updatePlayerName(String playerName) {
        if (gameViewCache != null) {
            gameViewCache.setCurrentPlayerName(playerName);
        }
    }

    public void onPickComponentClick() throws RemoteException {
        hideValidationMessage();
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("pick_tile", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);

        pickComponentButton.setDisable(true);
        pickDiscoveredButton.setDisable(true);
        discardComponentButton.setDisable(false);
        rotateLeftButton.setDisable(false);
        rotateRightButton.setDisable(false);
        placeComponentButton.setDisable(false);
        pickBookedButton.setDisable(true);
    }

    public void onDiscardComponentClick() throws RemoteException{
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("discard_tile", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);

        pickComponentButton.setDisable(false);
        discardComponentButton.setDisable(true);
        rotateLeftButton.setDisable(true);
        rotateRightButton.setDisable(true);
        placeComponentButton.setDisable(true);
        pickDiscoveredButton.setDisable(false);

        boolean hasBookedComponents = (localBookedComponents[0] != null || localBookedComponents[1] != null);
        pickBookedButton.setDisable(!hasBookedComponents);
    }

    public void onRotateLeftClick() throws RemoteException{
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("left_rotate", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
    }

    public void onRotateRightClick() throws RemoteException{
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("right_rotate", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
    }

    public void onPlaceComponentClick() throws RemoteException {
        String[] args = new String[2];
        args[0] = xPositionField.getText();
        args[1] = yPositionField.getText();

        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);

        if(validateInputs(x,y)) {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("place_tile", java.util.Arrays.asList(args));
            getGuiRoot().getClient().sendMessage(message);

            pickComponentButton.setDisable(false);
            pickDiscoveredButton.setDisable(false);
            discardComponentButton.setDisable(true);
            rotateLeftButton.setDisable(true);
            rotateRightButton.setDisable(true);
            placeComponentButton.setDisable(true);
            pickBookedButton.setDisable(false);
            pickDiscoveredButton.setDisable(false);

            hideValidationMessage();

            clearCurrentTileFromGUI();

            xPositionField.clear();
            yPositionField.clear();

        }else{
            showValidationError("Please, insert valid inputs for X and Y");
        }
    }

    private boolean validateInputs(int x, int y){
        if (getGuiRoot().getShipBoardLevel()==1) {
            if(x<5 || x>9 || y<5 || y>9 || x==5 && y==5 || x==5 && y==6 || x==6 && y==5
                || x==9 && y==5 || x==9 && y==6 || x==7 && y==9 || occupiedCells.contains(new Points(x, y))) {
                return false;
            }
        }else {
            if(x<4 || x>10 || y<5 || y>9 || x==4 && y==5 || x==4 && y==6 || x==5 && y==5 || x==7 && y==9
                || x==7 && y==5 || x==10 && y==5 || x==10 && y==6 || x==9 && y==5 || occupiedCells.contains(new Points(x, y))) {
                return false;
            }
        }
        return true;
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
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer2ShipboardButton.setDisable(true);
        showPlayer3ShipboardButton.setDisable(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer1ShipboardButton));
    }

    @FXML
    public void onShowPlayer2Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer1ShipboardButton.setDisable(true);
        showPlayer3ShipboardButton.setDisable(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer2ShipboardButton));
    }

    @FXML
    public void onShowPlayer3Shipboard() {
        isViewingOtherPlayerShipboard = true;
        saveButtonStates();
        disableAllButtons();
        showPlayer1ShipboardButton.setDisable(true);
        showPlayer2ShipboardButton.setDisable(true);
        showPlayerShipboard(getPlayerNameFromButton(showPlayer3ShipboardButton));
    }

    @FXML
    public void onShowOwnShipboard() {
        isViewingOtherPlayerShipboard = false;
        showPlayerShipboard(getGuiRoot().getClient().getPlayerName());
        restoreButtonStates();
        showOwnShipboardButton.setDisable(true);
        showOwnShipboardButton.setVisible(false);

        if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
            updatePlayerShipboardButtons(gameViewCache.getCachedGameView());
        }
        hideValidationMessage();

        // Ripristina la visibilità dei componenti prenotati
        Platform.runLater(() -> {
            for (int i = 0; i < bookedComponentImages.length; i++) {
                if (bookedComponentImages[i] != null) {
                    bookedComponentImages[i].setVisible(true);
                }
            }
        });
    }

    private String getPlayerNameFromButton(Button button) {
        String buttonText = button.getText();
        return buttonText.substring(5, buttonText.indexOf("'s Shipboard"));
    }

    private void showPlayerShipboard(String playerName) {
        if (gameViewCache == null || !gameViewCache.hasCachedGameView()) {
            System.err.println("Nessuna GameView disponibile per mostrare la shipboard");
            return;
        }

        GameView cachedGame = gameViewCache.getCachedGameView();

        // Se la GameView cached contiene un'eccezione, usa l'ultima GameView valida
        // per evitare errori di "player not found"
        if (cachedGame.getException() != null) {
            // Non fare nulla, mantieni la visualizzazione corrente
            return;
        }

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

        currentDisplayedPlayer = playerName;
        showValidationError("Now showing " + playerName + "'s shipboard");

        if (!playerName.equals(getGuiRoot().getClient().getPlayerName())) {
            Platform.runLater(() -> {
                for (int i = 0; i < bookedComponentImages.length; i++) {
                    if (bookedComponentImages[i] != null) {
                        bookedComponentImages[i].setVisible(false);
                    }
                }
            });
        }
    }

    private List<ComponentsView> getShipboardComponents(ShipboardView shipboardView) {
        List<ComponentsView> components = new ArrayList<>();

        if (shipboardView == null) {
            return components;
        }

        ComponentsView[][] componentMatrix = shipboardView.getComponentsView();
        if (componentMatrix != null) {
            for (int i = 0; i < componentMatrix.length; i++) {
                for (int j = 0; j < componentMatrix[i].length; j++) {
                    if (componentMatrix[i][j] != null) {
                        components.add(componentMatrix[i][j]);
                    }
                }
            }
        }

        ComponentsView[] bookedComponents = shipboardView.getBookedComponents();
        if (bookedComponents != null) {
            for (ComponentsView bookedComponent : bookedComponents) {
                if (bookedComponent != null) {
                    components.add(bookedComponent);
                }
            }
        }

        return components;
    }

    @FXML
    public void onPickDiscoveredClick() throws RemoteException {
        hideValidationMessage();
        String indexText = discoveredIndexField.getText().trim();

        if (indexText.isEmpty()) {
            showValidationError("Please enter a valid index for the discovered component");
            return;
        }

        try {
            int index = Integer.parseInt(indexText);

            // Verifica che l'indice sia valido
            if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
                List<ComponentsView> discoveredComponents = gameViewCache.getCachedGameView().getComponentsDiscovered();
                if (index < 0 || index >= discoveredComponents.size()) {
                    showValidationError("Index out of range. Valid range: 0-" + (discoveredComponents.size() - 1));
                    return;
                }
            }

            // Invia il messaggio al server
            List<String> args = new ArrayList<>();
            args.add(indexText);
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("pick_discovered_tile", args);
            getGuiRoot().getClient().sendMessage(message);

            pickComponentButton.setDisable(true);
            pickDiscoveredButton.setDisable(true);
            discardComponentButton.setDisable(false);
            rotateLeftButton.setDisable(false);
            rotateRightButton.setDisable(false);
            placeComponentButton.setDisable(false);
            pickBookedButton.setDisable(true);

            discoveredIndexField.clear();
            hideValidationMessage();

        } catch (NumberFormatException e) {
            showValidationError("Please enter a valid number");
        }
    }

    private void updateBookedComponentsDisplay() {
        if (gameViewCache == null || !gameViewCache.hasCachedGameView()) {
            return;
        }

        GameView cachedGame = gameViewCache.getCachedGameView();
        PlayerView currentPlayer = null;

        for (PlayerView player : cachedGame.getPlayers()) {
            if (player.getName().equals(getGuiRoot().getClient().getPlayerName())) {
                currentPlayer = player;
                break;
            }
        }

        if (currentPlayer == null || currentPlayer.getShipboardView() == null) {
            return;
        }

        ComponentsView[] serverBookedComponents = currentPlayer.getShipboardView().getBookedComponents();

        Platform.runLater(() -> {
            for (int i = 0; i < 2; i++) {
                // Rimuovi l'immagine precedente se esiste
                if (bookedComponentImages[i] != null) {
                    shipboardContainer.getChildren().remove(bookedComponentImages[i]);
                    bookedComponentImages[i] = null;
                }

                if (serverBookedComponents != null && i < serverBookedComponents.length && serverBookedComponents[i] != null) {
                    localBookedComponents[i] = serverBookedComponents[i];
                    displayBookedComponent(localBookedComponents[i], i);
                } else {
                    localBookedComponents[i] = null;
                }
            }

            if(isViewingOtherPlayerShipboard) {
                pickBookedButton.setDisable(true);
            }else{
                boolean hasCurrentTile=!discardComponentButton.isDisable();
                boolean hasBookedComponents = (localBookedComponents[0] != null || localBookedComponents[1] != null);

                if(hasCurrentTile) {
                    pickBookedButton.setDisable(true);
                }else{
                    pickBookedButton.setDisable(!hasBookedComponents);
                }
            }
        });
    }

    private void displayBookedComponent(ComponentsView component, int index) {
        try {
            JSONObject componentJson = findComponentJsonById(String.valueOf(component.getId()));
            if (componentJson == null) {
                System.err.println("Componente prenotato con ID " + component.getId() + " non trovato nel JSON.");
                return;
            }

            String imagePath = componentJson.getString("img");

            // Posizioni fisse per i componenti prenotati
            int x = (index == 0) ? 9 : 10;
            int y = 5;

            // Converti le coordinate per la visualizzazione
            x = x - 4;
            y = y - 5;

            double cellWidth = shipboardImageView.getFitWidth() / 7.18;
            double cellHeight = shipboardImageView.getFitHeight() / 4;

            double posX = x * cellWidth;
            double posY = y * cellHeight;

            InputStream imageStream = getClass().getResourceAsStream(imagePath);
            if (imageStream == null) {
                System.err.println("Immagine non trovata: " + imagePath);
                return;
            }

            Image componentImage = new Image(imageStream);
            ImageView componentImageView = new ImageView(componentImage);

            componentImageView.setFitWidth(cellWidth * 0.7);
            componentImageView.setFitHeight(cellHeight * 0.7);
            componentImageView.setPreserveRatio(true);

            componentImageView.setX(posX + (cellWidth * 0.2));
            componentImageView.setY(posY + (cellHeight * 0.2));

            // Applica la rotazione
            Direction direction = component.getDirection();
            switch (direction) {
                case NORTH: componentImageView.setRotate(0); break;
                case WEST: componentImageView.setRotate(-90); break;
                case EAST: componentImageView.setRotate(90); break;
                case SOUTH: componentImageView.setRotate(180); break;
                default: componentImageView.setRotate(0); break;
            }

            // Aggiungi un bordo per distinguere i componenti prenotati
            componentImageView.setStyle("-fx-effect: dropshadow(gaussian, orange, 3, 0.7, 0, 0);");

            bookedComponentImages[index] = componentImageView;
            shipboardContainer.getChildren().add(componentImageView);
        } catch (Exception e) {
            System.err.println("Errore durante la visualizzazione del componente prenotato: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void onBookComponentClick() throws RemoteException {
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("book_tile", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);


        discardComponentButton.setDisable(true);
        rotateLeftButton.setDisable(true);
        rotateRightButton.setDisable(true);
        placeComponentButton.setDisable(true);
        bookComponentButton.setDisable(true);
        pickComponentButton.setDisable(false);

        if (gameViewCache != null && gameViewCache.hasCachedGameView()) {
            List <ComponentsView> discoveredComponents = gameViewCache.getCachedGameView().getComponentsDiscovered();
            pickDiscoveredButton.setDisable(discoveredComponents.isEmpty());
        }

        hideValidationMessage();
    }

    @FXML
    public void onPickBookedClick() throws RemoteException {
        String indexText = bookedIndexField.getText().trim();

        if (indexText.isEmpty()) {
            showValidationError("Please enter 0 or 1 for booked component index");
            return;
        }

        try {
            int index = Integer.parseInt(indexText);

            if (index < 0 || index > 1) {
                showValidationError("Index must be 0 or 1");
                return;
            }

            if (localBookedComponents[index] == null) {
                showValidationError("No booked component at index " + index);
                return;
            }

            // Invia il messaggio al server
            List<String> args = new ArrayList<>();
            args.add(indexText);
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("pick_booked_tile", args);
            getGuiRoot().getClient().sendMessage(message);

            if (bookedComponentImages[index] != null) {
                Platform.runLater(() -> {
                    shipboardContainer.getChildren().remove(bookedComponentImages[index]);
                    bookedComponentImages[index] = null;
                    localBookedComponents[index] = null;

                    pickBookedButton.setDisable(true);
                });
            }

            pickComponentButton.setDisable(true);
            pickDiscoveredButton.setDisable(true);
            pickBookedButton.setDisable(true);
            discardComponentButton.setDisable(true);
            rotateLeftButton.setDisable(false);
            rotateRightButton.setDisable(false);
            placeComponentButton.setDisable(false);
            pickBookedButton.setDisable(true);

            bookedIndexField.clear();
            hideValidationMessage();

        } catch (NumberFormatException e) {
            showValidationError("Please enter a valid number (0 or 1)");
        }
    }

    @FXML
    public void onTurnTimerClick() throws RemoteException {
        Message message = getGuiRoot().getClient().getMessageGenerator().generate("turn_timer", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
    }

}