package org.example.UIPkg;

import org.example.ServerPkg.Model.ForView.GameView;

public class RMIVirtualView implements GameUpdater {
    private UI userInterface;

    RMIVirtualView(UI userInterface){
        this.userInterface = userInterface;
    }

    @Override
    public void updateGame(GameView game) {
        try {
            userInterface.addGameUpdate(game); // Chiamata remota
        } catch (Exception e) {
            System.err.println("Errore nell'aggiornamento RMI: " + e.getMessage());
        }

    }
}
