package org.example.UIPkg;

import org.example.ServerPkg.ConnectionsPkg.RMIPkg.RMIClientInterface;
import org.example.ServerPkg.Model.ForView.GameView;

public class RMIVirtualView implements GameUpdater {
    private final RMIClientInterface client;

    public RMIVirtualView(RMIClientInterface client){
        this.client = client;
    }

    @Override
    public void updateGame(GameView game) {
        try {
            client.addGameUpdate(game);
        } catch (Exception e) {
            System.err.println("Errore nell'aggiornamento RMI: " + e.getMessage());
        }

    }
}
