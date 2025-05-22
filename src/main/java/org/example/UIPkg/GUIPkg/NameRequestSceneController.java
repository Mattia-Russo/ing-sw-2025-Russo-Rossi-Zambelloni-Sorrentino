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

import java.net.URL;
import java.nio.file.Paths;
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
    private VBox controls;

    private MediaPlayer mediaPlayer;

    private GUI gui;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        confirmNameButton.setVisible(false);
        nameInvalid.setVisible(false);
        createLobbyButton.setVisible(false);
        joinLobbyButton.setVisible(false);

        String videoPath = Paths.get("src/main/resources/org.example/animatedBackgrounds/159088-818219574.mp4").toUri().toString();
        Media backgroundMedia = new Media(videoPath);

        mediaPlayer = new MediaPlayer(backgroundMedia);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(borderPane.widthProperty());
        mediaView.fitHeightProperty().bind(borderPane.heightProperty());
        mediaView.setPreserveRatio(false);
        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        borderPane.setCenter(mediaView);

        controls = new VBox(10, askName, nameBox, confirmNameButton, nameInvalid);
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
            gui.getClient().insertName(name);
        }
    }

    public void printNameInvalid() {
        nameInvalid.setText("Name already taken");
        nameInvalid.setVisible(true);
    }

    public void askName(){
        askName.setText("Enter your name:");
        askName.setVisible(true);
        confirmNameButton.setVisible(true);
    }

    public void nameAccepted(){
        Platform.runLater(() -> {
            // Nascondi i controlli del nome
            nameBox.setVisible(false);
            confirmNameButton.setVisible(false);
            askName.setVisible(false);

            // Mostra i pulsanti per creare/unirsi alla lobby
            createLobbyButton.setVisible(true);
            joinLobbyButton.setVisible(true);

            // Oppure vai direttamente alla prossima scena
            // gui.goToSettingsScene();
        });
    }
}