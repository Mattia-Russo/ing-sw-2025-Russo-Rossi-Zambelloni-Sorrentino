package org.example.UIPkg.GUIPkg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
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
    public static final String BUILD_SHIP_SCENE = "/org.example/FxmlPkg/buildShipScene.fxml";
    public static final String ADD_ALIEN_SCENE = "/org.example/FxmlPkg/addAlienScene.fxml";
    public static final String FIX_SHIP_SCENE = "/org.example/FxmlPkg/fixShipScene.fxml";
    public static final String SHIP_WRECK_SCENE = "/org.example/FxmlPkg/shipWreckScene.fxml";

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
        guiRoot.startUpdateThread();

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

    public void goToFirstScene() {
        List<String> FxmlFiles = new ArrayList<>(Arrays.asList(NAME_REQUEST_SCENE, SETTINGS_SCENE, WAITING_ROOM_SCENE, BUILD_SHIP_SCENE, ADD_ALIEN_SCENE, FIX_SHIP_SCENE, SHIP_WRECK_SCENE));
        try{
            for(String fxmlFile : FxmlFiles){
                FXMLLoader loader = new FXMLLoader(GUI.class.getResource(fxmlFile));
                if(fxmlFile.equals(NAME_REQUEST_SCENE)) {
                    this.sceneMap.put(fxmlFile, new Scene(loader.load(), 800, 600));
                } else {
                    this.sceneMap.put(fxmlFile, new Scene(loader.load()));
                }
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

    public GuiController getCurrentController() {
        for (Map.Entry<String, Scene> entry : sceneMap.entrySet()) {
            if (entry.getValue() == currentScene) {
                return controllerMap.get(entry.getKey());
            }
        }
        return null;
    }


    public Stage getStage() {
        return this.stage;
    }
}