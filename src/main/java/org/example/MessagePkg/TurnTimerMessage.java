package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class TurnTimerMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().turnTimer(controller.getGame().getPlayerByName(playerName));
        }
    }
}
