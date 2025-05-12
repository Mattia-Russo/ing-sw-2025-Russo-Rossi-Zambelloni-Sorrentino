package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class RemoveTileMessage extends Message{
    private Points point;

    public RemoveTileMessage(Points point){
        this.point=point;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().removeTile(point, controller.getGame().getPlayerByName(playerName));
        }
    }
}
