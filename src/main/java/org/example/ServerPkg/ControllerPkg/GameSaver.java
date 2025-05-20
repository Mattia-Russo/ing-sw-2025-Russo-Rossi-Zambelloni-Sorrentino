package org.example.ServerPkg.ControllerPkg;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class GameSaver implements Runnable {
    private final GameController gameController;
    private String lastSave = "";

    public GameSaver(GameController gameController) {
        this.gameController = gameController;
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this, 0, 10, java.util.concurrent.TimeUnit.SECONDS);
    }

    public void run() {
        String toDelete = lastSave;
        lastSave = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        gameController.saveGame(lastSave);
        try{
            Files.delete(Paths.get(toDelete));
        } catch (IOException ignored) {
            System.out.println("no file to delete");
        }
    }

}
