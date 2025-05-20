package org.example.UIPkg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.ServerPkg.Model.ForView.GameView;

import java.nio.file.Paths;

public class GUI extends Application implements UI {
    private Button button;
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle("Galaxy Trucker");
        button = new Button();
        button.setText("Start Game");

        String videoPath = Paths.get("src/main/resources/org.example.gc31/animatedBackgrounds/159088-818219574.mp4").toUri().toString();
        //String videoPath = Paths.get("src/main/resources/org.example.gc31/animatedBackgrounds/174453-851475315.mp4").toUri().toString();

        Media backgroundMedia = new Media(videoPath);
        mediaPlayer = new MediaPlayer(backgroundMedia);
        mediaPlayer.setAutoPlay(true);

        mediaView = new MediaView(mediaPlayer);
        mediaView.fitWidthProperty().bind(stage.widthProperty());
        mediaView.fitHeightProperty().bind(stage.heightProperty());
        mediaView.setPreserveRatio(true);

        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.pause();
        });

        button.setOnAction(event -> {
            System.out.println("Start Game button clicked!");
            // Qui puoi aggiungere la logica per iniziare il gioco o cambiare scena
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(mediaView, button);

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void addGameUpdate(GameView game) {
        // Implementa qui come aggiornare la UI con i dati del gioco
    }
}