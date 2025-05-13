package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class PickDiscoveredComponentMessage extends Message{
    private int index;

    public PickDiscoveredComponentMessage(int index){
        this.index=index;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()) {
            try {
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().pickDiscoveredComponent(player, index);
            } catch (InvalidMethodCallException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
