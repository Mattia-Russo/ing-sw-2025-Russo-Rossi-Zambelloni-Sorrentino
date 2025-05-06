package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;

public class EndRemoveAstronautsMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().endRemoveAstronauts();
            } catch (NotEnoughAstronautsRemovedException | NotCabinException | EndStateException |
                     WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
