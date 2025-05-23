package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;

public class AlreadyCreatingLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        getClient().getUserInterface().showLobbyExistsMessage();
    }
}
