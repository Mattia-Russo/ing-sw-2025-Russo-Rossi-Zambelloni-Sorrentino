package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;

public class SetPositionMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().setPosition(controller.getGame().getPlayerByName(playerName));
        }
    }
}
