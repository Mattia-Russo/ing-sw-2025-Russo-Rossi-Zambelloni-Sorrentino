package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;

import java.rmi.RemoteException;

public class EndAddAlienMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            controller.getGame().getPlayerByName(playerName).getState().endAlienState();
        }
    }
}
