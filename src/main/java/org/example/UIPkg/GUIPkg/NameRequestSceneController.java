package org.example.UIPkg.GUIPkg;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

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

    private MediaPlayer mediaPlayer;

    private GUI gui;

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameBox.setPromptText("Enter your name");
        nameInvalid.setVisible(false);
        createLobbyButton.setVisible(false);
        joinLobbyButton.setVisible(false);

        String videoPath = Paths.get("src/main/resources/org.example.gc31/animatedBackgrounds/159088-818219574.mp4").toUri().toString();
        Media backgroundMedia = new Media(videoPath);

        mediaPlayer = new MediaPlayer(backgroundMedia);
        mediaPlayer.setAutoPlay(true);

        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(borderPane.widthProperty());
        mediaView.fitHeightProperty().bind(borderPane.heightProperty());
        mediaView.setPreserveRatio(true);

        borderPane.setCenter(mediaView);
    }

    @FXML
    public void onConfirmNameButtonClick() {
        nameInvalid.setVisible(false);
        String name = nameBox.getText();
        if (name == null || name.isEmpty()) {
            nameInvalid.setVisible(true);
            nameInvalid.setText("Name cannot be empty");
        } else {
            gui.getClient().insertName(name);
        }
    }

    public void printNameInvalid() {
        nameInvalid.setText("Name already taken");
        nameInvalid.setVisible(true);
    }
}