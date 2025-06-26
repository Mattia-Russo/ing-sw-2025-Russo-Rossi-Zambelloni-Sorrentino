package org.example.MessagePkg.ToClient;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

public class NotifyAddAlienMessage extends Message {
    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().goToAddAlienScene();
    }
}
