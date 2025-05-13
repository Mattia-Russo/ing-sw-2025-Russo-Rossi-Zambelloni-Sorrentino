package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class EndActivateCannonsMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().endActivateCannons(player);
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
