package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

public class AcceptCreateLobbyMessage extends Message {

    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().onCreateLobbyAccepted();
    }
}
