package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class RemoveAstronautsMessage extends Message {
    private Points point;

    public RemoveAstronautsMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().removeAstronauts(point, player);
            } catch (EnoughAstronautsRemovedException | NotCabinException | EndStateException | WaitingStateException |
                     AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
