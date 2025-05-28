package org.example.UIPkg.GUIPkg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GUIMain extends Application {
    private Stage stage;
    private static GUI guiRoot;
    private static GUIMain guiMain;
    public Map<String, Scene> sceneMap;
    public Map<String, GuiController> controllerMap;
    private Scene currentScene;

    public static final String NAME_REQUEST_SCENE = "/org.example/FxmlPkg/nameRequestScene.fxml";
    public static final String SETTINGS_SCENE = "/org.example/FxmlPkg/settingsScene.fxml";
    public static final String WAITING_ROOM_SCENE = "/org.example/FxmlPkg/waitingRoomScene.fxml";

    public GUIMain() {
        guiMain = this;
        this.controllerMap = new HashMap<>();
        this.sceneMap = new HashMap<>();
    }

    public static void startGui(GUI root) {
        guiMain = new GUIMain();
        GUIMain.guiRoot = root;
        Application.launch(GUIMain.class);
    }

    public static GUIMain getGuiMain() {
        if(guiMain == null){
            throw new RuntimeException("GUIMain not initialized!");
        }
        return guiMain;
    }

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        goToFirstScene();

        currentScene = sceneMap.get(NAME_REQUEST_SCENE);
        if (currentScene != null) {
            stage.setScene(currentScene);
            stage.centerOnScreen();
            stage.show();
        } else {
            System.err.println("Error initializing first scene!");
        }
    }

    public void goToFirstScene() throws IOException {
        List<String> FxmlFiles = new ArrayList<>(Arrays.asList(NAME_REQUEST_SCENE, SETTINGS_SCENE, WAITING_ROOM_SCENE));
        try{
            for(String fxmlFile : FxmlFiles){
                FXMLLoader loader = new FXMLLoader(GUI.class.getResource(fxmlFile));
                this.sceneMap.put(fxmlFile, new Scene(loader.load(), 800, 600));
                GuiController controller = loader.getController();
                controller.setGui(guiRoot);
                controllerMap.put(fxmlFile, controller);
            }
        } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

    public Map<String, GuiController> getControllerMap() {
        return this.controllerMap;
    }

    public Map<String, Scene> getSceneMap() {
        return sceneMap;
    }

    public void changeScene(String sceneName) {
        Platform.runLater(() -> {
            Scene newScene = sceneMap.get(sceneName);
            if (newScene != null) {
                currentScene = newScene;
                stage.setScene(currentScene);
                stage.centerOnScreen();
                stage.show();
            }
        });
    }

    public String getCurrentSceneName() {
        for (String key : sceneMap.keySet()) {
            if (sceneMap.get(key) == currentScene) {
                return key;
            }
        }
        return NAME_REQUEST_SCENE;

    }

    public Stage getStage() {
        return this.stage;
    }
}