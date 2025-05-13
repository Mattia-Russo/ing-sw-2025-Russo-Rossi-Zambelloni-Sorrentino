package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class AddBrownAlienMessage  extends Message{
    private Points point;

    public AddBrownAlienMessage(Points point){
        this.point=point;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            Player player= controller.getGame().getPlayerByName(playerName);
            player.getState().addBrownAlien(point, player);
        }
    }
}
