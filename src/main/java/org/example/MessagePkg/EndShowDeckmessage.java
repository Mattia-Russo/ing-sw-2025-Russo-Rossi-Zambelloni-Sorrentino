package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class EndShowDeckmessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().endShowDeck(controller.getGame().getPlayerByName(playerName));
        }
    }
}
