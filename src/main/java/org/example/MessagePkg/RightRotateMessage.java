package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class RightRotateMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()){
            controller.getGame().getPlayerByName(playerName).getState().rightRotateTile(controller.getGame().getPlayerByName(playerName));
        }
    }
}
