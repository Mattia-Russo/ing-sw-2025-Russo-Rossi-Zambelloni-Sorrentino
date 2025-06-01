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
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BuildShipSceneController extends GuiController implements Initializable {

    private static final String COMPONENT_JSON_PATH = "/org.example/components.json"; // Adatta il path

    @FXML
    private BorderPane borderPane;

    @FXML
    private ImageView shipboardImageView;

    @FXML
    private ImageView currentComponentImageView;

    @FXML
    private Pane shipboardContainer;

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
    private TextField xPositionField;

    @FXML
    private TextField yPositionField;

    @FXML
    private Label validationMessage;

    @FXML
    private Label xLabel;

    @FXML
    private Label yLabel;

    // Dati dei componenti
    private List<ComponentData> allComponents;
    private ComponentData currentComponent;

    // Classe per rappresentare i dati del componente
    private static class ComponentData {
        public String type;
        public int id;
        public String direction;
        public List<String> connectors;
        public boolean isCentral;
        public String img;
        public Integer capacity;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFieldValidation();
        setupUI();
        loadComponentsFromJSON();
        loadShipboardImage();
        //todo loadInitialCabin();
        validationMessage.setVisible(false);
    }

    private void setupUI() {
        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });

        // Inizialmente disabilita i pulsanti che richiedono un componente
        discardComponentButton.setDisable(true);
        rotateLeftButton.setDisable(true);
        rotateRightButton.setDisable(true);
        placeComponentButton.setDisable(true);
    }

    private void setupFieldValidation() {
        // Validazione per accettare solo numeri nei campi X e Y
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

    //todo cambia in base al livello selezionato
    private void loadShipboardImage() {
        try {
            String imagePath = "src/main/resources/org.example/cardboard/cardboard-1.jpg";
            Image shipboardImage = new Image(imagePath);
            shipboardImageView.setImage(shipboardImage);
            shipboardImageView.setFitWidth(400); // Adatta le dimensioni come necessario
            shipboardImageView.setFitHeight(300);
            shipboardImageView.setPreserveRatio(true);
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dell'immagine della navicella: " + e.getMessage());
            showValidationError("Errore nel caricamento dell'immagine della navicella!");
        }
    }

    @FXML
    public void onPickComponentClick() {
        try {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("pick_component", java.util.Arrays.asList());
            getGuiRoot().getClient().sendMessage(message);

            // Abilita i pulsanti ora che abbiamo un componente
            enableComponentButtons();
            hideValidationMessage();

        } catch (IOException e) {
            showValidationError("Errore nell'invio del messaggio pick_component!");
            e.printStackTrace();
        }
    }

    @FXML
    public void onDiscardComponentClick() {
        try {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("discard_component", java.util.Arrays.asList());
            getGuiRoot().getClient().sendMessage(message);

            // Disabilita i pulsanti e rimuovi l'immagine del componente
            disableComponentButtons();
            currentComponentImageView.setImage(null);
            hideValidationMessage();

        } catch (IOException e) {
            showValidationError("Errore nell'invio del messaggio discard_component!");
            e.printStackTrace();
        }
    }

    @FXML
    public void onRotateLeftClick() {
        try {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("rotate_left", java.util.Arrays.asList());
            getGuiRoot().getClient().sendMessage(message);
            hideValidationMessage();

        } catch (IOException e) {
            showValidationError("Errore nell'invio del messaggio rotate_left!");
            e.printStackTrace();
        }
    }

    @FXML
    public void onRotateRightClick() {
        try {
            Message message = getGuiRoot().getClient().getMessageGenerator().generate("rotate_right", java.util.Arrays.asList());
            getGuiRoot().getClient().sendMessage(message);
            hideValidationMessage();

        } catch (IOException e) {
            showValidationError("Errore nell'invio del messaggio rotate_right!");
            e.printStackTrace();
        }
    }

    @FXML
    public void onPlaceComponentClick() {
        if (validatePlacementInputs()) {
            try {
                String[] args = new String[2];
                args[0] = xPositionField.getText(); // X position
                args[1] = yPositionField.getText(); // Y position

                Message message = getGuiRoot().getClient().getMessageGenerator().generate("place_component", java.util.Arrays.asList(args));
                getGuiRoot().getClient().sendMessage(message);

                // Dopo aver piazzato il componente, pulisci i campi e disabilita i pulsanti
                clearPlacementFields();
                disableComponentButtons();
                currentComponentImageView.setImage(null);
                hideValidationMessage();

            } catch (IOException e) {
                showValidationError("Errore nell'invio del messaggio place_component!");
                e.printStackTrace();
            }
        }
    }

    private boolean validatePlacementInputs() {
        String xText = xPositionField.getText();
        String yText = yPositionField.getText();

        if (xText.isEmpty() || yText.isEmpty()) {
            showValidationError("Inserisci entrambe le coordinate X e Y!");
            return false;
        }

        try {
            int x = Integer.parseInt(xText);
            int y = Integer.parseInt(yText);

            // Validazione range (colonne 0-6, righe 0-4)
            if (x < 0 || x > 6) {
                showValidationError("La coordinata X deve essere tra 0 e 6!");
                return false;
            }

            if (y < 0 || y > 4) {
                showValidationError("La coordinata Y deve essere tra 0 e 4!");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            showValidationError("Inserisci numeri validi per le coordinate!");
            return false;
        }
    }

    private void enableComponentButtons() {
        discardComponentButton.setDisable(false);
        rotateLeftButton.setDisable(false);
        rotateRightButton.setDisable(false);
        placeComponentButton.setDisable(false);
    }

    private void disableComponentButtons() {
        discardComponentButton.setDisable(true);
        rotateLeftButton.setDisable(true);
        rotateRightButton.setDisable(true);
        placeComponentButton.setDisable(true);
    }

    private void clearPlacementFields() {
        xPositionField.clear();
        yPositionField.clear();
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

    private void loadComponentsFromJSON() {
        allComponents = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(COMPONENT_JSON_PATH)) {
            if (is == null) {
                System.err.println("File JSON non trovato: " + COMPONENT_JSON_PATH);
                return;
            }

            JSONArray jsonArray = new JSONArray(new JSONTokener(is));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                ComponentData component = parseComponent(json);
                if (component != null) {
                    allComponents.add(component);
                }
            }
            System.out.println("Caricati " + allComponents.size() + " componenti dal JSON");
        } catch (Exception e) {
            System.err.println("Errore nel caricamento dei componenti: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ComponentData parseComponent(JSONObject json) {
        try {
            ComponentData component = new ComponentData();
            component.type = json.getString("type");
            component.id = json.getInt("id");
            component.direction = json.getString("direction");
            component.img = json.getString("img");

            component.connectors = new ArrayList<>();
            JSONArray connectorsArray = json.getJSONArray("connectors");
            for (int i = 0; i < connectorsArray.length(); i++) {
                component.connectors.add(connectorsArray.getString(i));
            }

            if (json.has("isCentral")) {
                component.isCentral = json.getBoolean("isCentral");
            }
            if (json.has("capacity")) {
                component.capacity = json.getInt("capacity");
            }

            return component;
        } catch (Exception e) {
            System.err.println("Errore nel parsing del componente: " + e.getMessage());
            return null;
        }
    }
    public void updateCurrentComponentImage(String imagePath) {
        Platform.runLater(() -> {
            try {
                Image componentImage = new Image("file:" + imagePath);
                currentComponentImageView.setImage(componentImage);
                currentComponentImageView.setFitWidth(100);
                currentComponentImageView.setFitHeight(100);
                currentComponentImageView.setPreserveRatio(true);
            } catch (Exception e) {
                System.err.println("Errore nel caricamento dell'immagine del componente: " + e.getMessage());
            }
        });
    }

    public void showSuccessMessage(String message) {
        Platform.runLater(() -> {
            validationMessage.setText(message);
            validationMessage.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
            validationMessage.setVisible(true);
        });
    }
}