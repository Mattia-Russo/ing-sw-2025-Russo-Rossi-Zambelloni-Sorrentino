package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class ChooseWreckedMessage extends Message {
    private final Points point;

    public ChooseWreckedMessage(Points point){
        this.point=point;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            Player player= controller.getGame().getPlayerByName(playerName);
            player.getState().chooseWrecked(point, player);
        }
    }
}
