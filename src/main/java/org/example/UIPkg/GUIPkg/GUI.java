package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.MessagePkg.NotifyClientMessage;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.Client;
import org.example.UIPkg.UI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class GUI implements UI {

    private final CountDownLatch guiReadyLatch = new CountDownLatch(1);
    private GUIMain guiMain;
    private final Client client;
    private BlockingQueue<GameView> gameUpdatesQueue;

    private NameRequestSceneController nameRequestSceneController;
    private SettingsSceneController settingsSceneController;
    private WaitingRoomSceneController waitingRoomSceneController; // AGGIUNTO
    private BuildShipSceneController buildShipSceneController;
    private PlayCardSceneController playCardSceneController;
    private EndGameSceneController endGameSceneController;

    public GUI(Client client){
        this.client = client;
        this.gameUpdatesQueue = new LinkedBlockingQueue<>();

        Thread guiThread = new Thread(() -> {
            GUIMain.startGui(this);
        });
        guiThread.setDaemon(false);
        guiThread.start();

        waitForGuiReady();
    }

    private void waitForGuiReady() {
        try {
            guiReadyLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("GUI initialization interrupted", e);
        }
    }

    public void notifyGuiReady() {
        guiReadyLatch.countDown();
    }

    public void setGuiMain(GUIMain guiMain) {
        this.guiMain = guiMain;
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

    public void goToFirstScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL location = getClass().getResource("/org.example/FxmlPkg/nameRequestScene.fxml");
        loader.setLocation(location);

        Parent root = loader.load();

        nameRequestSceneController = loader.getController();
        nameRequestSceneController.setGUI(this);

        Scene scene = new Scene(root, 800, 600);

        scene.setUserData(nameRequestSceneController);
        guiMain.sceneControllerMap.put(scene, nameRequestSceneController);

        changeScene(scene);
    }

    public void goToSettingsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL location = getClass().getResource("/org.example/FxmlPkg/settingsScene.fxml");
        loader.setLocation(location);

        Parent root = loader.load();

        settingsSceneController = loader.getController();
        settingsSceneController.setGUI(this);

        Scene scene = new Scene(root, 800, 600);
        scene.setUserData(settingsSceneController);
        guiMain.sceneControllerMap.put(scene, settingsSceneController);

        changeScene(scene);
    }

    public void goToWaitingRoomScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL location = getClass().getResource("/org.example/FxmlPkg/waitingRoomScene.fxml");
        loader.setLocation(location);

        Parent root = loader.load();

        waitingRoomSceneController = loader.getController();
        waitingRoomSceneController.setGUI(this);

        waitingRoomSceneController.setMaxPlayers(settingsSceneController.getNumberOfPlayers());
        waitingRoomSceneController.setShipboardLevel(settingsSceneController.getShipboardLevel());
        waitingRoomSceneController.setGameMode(settingsSceneController.getGameMode());

        Scene scene = new Scene(root, 800, 600);
        scene.setUserData(waitingRoomSceneController);
        guiMain.sceneControllerMap.put(scene, waitingRoomSceneController);

        changeScene(scene);
    }

    private void changeScene(Scene scene) {
        Platform.runLater(() -> {
            Stage stage = guiMain.getPrimaryStage();
            stage.setTitle("Galaxy Trucker");
            stage.setScene(scene);
            stage.show();
        });
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void printNameInvalid() {
        if (nameRequestSceneController != null) {
            Platform.runLater(() -> nameRequestSceneController.printNameInvalid());
        }
    }

    @Override
    public void askName(){
        // Assicurati che il metodo venga eseguito sul JavaFX Application Thread
        Platform.runLater(() -> {
            if (nameRequestSceneController == null) {
                try {
                    goToFirstScene();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }

            if (nameRequestSceneController != null) {
                nameRequestSceneController.askName();
            } else {
                System.err.println("Errore: nameRequestSceneController è ancora null dopo il tentativo di inizializzazione");
            }
        });
    }

    @Override
    public void readName(){
        //does nothing, waits for button click
    }

    @Override
    public void onNameAccepted() {
        if (nameRequestSceneController != null) {
            nameRequestSceneController.onNameAccepted();
        }
    }

    @Override
    public void onLobbyCreated(String name) {
        if (settingsSceneController != null) {
            settingsSceneController.onLobbyCreated();
        }
        Platform.runLater(() -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1500);
                    Platform.runLater(() -> {
                        try {
                            goToWaitingRoomScene();
                            if (waitingRoomSceneController != null) {
                                waitingRoomSceneController.setLobbyCreator(true);
                                waitingRoomSceneController.updatePlayersList(new ArrayList<>(List.of(name)));
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
    public void onLobbyJoined(String name) {
        Platform.runLater(() -> {
            try {
                goToWaitingRoomScene();
                if (waitingRoomSceneController != null) {
                    waitingRoomSceneController.setLobbyCreator(false);
                    List<String> newPlayerList = new ArrayList<>(waitingRoomSceneController.getPlayersList());
                    newPlayerList.add(name);
                    waitingRoomSceneController.updatePlayersList(newPlayerList);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void manageNotification(NotifyClientMessage notifyClientMessage){
        new Thread(() -> {
            try {
                //gestire stampa sulla gui del messaggio di errore
                Thread.sleep(5000);
                //rimuovere il messaggio

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    public void onGameStarted() {
        if (waitingRoomSceneController != null) {
            waitingRoomSceneController.onGameStarted();
        }
    }

    public void printMessage(String message){}
}