package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.example.MessagePkg.Message;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SettingsSceneController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField numberOfPlayersField;

    @FXML
    private TextField shipboardLevelField;

    @FXML
    private TextField gameModeField;

    @FXML
    private Button createLobbyButton;

    @FXML
    private Label validationMessage;

    @FXML
    private Label playersLabel;

    @FXML
    private Label shipboardLabel;

    @FXML
    private Label gameModeLabel;

    private GUI gui;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Imposta validazione in tempo reale sui campi
        setupFieldValidation();

        // Nascondi inizialmente il messaggio di validazione
        validationMessage.setVisible(false);

        // Imposta lo sfondo nero per il BorderPane
        borderPane.setStyle("-fx-background-color: black;");
    }

    private void setupFieldValidation() {
        numberOfPlayersField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberOfPlayersField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validazione per il campo livello shipboard
        shipboardLevelField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                shipboardLevelField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validazione per il campo game mode
        gameModeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                gameModeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Listener per game mode - se è 0, imposta automaticamente shipboard level a 1
        gameModeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if ("0".equals(newValue)) {
                shipboardLevelField.setText("1");
                shipboardLevelField.setDisable(true);
                shipboardLabel.setText("Level of the Shipboard (Auto-set)");
            } else {
                shipboardLevelField.setDisable(false);
                shipboardLabel.setText("Level of the Shipboard");
            }
        });
    }

    @FXML
    public void onCreateLobbyClick() throws RemoteException {
        if (validateInputs()) {
            String[] args = new String[3];
            args[0] = numberOfPlayersField.getText();
            args[1] = shipboardLevelField.getText();
            args[2] = gameModeField.getText();

            Message message = gui.getClient().getMessageGenerator().generate("create_lobby", java.util.Arrays.asList(args));
            gui.getClient().sendMessage(message);

            hideValidationMessage();
        }
    }

    private boolean validateInputs() {
        String playersText = numberOfPlayersField.getText();
        String shipboardText = shipboardLevelField.getText();
        String gameModeText = gameModeField.getText();

        // Controlla se i campi sono vuoti
        if (playersText.isEmpty() || shipboardText.isEmpty() || gameModeText.isEmpty()) {
            showValidationError("All fields are required!");
            return false;
        }

        try {
            int numPlayers = Integer.parseInt(playersText);
            int shipboardLevel = Integer.parseInt(shipboardText);
            int gameMode = Integer.parseInt(gameModeText);

            // Valida numero di giocatori
            if (numPlayers < 2 || numPlayers > 4) {
                showValidationError("Number of players must be between 2 and 4!");
                return false;
            }

            // Valida livello shipboard
            if (shipboardLevel < 1 || shipboardLevel > 3) {
                showValidationError("Shipboard level must be between 1 and 3!");
                return false;
            }

            // Valida game mode
            if (gameMode != 0 && gameMode != 1) {
                showValidationError("Game mode must be 0 or 1!");
                return false;
            }

            // Valida combinazione game mode 0 e shipboard level
            if (gameMode == 0 && shipboardLevel != 1) {
                showValidationError("With game mode 0, shipboard level must be 1!");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            showValidationError("Please enter valid numbers!");
            return false;
        }
    }

    private void showValidationError(String message) {
        Platform.runLater(() -> {
            validationMessage.setText(message);
            validationMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
            validationMessage.setVisible(true);
        });
    }

    private void showMessage(String message, String color) {
        Platform.runLater(() -> {
            validationMessage.setText(message);
            validationMessage.setStyle("-fx-text-fill: " + color + "; -fx-font-size: 14px; -fx-font-weight: bold;");
            validationMessage.setVisible(true);
        });
    }

    private void hideValidationMessage() {
        Platform.runLater(() -> {
            validationMessage.setVisible(false);
        });
    }

    public void onLobbyCreated() {
        Platform.runLater(() -> {
            showMessage("Lobby created successfully!", "green");
        });
    }
}