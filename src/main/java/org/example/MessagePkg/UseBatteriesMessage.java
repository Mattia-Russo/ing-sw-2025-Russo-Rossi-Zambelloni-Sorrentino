package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class UseBatteriesMessage extends Message {
    private ArrayList<Points> batteries;

    public UseBatteriesMessage(ArrayList<Points> batteries){
        this.batteries=batteries;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
            if(checkClient()){
                try{
                    controller.getGame().getPlayerByName(playerName).getState().useBatteries(batteries);
                } catch (AlreadyBatteryException | EndStateException | WaitingStateException | AbandonedStateException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
    }

}
