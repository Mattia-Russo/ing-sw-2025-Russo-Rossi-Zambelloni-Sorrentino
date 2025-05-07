package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;

public class LandOnAbandonMessage extends Message {
    private boolean bool;

    public LandOnAbandonMessage(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().landOnAbandon(bool);
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
