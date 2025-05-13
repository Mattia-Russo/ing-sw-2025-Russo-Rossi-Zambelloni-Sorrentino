package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class ActivateShieldsMessage extends Message {
    private ArrayList<Points> shields;

    public ActivateShieldsMessage(ArrayList<Points> shields){
        this.shields=shields;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().activateShields(shields, player);
            } catch (AlreadyShieldException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (java.rmi.RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
