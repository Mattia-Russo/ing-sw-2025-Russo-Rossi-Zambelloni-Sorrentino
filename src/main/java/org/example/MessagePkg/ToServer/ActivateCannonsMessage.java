package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.AlreadyCannonException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ActivateCannonsMessage extends Message {
    private ArrayList<Points> cannons;

    public ActivateCannonsMessage(ArrayList<Points> cannons){
        this.cannons=cannons;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try {
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().activateCannons(cannons, player);
            } catch (AlreadyCannonException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

}
