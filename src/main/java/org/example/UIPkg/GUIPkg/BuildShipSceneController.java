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
import org.example.MessagePkg.Message;
import org.example.MessagePkg.NotifyClientMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void setGui(GUI guiRoot) {
        super.setGui(guiRoot);
        // Chiama i metodi necessari dopo che la GUI è stata impostata.
        Platform.runLater(() -> {
            loadShipboardImage();
            requestInitialCabin(7, 7); // Incarico al server di restituire l'id del componente iniziale
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
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
     * Invia una richiesta al server per ottenere l'ID del componente nella posizione `(x, y)`.
     */
    private void requestInitialCabin(int x, int y) {
        try {
            // Prepara il messaggio per il server
            Message requestMessage = getGuiRoot().getClient().getMessageGenerator()
                    .generate("get_component_id", java.util.Arrays.asList(String.valueOf(x), String.valueOf(y)));

            // Invia il messaggio al server
            getGuiRoot().getClient().sendMessage(requestMessage);

            // Aspetta la risposta del server
            getGuiRoot().getClient().onMessageReceived(response -> {
                if (response instanceof NotifyClientMessage notifyMessage) {
                    String receivedId = notifyMessage.getMessage();
                    System.out.println("Ricevuto ID del componente dalla posizione (" + x + ", " + y + "): " + receivedId);

                    // Cerca e piazza il componente in base all'ID ricevuto
                    findAndPlaceComponent(receivedId, x, y);
                }
            });

        } catch (Exception e) {
            System.err.println("Errore durante la richiesta al server per ottenere l'ID del componente: " + e.getMessage());
        }
    }

    /**
     * Cerca il componente nel JSON in base al suo ID e lo piazza sulla GUI.
     */
    private void findAndPlaceComponent(String componentId, int x, int y) {
        try {
            // Cerca il JSON del componente con l'ID corrispondente
            JSONObject componentJson = findComponentJsonById(componentId);

            if (componentJson == null) {
                System.err.println("Componente con ID " + componentId + " non trovato nel file JSON.");
                return;
            }

            // Recupera i dettagli del componente (esempio, immagine) e piazzalo
            String imagePath = componentJson.getString("img");
            placeImageOnGUI(imagePath, x, y);

        } catch (Exception e) {
            System.err.println("Errore durante il piazzamento del componente: " + e.getMessage());
            e.printStackTrace();
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
     * Piazza un'immagine della nave sulla GUI in base alla posizione `(x, y)`.
     */
    private void placeImageOnGUI(String imagePath, int x, int y) {
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

            // Aggiungi l'immagine alla GUI
            Platform.runLater(() -> shipboardContainer.getChildren().add(componentImageView));
        } catch (Exception e) {
            System.err.println("Errore durante il posizionamento dell'immagine: " + e.getMessage());
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
}