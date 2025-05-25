package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class LobbyCreatedMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        super.getClient().getUserInterface().onLobbyCreated(playerName);
    }
}
