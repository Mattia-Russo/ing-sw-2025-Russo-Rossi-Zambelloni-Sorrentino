package org.example.UIPkg.GUIPkg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.UI;

import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GUI extends Application implements UI {
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    BlockingQueue<GameView> gameUpdatesQueue;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        gameUpdatesQueue = new LinkedBlockingQueue<>();
        Thread UpdateThread = new Thread(() -> {
            try {
                while (true) {
                    if(!gameUpdatesQueue.isEmpty()) {
                        draw();
                    }
                }
            }catch (Exception e) {
                System.err.println("Error sending connection update to server: " + e.getMessage());
            }
        });
        UpdateThread.setDaemon(false);
        UpdateThread.start();
    }

    public void draw(){

    }

    public void drawStart(Stage stage){
        stage.setTitle("Galaxy Trucker");

        String videoPath = Paths.get("src/main/resources/org.example.gc31/animatedBackgrounds/159088-818219574.mp4").toUri().toString();

        Media backgroundMedia = new Media(videoPath);
        mediaPlayer = new MediaPlayer(backgroundMedia);
        mediaPlayer.setAutoPlay(true);

        mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(stage.widthProperty());
        mediaView.fitHeightProperty().bind(stage.heightProperty());
        mediaView.setPreserveRatio(true);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name...");
        nameField.setMaxWidth(200);

        Button createLobbyButton = new Button("Create Lobby");
        Button joinLobbyButton = new Button("Join Lobby");

        createLobbyButton.setVisible(false);
        joinLobbyButton.setVisible(false);

        VBox vBox = new VBox(20, nameField, createLobbyButton, joinLobbyButton);
        vBox.setStyle("-fx-alignment: center;");

        nameField.setOnAction(event -> {
            String playerName = nameField.getText();
            if (!playerName.trim().isEmpty()) {
                System.out.println("Player name: " + playerName);

                nameField.setVisible(false);
                createLobbyButton.setVisible(true);
                joinLobbyButton.setVisible(true);
            }
        });

        createLobbyButton.setOnAction(event -> {
            System.out.println("Create Lobby button clicked!");
            vBox.getChildren().clear();
        });

        // Impostando l'azione per il pulsante "Join Lobby"
        joinLobbyButton.setOnAction(event -> {
            System.out.println("Join Lobby button clicked!");
            vBox.getChildren().clear();
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(mediaView, vBox);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();}

    private void stopBackgroundVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        mediaView.setVisible(false);
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void addGameUpdate(GameView game){
        try {
            gameUpdatesQueue.put(game);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error inserting game update", e);
        }
    }
}