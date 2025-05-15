package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.PickTileWithDeckException;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class PickComponentTileMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try {
                Player player = controller.getGame().getPlayerByName(playerName);
                player.getState().pickComponentTile(player);
            } catch (PickTileWithDeckException e) {
                System.out.println("Error handling the message: " + e.getMessage());
            }
        }
    }
}
