package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

public class RemoveAstronautsMessage extends Message {
    private Points point;

    public void RemoveAstronautsMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().removeAstronauts(point);
            } catch (EnoughAstronautsRemovedException | NotCabinException | EndStateException | WaitingStateException |
                     AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
