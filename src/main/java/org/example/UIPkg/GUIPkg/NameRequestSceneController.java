package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import org.example.MessagePkg.Message;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NameRequestSceneController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private TextField nameBox;

    @FXML
    private Button confirmNameButton;

    @FXML
    private Button createLobbyButton;

    @FXML
    private Button joinLobbyButton;

    @FXML
    private Label nameInvalid;

    @FXML
    private Label askName;

    @FXML
    private Label lobbyMessage;

    @FXML
    private VBox controls;

    private MediaPlayer mediaPlayer;
    private GUI gui;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inizialmente nascondi tutti i controlli tranne quelli necessari
        confirmNameButton.setVisible(false);
        nameInvalid.setVisible(false);
        createLobbyButton.setVisible(false);
        joinLobbyButton.setVisible(false);

        // Inizializza la nuova label per i messaggi della lobby
        if (lobbyMessage == null) {
            lobbyMessage = new Label();
            lobbyMessage.setStyle("-fx-text-fill: orange; -fx-font-size: 14px; -fx-font-weight: bold;");
        }
        lobbyMessage.setVisible(false);

        // Setup del video di background
        String videoPath = Paths.get("src/main/resources/org.example/animatedBackgrounds/159088-818219574.mp4").toUri().toString();
        Media backgroundMedia = new Media(videoPath);

        mediaPlayer = new MediaPlayer(backgroundMedia);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(borderPane.widthProperty());
        mediaView.fitHeightProperty().bind(borderPane.heightProperty());
        mediaView.setPreserveRatio(false);
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        // Ricrea il VBox con tutti i controlli inclusa la nuova label
        controls = new VBox(10, askName, nameBox, confirmNameButton, nameInvalid, createLobbyButton, joinLobbyButton, lobbyMessage);
        controls.setAlignment(Pos.CENTER);

        borderPane.setCenter(new StackPane(mediaView, controls));
    }

    @FXML
    public void onConfirmNameButtonClick() {
        nameInvalid.setVisible(false);
        String name = nameBox.getText();

        if (name == null || name.isEmpty()) {
            nameInvalid.setText("Name cannot be empty");
            nameInvalid.setVisible(true);
        } else {
            gui.getClient().registerName(name);
        }
    }

    @FXML
    public void onCreateLobbyButtonClick() {
        lobbyMessage.setVisible(false);
        gui.getClient().notifyCreatingLobby();

        Platform.runLater(() -> {
            try {
                gui.goToSettingsScene();
            } catch (IOException e) {
                e.printStackTrace();
                lobbyMessage.setText("Error loading settings scene!");
                lobbyMessage.setStyle("-fx-text-fill: red; -fx-font-size: 14px; -fx-font-weight: bold;");
                lobbyMessage.setVisible(true);
            }
        });
    }

    @FXML
    public void onJoinLobbyButtonClick() throws RemoteException {
        lobbyMessage.setVisible(false);

        Message message = gui.getClient().getMessageGenerator().generate("join_lobby", new ArrayList<>());
        gui.getClient().sendMessage(message);

        // passaggio alla scena con lista giocatori correnti

    }

    public void onNameAccepted() {
        Platform.runLater(() -> {
            askName.setVisible(false);
            nameBox.setVisible(false);
            confirmNameButton.setVisible(false);
            nameInvalid.setVisible(false);

            createLobbyButton.setVisible(true);
            joinLobbyButton.setVisible(true);
        });
    }

    public void printNameInvalid() {
        Platform.runLater(() -> {
            nameInvalid.setText("Name already taken");
            nameInvalid.setVisible(true);
        });
    }

    public void askName() {
        Platform.runLater(() -> {
            askName.setText("Enter your name:");
            askName.setVisible(true);
            confirmNameButton.setVisible(true);
        });
    }
}