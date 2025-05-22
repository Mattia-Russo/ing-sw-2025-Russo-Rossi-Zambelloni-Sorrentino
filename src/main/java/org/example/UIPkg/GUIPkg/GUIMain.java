package org.example.UIPkg.GUIPkg;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GUIMain extends Application {




    private Stage primaryStage;
    private static GUIMain main;
    private static GUI guiRoot;
    public Map<Scene, Object> sceneControllerMap = new HashMap<>();

    public static void main(String[] args) {
        launch();
    }

    public GUIMain() {
        main = this;
    }

    public static void startGui(GUI root) {
        guiRoot = root;
        Application.launch(GUIMain.class);

    }

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = new Stage();
        guiRoot.setGuiMain(this);
        guiRoot.goToFirstScene();
    }

    public Object getControllerForScene(Scene scene) {
        return sceneControllerMap.get(scene);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}