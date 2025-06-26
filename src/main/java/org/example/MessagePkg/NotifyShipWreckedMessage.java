package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class NotifyShipWreckedMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().goToShipWreckedScene();
    }
}
