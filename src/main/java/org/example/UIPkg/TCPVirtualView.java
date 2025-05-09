package org.example.UIPkg;

import org.example.ServerPkg.Model.ForView.GameView;

import java.io.ObjectOutputStream;

public class TCPVirtualView implements GameUpdater {
    private UI userInterface;
    private final ObjectOutputStream out;

    public TCPVirtualView(UI userInterface, ObjectOutputStream out){
        this.userInterface = userInterface;
        this.out = out;
    }

    @Override
    public void updateGame(GameView game) {
        try {
            out.writeObject(game); // Invio aggiornamenti tramite TCP
            out.flush();
        } catch (Exception e) {
            System.err.println("Error updating game with TCP connection: " + e.getMessage());
        }

    }
}
// listener del client