package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class AddPurpleAlienMessage extends Message{
    private Points point;

    public AddPurpleAlienMessage(Points point){
        this.point=point;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            controller.getGame().getPlayerByName(playerName).getState().addPurpleAlien(point);
        }
    }
}
