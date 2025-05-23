package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class SetUpLobbyMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        controller.setGameCreating();
    }
}
