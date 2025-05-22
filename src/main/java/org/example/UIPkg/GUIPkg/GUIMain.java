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
        this.primaryStage = stage;

        // Imposta la GUI principale
        guiRoot.setGuiMain(this);

        try {
            guiRoot.goToFirstScene();
            // IMPORTANTE: Notifica che la GUI è pronta DOPO aver caricato la prima scena
            guiRoot.notifyGuiReady();
        } catch (IOException e) {
            e.printStackTrace();
            // Anche in caso di errore, notifica per evitare deadlock
            guiRoot.notifyGuiReady();
        }
    }

    public Object getControllerForScene(Scene scene) {
        return sceneControllerMap.get(scene);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}