package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class DiscardComponentMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName) {
        if (checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().discardComponent(controller.getGame().getPlayerByName(playerName));
        }
    }

}
