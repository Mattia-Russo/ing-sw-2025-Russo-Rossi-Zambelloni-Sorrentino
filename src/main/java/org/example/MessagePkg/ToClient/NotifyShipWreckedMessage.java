package org.example.MessagePkg.ToClient;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

public class NotifyShipWreckedMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().goToShipWreckedScene();
    }
}
