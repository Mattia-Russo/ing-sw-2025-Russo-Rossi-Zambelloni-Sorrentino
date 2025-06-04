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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ForView.GameViewCache;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class BuildShipSceneController extends GuiController implements Initializable {

    private static final String COMPONENT_JSON_PATH = "/org.example/JsonPkg/tiles.json";

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView shipboardImageView;

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

    // Cache per la GameView locale
    private GameViewCache gameViewCache;

    @Override
    public void setGui(GUI guiRoot) {
        super.setGui(guiRoot);
        this.gameViewCache = new GameViewCache(getGuiRoot().getClient().getPlayerName());
        Platform.runLater(this::loadShipboardImage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        validationMessage.setVisible(false);
        setupFieldValidation();
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });

        // Disabilita i pulsanti che dipendono da un componente
        discardComponentButton.setDisable(true);
        rotateLeftButton.setDisable(true);
        rotateRightButton.setDisable(true);
        placeComponentButton.setDisable(true);
    }

    /**
     * Aggiorna la GUI confrontando la nuova GameView con quella cached
     */
    public void updateGui(GameView game) {
        if (gameViewCache == null) {
            // Inizializza la cache se non esiste
            gameViewCache = new GameViewCache(getGuiRoot().getClient().getPlayerName());
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

                // Aggiorna i componenti scoperti a destra della navicella
                if (!differences.getNewDiscoveredComponents().isEmpty()) {
                    updateDiscoveredComponentsGUI(differences.getNewDiscoveredComponents());
                }

                if (differences.isCurrentTileChanged()) {
                    updateCurrentTileGUI(differences.getNewCurrentTile());
                }
            });
        }
    }

    private void updateCurrentTileGUI(ComponentsView currentTile) {
        if (currentTile == null) {
            // Il currentTile è stato rimosso/consumato
            clearCurrentTileFromGUI();
            // Disabilita i pulsanti che dipendono dal currentTile
            discardComponentButton.setDisable(true);
            rotateLeftButton.setDisable(true);
            rotateRightButton.setDisable(true);
            placeComponentButton.setDisable(true);

            System.out.println("CurrentTile rimosso - pulsanti disabilitati");
        } else {
            // Nuovo currentTile disponibile
            displayCurrentTileInGUI(currentTile);
            // Abilita i pulsanti per gestire il currentTile
            discardComponentButton.setDisable(false);
            rotateLeftButton.setDisable(false);
            rotateRightButton.setDisable(false);
            placeComponentButton.setDisable(false);

            System.out.println("Nuovo currentTile disponibile: ID=" + currentTile.getId() +
                    ", Tipo=" + currentTile.getType());
        }
    }

    /**
     * Visualizza il currentTile nella GUI (ad esempio in un'area dedicata)
     */
    private void displayCurrentTileInGUI(ComponentsView currentTile) {
        try {
            // Cerca il JSON del componente
            JSONObject componentJson = findComponentJsonById(String.valueOf(currentTile.getId()));
            if (componentJson == null) {
                System.err.println("CurrentTile con ID " + currentTile.getId() + " non trovato nel JSON.");
                return;
            }

            String imagePath = componentJson.getString("img");

            // TODO: Implementare la visualizzazione del currentTile in un'area dedicata della GUI
            // Ad esempio, potresti avere un ImageView dedicato per mostrare il componente corrente
            // che il giocatore può piazzare/ruotare/scartare

            System.out.println("Visualizzando currentTile: " + imagePath);

            // Esempio di implementazione (da adattare al tuo layout):
            // if (currentTileImageView != null) {
            //     InputStream imageStream = getClass().getResourceAsStream(imagePath);
            //     if (imageStream != null) {
            //         Image componentImage = new Image(imageStream);
            //         currentTileImageView.setImage(componentImage);
            //         double rotation = getRotationFromDirection(currentTile.getDirection());
            //         currentTileImageView.setRotate(rotation);
            //     }
            // }

        } catch (Exception e) {
            System.err.println("Errore durante la visualizzazione del currentTile: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Rimuove il currentTile dalla visualizzazione GUI
     */
    private void clearCurrentTileFromGUI() {
        // TODO: Implementare la rimozione del currentTile dalla GUI
        // Ad esempio:
        // if (currentTileImageView != null) {
        //     currentTileImageView.setImage(null);
        // }

        System.out.println("CurrentTile rimosso dalla GUI");
    }

    /**
     * Aggiorna la GUI della shipboard con i nuovi componenti del giocatore
     */
    private void updateShipBoardGUI(List<ComponentsView> newComponents) {
        for (ComponentsView component : newComponents) {
            placeComponentOnShipboard(component, component.getPosX(), component.getPosY());
        }
    }

    /**
     * Aggiorna la GUI dei componenti scoperti (a destra della navicella)
     */
    private void updateDiscoveredComponentsGUI(List<ComponentsView> newDiscoveredComponents) {
        // TODO: Implementare la visualizzazione dei componenti scoperti a destra della navicella
        for (ComponentsView component : newDiscoveredComponents) {
            System.out.println("Nuovo componente scoperto: ID=" + component.getId() +
                    ", Tipo=" + component.getType());
            // Aggiungi il componente alla sezione destra della GUI
            addDiscoveredComponentToRightPanel(component);
        }
    }

    /**
     * Aggiunge un componente scoperto al pannello destro
     */
    private void addDiscoveredComponentToRightPanel(ComponentsView component) {
        try {
            // Cerca il JSON del componente
            JSONObject componentJson = findComponentJsonById(String.valueOf(component.getId()));
            if (componentJson == null) {
                System.err.println("Componente scoperto con ID " + component.getId() + " non trovato nel JSON.");
                return;
            }

            String imagePath = componentJson.getString("img");

            // TODO: Implementare il posizionamento nel pannello destro
            // Questo dipende dal layout della tua GUI
            System.out.println("Aggiungendo componente scoperto: " + imagePath);

        } catch (Exception e) {
            System.err.println("Errore durante l'aggiunta del componente scoperto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Piazza un componente sulla shipboard
     */
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

            // Piazza l'immagine sulla shipboard con rotazione
            placeImageOnShipboard(imagePath, x, y);

            System.out.println("Componente piazzato sulla shipboard: ID=" + component.getId() +
                    ", Posizione=(" + x + "," + y + ")" +
                    ", Direzione=" + component.getDirection());

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
    }

    private void loadShipboardImage() {
        try {
            InputStream imageStream;
            if(getGuiRoot().getShipBoardLevel()==1) {
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


    /**
     * Cerca l'oggetto JSON di un componente in base al suo ID.
     */
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

    /**
     * Piazza un'immagine sulla shipboard con direzione/rotazione
     */
    private void placeImageOnShipboard(String imagePath, int x, int y) {
        try {
            // Calcola le dimensioni e la posizione nella griglia
            double cellWidth = shipboardImageView.getFitWidth() / 7.0; // Supponiamo 7 colonne
            double cellHeight = shipboardImageView.getFitHeight() / 5.0; // Supponiamo 5 righe

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
            componentImageView.setFitWidth(cellWidth * 0.8);
            componentImageView.setFitHeight(cellHeight * 0.8);
            componentImageView.setPreserveRatio(true);

            componentImageView.setX(posX + (cellWidth * 0.1));
            componentImageView.setY(posY + (cellHeight * 0.1));

            // Aggiungi l'immagine alla shipboard
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

    /**
     * Resetta la cache della GameView (utile per nuove partite)
     */
    public void resetGameViewCache() {
        if (gameViewCache != null) {
            gameViewCache.resetCache();
        }
    }

    /**
     * Aggiorna il nome del giocatore nella cache
     */
    public void updatePlayerName(String playerName) {
        if (gameViewCache != null) {
            gameViewCache.setCurrentPlayerName(playerName);
        }
    }

    public void onPickComponentClick() {
    }

    public void onDiscardComponentClick() {
    }

    public void onRotateLeftClick() {
    }

    public void onRotateRightClick() {}

    public void onPlaceComponentClick() {}
}