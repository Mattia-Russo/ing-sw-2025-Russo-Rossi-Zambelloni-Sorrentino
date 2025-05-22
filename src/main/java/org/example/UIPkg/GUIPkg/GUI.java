package org.example.UIPkg.GUIPkg;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.example.ServerPkg.Model.ForView.GameView;
import org.example.UIPkg.Client;
import org.example.UIPkg.UI;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.CountDownLatch;

public class GUI implements UI {
    // Usa CountDownLatch invece di un flag booleano per una sincronizzazione più robusta
    private final CountDownLatch guiReadyLatch = new CountDownLatch(1);
    private GUIMain guiMain;
    private final Client client;
    private BlockingQueue<GameView> gameUpdatesQueue;

    private NameRequestSceneController nameRequestSceneController;
    private SettingsSceneController settingsSceneController;
    private BuildShipSceneController buildShipSceneController;
    private PlayCardSceneController playCardSceneController;
    private EndGameSceneController endGameSceneController;

    public GUI(Client client){
        this.client = client;
        this.gameUpdatesQueue = new LinkedBlockingQueue<>();

        // Avvia JavaFX in un thread separato
        Thread guiThread = new Thread(() -> {
            GUIMain.startGui(this);
        });
        guiThread.setDaemon(false);
        guiThread.start();

        // Aspetta che la GUI sia pronta
        waitForGuiReady();
    }

    private void waitForGuiReady() {
        try {
            guiReadyLatch.await(); // Aspetta indefinitamente che la GUI sia pronta
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("GUI initialization interrupted", e);
        }
    }

    // Questo metodo viene chiamato da GUIMain quando la GUI è pronta
    public void notifyGuiReady() {
        guiReadyLatch.countDown(); // Rilascia il latch
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
}