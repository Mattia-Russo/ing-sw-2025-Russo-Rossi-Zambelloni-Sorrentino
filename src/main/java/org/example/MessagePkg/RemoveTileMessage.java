package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Player;
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
            Player player= controller.getGame().getPlayerByName(playerName);
            player.getState().removeTile(point, player);
        }
    }
}
