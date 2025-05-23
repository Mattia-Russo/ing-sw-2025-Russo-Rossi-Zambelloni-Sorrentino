package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class JoinedLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().onLobbyJoined();
    }
}
