package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;

import java.rmi.RemoteException;

public class EndActivateShieldsMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().endActivateShields();
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

    }
}
