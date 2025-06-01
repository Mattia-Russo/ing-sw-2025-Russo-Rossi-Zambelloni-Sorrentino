package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.rmi.RemoteException;

public class WaitingRoomSceneController extends GuiController implements Initializable {

    public VBox playersContainer;
    @FXML
    private BorderPane borderPane;

    @FXML
    private Label settingsTitle;

    @FXML
    private Label playersLabel;

    @FXML
    private Label gameModeLabel;

    @FXML
    private Label shipboardLevelLabel;

    @FXML
    private Label maxPlayersLabel;

    @FXML
    private ListView<String> playersListView;

    @FXML
    private Button startGameButton;

    @FXML
    private Label statusMessage;

    private ObservableList<String> playersList;

    private int maxPlayers;
    private boolean isLobbyCreator = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playersList = FXCollections.observableArrayList();
        playersListView.setItems(playersList);

        borderPane.setStyle("-fx-background-color: black;");

        startGameButton.setVisible(false);
        statusMessage.setVisible(false);

        setupLabelStyles();

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setResizable(true);
            }
        });
    }

    private void setupLabelStyles() {
        settingsTitle.setStyle("-fx-text-fill: yellow; -fx-font-size: 20px; -fx-font-weight: bold;");
        playersLabel.setStyle("-fx-text-fill: yellow; -fx-font-size: 20px; -fx-font-weight: bold;");

        gameModeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        shipboardLevelLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        maxPlayersLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        playersListView.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white;");
    }

    public void updatePlayersList(List<String> players) {
        Platform.runLater(() -> {
            playersList.clear();
            playersList.addAll(players);
            playersList.set(getGuiRoot().getNameIndex(), players.get(getGuiRoot().getNameIndex()) + " (You)" );

            playersLabel.setText("Players (" + players.size() + "/" + maxPlayers + ")");

            updateStartGameButtonVisibility();
        });
    }

    private void updateStartGameButtonVisibility() {
        if (isLobbyCreator && playersList.size() >= 2 && playersList.size() <= maxPlayers) {
            startGameButton.setVisible(true);
            statusMessage.setText("Ready to start the game!");
            statusMessage.setStyle("-fx-text-fill: green; -fx-font-size: 14px; -fx-font-weight: bold;");
            statusMessage.setVisible(true);
        } else if (isLobbyCreator && playersList.size() < 2) {
            startGameButton.setVisible(false);
            statusMessage.setText("Waiting for more players to join...");
            statusMessage.setStyle("-fx-text-fill: orange; -fx-font-size: 14px; -fx-font-weight: bold;");
            statusMessage.setVisible(true);
        } else if (!isLobbyCreator) {
            startGameButton.setVisible(false);
            statusMessage.setText("Waiting for lobby creator to start the game...");
            statusMessage.setStyle("-fx-text-fill: cyan; -fx-font-size: 14px; -fx-font-weight: bold;");
            statusMessage.setVisible(true);
        }
    }

    public void setLobbyCreator(boolean isCreator) {
        this.isLobbyCreator = isCreator;
        updateStartGameButtonVisibility();
    }

    @FXML
    public void onStartGameClick() throws RemoteException {
        if (isLobbyCreator && playersList.size() >= 2) {

            getGuiRoot().getClient().sendMessage(getGuiRoot().getClient().getMessageGenerator().generate("start_game", new ArrayList<>()));

            statusMessage.setText("Starting game...");
            statusMessage.setStyle("-fx-text-fill: yellow; -fx-font-size: 14px; -fx-font-weight: bold;");
            startGameButton.setDisable(true);
        }
    }

    public void onGameStarted() {
        Platform.runLater(() -> {
            statusMessage.setText("Game is starting!");
            statusMessage.setStyle("-fx-text-fill: lime; -fx-font-size: 16px; -fx-font-weight: bold;");
            startGameButton.setVisible(false);
        });
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        Platform.runLater(() -> {
            maxPlayersLabel.setText("Max Players: " + maxPlayers);
            updateStartGameButtonVisibility();
        });
    }

    public void setShipboardLevel(int shipboardLevel) {
        Platform.runLater(() -> {
            shipboardLevelLabel.setText("Shipboard Level: " + shipboardLevel);
        });
    }

    public void setGameMode(int gameMode) {
        Platform.runLater(() -> {
            String modeText = (gameMode == 0) ? "Easy Mode" : "Normal Mode";
            gameModeLabel.setText("Game Mode: " + modeText);
        });
    }
}