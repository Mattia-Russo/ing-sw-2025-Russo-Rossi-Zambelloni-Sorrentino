package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class EndAddAlienMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            Player player= controller.getGame().getPlayerByName(playerName);
            player.getState().endAlienState(player);
        }
    }
}
