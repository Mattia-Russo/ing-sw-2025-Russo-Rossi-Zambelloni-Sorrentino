package org.example.UIPkg;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.ServerPkg.Model.ForView.GameView;
import javafx.scene.*;
public class GUI extends Application implements UI{
    private Button button;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle("GalaxyTrucker");
        button = new Button();
        button.setText("StartGame");

        StackPane root = new StackPane();
        root.getChildren().add(button);

        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void addGameUpdate(GameView game) {

    }


}
