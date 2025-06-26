package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;

public class AbandonMessage extends Message {

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        controller.getGame().getPlayerByName(playerName).abandon(controller.getGame());
    }
}
