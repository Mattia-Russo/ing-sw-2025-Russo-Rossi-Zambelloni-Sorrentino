package org.example.UIPkg.GUIPkg;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.util.Duration;
import org.example.MessagePkg.NotifyClientMessage;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.ForView.GameViewCache;
import org.example.UIPkg.Client;
import org.example.UIPkg.UI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class GUI extends UI {

    private final BlockingQueue<GameView> gameUpdatesQueue;
    private int numPlayers;
    private int shipboardLevel;
    private int gameMode;
    private  List<String> playersList;
    private GameViewCache gameCache;

    public GUI(Client client){
        super(client);
        this.gameUpdatesQueue = new LinkedBlockingQueue<>();
        this.playersList = new ArrayList<>();
        this.gameCache=null;
    }

    public int getGameMode(){
        return gameMode;
    }

    public GameViewCache getGameCache() {
        return gameCache;
    }

    public int getShipBoardLevel(){
        return this.shipboardLevel;
    }

    @Override
    public void startGui() {
        GUIMain.startGui(this);
    }

    public void startUpdateThread() {
        Thread UpdateThread = new Thread(() -> {
            try {
                while (true) {
                    if(!gameUpdatesQueue.isEmpty()) {
                        if(gameCache == null){
                            gameCache = new GameViewCache(getClient().getPlayerName());
                        }
                        GameView game = gameUpdatesQueue.poll();
                        assert game != null;
                        GuiController controller =  GUIMain.getGuiMain().getCurrentController();
                        controller.setUp(game);

                        if (game.getException() == null &&
                                game.getPlayers() != null &&
                                !game.getPlayers().isEmpty()) {
                            controller.updatePlayerShipboardButtons(game);
                        }
                    }
                }
            }catch (Exception e) {
                System.err.println("Error sending connection update to server: " + e.getMessage());
                e.printStackTrace();
            }
        });
        UpdateThread.setDaemon(false);
        UpdateThread.start();
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

    public void preserveWindowSize() {
        if(GUIMain.getGuiMain() != null && GUIMain.getGuiMain().getStage() != null){
            Stage stage = GUIMain.getGuiMain().getStage();
            double currentWidth = stage.getWidth();
            double currentHeight = stage.getHeight();
            Platform.runLater(() -> {
                stage.setWidth(currentWidth);
                stage.setHeight(currentHeight);
            });
        }
    }

    public void goToSettingsScene() throws IOException {
        preserveWindowSize();
        changeScene(GUIMain.SETTINGS_SCENE);
    }

    public void goToWaitingRoomScene() throws IOException {
        preserveWindowSize();
        GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.WAITING_ROOM_SCENE);

        controller.setMaxPlayers(numPlayers);
        controller.setShipboardLevel(shipboardLevel);
        controller.setGameMode(gameMode);
        controller.updatePlayersList(playersList);

        preserveWindowSize();
        changeScene(GUIMain.WAITING_ROOM_SCENE);
    }

    private void changeScene(String scene) {
        if(GUIMain.getGuiMain() != null){
            GUIMain.getGuiMain().changeScene(scene);
        }
    }

    @Override
    public void printNameInvalid() {
        GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.NAME_REQUEST_SCENE);
        if (controller != null) {
            Platform.runLater(controller::printNameInvalid);
        }
    }

    @Override
    public void onNameAccepted() {
        GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.NAME_REQUEST_SCENE);
        if (controller != null) {
            Platform.runLater(controller::onNameAccepted);
        }
    }

    @Override
    public void onLobbyCreated(String name, int numPlayers, int shipboardLevel, int gameMode) {
        GuiController settingsSceneController = GUIMain.getGuiMain().getControllerMap().get(GUIMain.SETTINGS_SCENE);
        if (settingsSceneController != null) {
            settingsSceneController.onLobbyCreated();
        }
        Platform.runLater(() -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        try {
                            this.numPlayers = numPlayers;
                            this.shipboardLevel = shipboardLevel;
                            this.gameMode = gameMode;
                            this.playersList.add(name);
                            goToWaitingRoomScene();
                            GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.WAITING_ROOM_SCENE);
                            if (controller != null) {
                                controller.setLobbyCreator(true);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        });
    }

    @Override
    public void onLobbyJoined(List<String> alreadyLoggedNames, int numPlayers, int shipboardLevel, int gameMode) {
        Platform.runLater(() -> {
            try {
                this.numPlayers = numPlayers;
                this.shipboardLevel = shipboardLevel;
                this.gameMode = gameMode;
                this.playersList = alreadyLoggedNames;
                goToWaitingRoomScene();
                GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.WAITING_ROOM_SCENE);
                if (controller != null) {
                    controller.setLobbyCreator(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onCreateLobbyAccepted(){
        GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.NAME_REQUEST_SCENE);
        if (controller != null) {
            controller.onCreateLobbyAccepted();
        }
    }

    @Override
    public void manageNotification(NotifyClientMessage notifyClientMessage) {
        Platform.runLater(() -> {
            Stage stage = GUIMain.getGuiMain().getStage();
            Scene currentScene = stage.getScene();

            if (currentScene != null) {
                showNotificationOverlay(currentScene, notifyClientMessage.getMessage());
            }
        });
    }

    private void showNotificationOverlay(Scene scene, String message) {
        Parent originalRoot = scene.getRoot();

        Label notificationLabel = new Label(message);
        notificationLabel.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 20px; " +
                        "-fx-effect: dropshadow(gaussian, black, 10, 0.8, 2, 2);"
        );

        StackPane overlayRoot = new StackPane();
        overlayRoot.getChildren().addAll(originalRoot, notificationLabel);
        overlayRoot.setStyle("-fx-background-color: rgba(0, 0, 0, 0.2);");
        StackPane.setAlignment(notificationLabel, javafx.geometry.Pos.CENTER);

        scene.setRoot(overlayRoot);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(3),e -> {
            overlayRoot.getChildren().remove(originalRoot);
            scene.setRoot(originalRoot);
        }
        ));
        timeline.play();
    }

    public void onGameStarted() {
        GuiController waitingRoomSceneController = GUIMain.getGuiMain().getControllerMap().get(GUIMain.WAITING_ROOM_SCENE);
        if (waitingRoomSceneController != null) {
            waitingRoomSceneController.onGameStarted();
        }
        Platform.runLater(() -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    Platform.runLater(this::goToBuildShipScene);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        });
    }

    @Override
    public void onUpdatePlayerList(List<String> updatedList){
        this.playersList = updatedList;
        GuiController controller = GUIMain.getGuiMain().getControllerMap().get(GUIMain.WAITING_ROOM_SCENE);
        if (controller != null) {
            controller.updatePlayersList(updatedList);
        }
    }

    private void goToBuildShipScene(){
        preserveWindowSize();
        changeScene(GUIMain.BUILD_SHIP_SCENE);
    }

    public void goToAddAlienScene(){
        preserveWindowSize();
        changeScene(GUIMain.ADD_ALIEN_SCENE);
    }

    public void goToReadyForCardsScene(){
        preserveWindowSize();
        changeScene(GUIMain.READY_FOR_CARDS_SCENE);
    }
}