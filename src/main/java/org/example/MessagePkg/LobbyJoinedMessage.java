package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class LobbyJoinedMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        super.getClient().getUserInterface().onLobbyJoined();
    }
}
