package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class LeftRotateMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()){
            controller.getGame().getPlayerByName(playerName).getState().leftRotateTile(controller.getGame().getPlayerByName(playerName));
        }
    }
}
