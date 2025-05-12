package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;

import java.rmi.RemoteException;

public class AcceptRewardMessage extends Message {
    private boolean bool;

    public AcceptRewardMessage(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().acceptReward(bool);
            } catch (EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
