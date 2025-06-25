package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.MessagePkg.Message;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsSceneController extends GuiController implements Initializable {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupFieldValidation();
        validationMessage.setVisible(false);

        borderPane.setStyle("-fx-background-color: black;");

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });
    }

    private void setupFieldValidation() {
        BuildShipSceneController.setUpNumberField(numberOfPlayersField, shipboardLevelField, gameModeField);

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
    public void onCreateLobbyClick() throws IOException {
        if (validateInputs()) {
            String[] args = new String[3];
            args[0] = numberOfPlayersField.getText();
            args[1] = shipboardLevelField.getText();
            args[2] = gameModeField.getText();

            Message message = getGuiRoot().getClient().getMessageGenerator().generate("create_lobby", java.util.Arrays.asList(args));
            getGuiRoot().getClient().sendMessage(message);

            hideValidationMessage();
        }
    }

    private boolean validateInputs() {
        String playersText = numberOfPlayersField.getText();
        String shipboardText = shipboardLevelField.getText();
        String gameModeText = gameModeField.getText();

        if (playersText.isEmpty() || shipboardText.isEmpty() || gameModeText.isEmpty()) {
            showValidationError("All fields are required!");
            return false;
        }

        try {
            int numPlayers = Integer.parseInt(playersText);
            int shipboardLevel = Integer.parseInt(shipboardText);
            int gameMode = Integer.parseInt(gameModeText);

            if (numPlayers < 2 || numPlayers > 4) {
                showValidationError("Number of players must be between 2 and 4!");
                return false;
            }

            if (shipboardLevel <1 || shipboardLevel > 2) {
                showValidationError("Shipboard level must be between 1 and 2!");
                return false;
            }

            if (gameMode != 0 && gameMode != 1) {
                showValidationError("Game mode must be 0 or 1!");
                return false;
            }

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

    private void hideValidationMessage() {
        Platform.runLater(() -> {
            validationMessage.setVisible(false);
        });
    }

    public void onLobbyCreated() {
        Platform.runLater(() -> {
            validationMessage.setText("Lobby created successfully!");
            validationMessage.setStyle("-fx-text-fill: " + "green" + "; -fx-font-size: 14px; -fx-font-weight: bold;");
            validationMessage.setVisible(true);
        });
    }
}