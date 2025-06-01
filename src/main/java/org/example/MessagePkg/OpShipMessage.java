package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class OpShipMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        controller.getGame().getPlayerByName(playerName).opShip();
    }
}
