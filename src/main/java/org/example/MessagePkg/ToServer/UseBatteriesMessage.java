package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UseBatteriesMessage extends Message {
    private final ArrayList<Points> batteries;

    public UseBatteriesMessage(ArrayList<Points> batteries){
        this.batteries=batteries;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
            if(checkClient()){
                try{
                    Player player= controller.getGame().getPlayerByName(playerName);
                    player.getState().useBatteries(batteries, player);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
    }

}
