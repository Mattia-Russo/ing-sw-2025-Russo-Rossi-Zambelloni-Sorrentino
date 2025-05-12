package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;

public class ShowDeckMessage extends Message{
    int num;

    public ShowDeckMessage(int num){
        this.num=num;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().showDeck(controller.getGame().getPlayerByName(playerName), num);
        }
    }
}
