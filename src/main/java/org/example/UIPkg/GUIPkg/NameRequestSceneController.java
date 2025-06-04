package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.MessagePkg.Message;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NameRequestSceneController extends GuiController implements Initializable {

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
    private MediaView mediaView;
    private ImageView titleImageView;
    private VBox nameInputSection;
    private HBox lobbyButtonsSection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTitleImage();
        setupNameInputSection();
        setupLobbyButtonsSection();
        setupInitialState();
        setupBackground();
        layoutComponents();

        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            if (stage != null) {
                stage.setWidth(800);
                stage.setHeight(600);
                stage.centerOnScreen();
            }
        });
    }

    private void setupTitleImage() {
        try {
            InputStream imageStream = getClass().getResourceAsStream("/org.example/cardboard/TitleImage.jpg");
            if (imageStream != null) {
                Image titleImage = new Image(imageStream);
                titleImageView = new ImageView(titleImage);
                titleImageView.setPreserveRatio(true);
                titleImageView.setFitWidth(400);
                titleImageView.setFitHeight(200);
            } else {
                titleImageView = new ImageView();
                titleImageView.setFitWidth(400);
                titleImageView.setFitHeight(100);
                System.err.println("Title image not found in resources.");
            }
        } catch (Exception e) {
            titleImageView = new ImageView();
            titleImageView.setFitWidth(400);
            titleImageView.setFitHeight(100);
            System.err.println("Error loading title image: " + e.getMessage());
        }
    }

    private void setupNameInputSection() {
        askName.setText("Enter your name:");
        askName.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        nameBox.setStyle("-fx-font-size: 14px; -fx-pref-width: 200px;");

        confirmNameButton.setStyle("-fx-font-size: 14px; -fx-pref-width: 120px;");

        nameInvalid.setStyle("-fx-text-fill: red; -fx-font-size: 12px; -fx-font-weight: bold;");

        nameInputSection = new VBox(10);
        nameInputSection.setAlignment(Pos.CENTER);
        nameInputSection.getChildren().addAll(askName, nameBox, confirmNameButton, nameInvalid);
        nameInputSection.setPadding(new Insets(20, 0, 20, 0));
    }

    private void setupLobbyButtonsSection() {
        String buttonStyle = "-fx-font-size: 14px; -fx-pref-width: 150px; -fx-pref-height: 40px;";
        createLobbyButton.setStyle(buttonStyle);
        joinLobbyButton.setStyle(buttonStyle);

        if (lobbyMessage == null) {
            lobbyMessage = new Label();
        }
        lobbyMessage.setStyle("-fx-text-fill: orange; -fx-font-size: 14px; -fx-font-weight: bold;");

        lobbyButtonsSection = new HBox(20);
        lobbyButtonsSection.setAlignment(Pos.CENTER);
        lobbyButtonsSection.getChildren().addAll(createLobbyButton, joinLobbyButton);
        lobbyButtonsSection.setPadding(new Insets(20, 0, 20, 0));
    }

    private void setupInitialState() {
        askName.setVisible(true);
        nameBox.setVisible(true);
        confirmNameButton.setVisible(true);
        nameInvalid.setVisible(false);
        createLobbyButton.setVisible(false);
        joinLobbyButton.setVisible(false);
        lobbyMessage.setVisible(false);
    }

    private void setupBackground() {
        try {
            String videoPath = Paths.get("src/main/resources/org.example/animatedBackgrounds/159088-818219574.mp4").toUri().toString();
            Media backgroundMedia = new Media(videoPath);
            mediaPlayer = new MediaPlayer(backgroundMedia);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            mediaView = new MediaView(mediaPlayer);

            mediaView.fitWidthProperty().bind(borderPane.widthProperty());
            mediaView.fitHeightProperty().bind(borderPane.heightProperty());
            mediaView.setPreserveRatio(false);

        } catch (Exception e) {
            System.err.println("Error loading background video: " + e.getMessage());
            mediaPlayer = null;
            mediaView = null;
        }

        borderPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void layoutComponents() {
        VBox mainContainer = new VBox(30);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));

        mainContainer.getChildren().addAll(
                titleImageView,
                nameInputSection,
                lobbyButtonsSection,
                lobbyMessage
        );

        if (mediaView != null) {
            StackPane centerPane = new StackPane();
            centerPane.getChildren().addAll(mediaView, mainContainer);
            borderPane.setCenter(centerPane);
        } else {
            borderPane.setCenter(mainContainer);
        }
    }

    @FXML
    public void onConfirmNameButtonClick() {
        nameInvalid.setVisible(false);
        String name = nameBox.getText();

        if (name == null || name.isEmpty()) {
            nameInvalid.setText("Name cannot be empty");
            nameInvalid.setVisible(true);
        } else {
            getGuiRoot().getClient().registerName(name);
        }
    }

    @FXML
    public void onCreateLobbyButtonClick() throws RemoteException {
        lobbyMessage.setVisible(false);
        getGuiRoot().getClient().notifyCreatingLobby();
    }

    public void onCreateLobbyAccepted(){
        Platform.runLater(() -> {
            try {
                getGuiRoot().goToSettingsScene();
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

        Message message = getGuiRoot().getClient().getMessageGenerator().generate("join_lobby", new ArrayList<>());
        getGuiRoot().getClient().sendMessage(message);
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
}