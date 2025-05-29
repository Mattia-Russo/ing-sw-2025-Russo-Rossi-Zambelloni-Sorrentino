package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.ControllerPkg.LobbyState;

import java.rmi.RemoteException;

public class SetUpLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(controller.getLobbyState().equals(LobbyState.GAME_CREATION)){
            getServer().notifyClient(getHandler().getPlayerName(), "Somebody else is setting up a lobby, wait it to be created");
        } else if(controller.getLobbyState().equals((LobbyState.GAME_READY))) {
            getServer().notifyClient(getHandler().getPlayerName(), "There's already a lobby ready, join it!");
        } else {
            controller.setGameCreating();
            getServer().acceptCreateLobby(getHandler().getPlayerName());
        }
    }
}
