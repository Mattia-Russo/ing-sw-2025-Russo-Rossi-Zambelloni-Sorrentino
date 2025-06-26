package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class NotifyRemoveBestGoodsMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().goToRemoveBestGoodsScene();
    }
}
