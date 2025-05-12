package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;

import java.rmi.RemoteException;

public class EndRemoveAstronautsMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
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
