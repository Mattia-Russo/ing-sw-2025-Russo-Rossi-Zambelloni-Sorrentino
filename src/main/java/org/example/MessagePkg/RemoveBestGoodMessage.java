package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class RemoveBestGoodMessage extends Message {
    private Points point;
    private int numGood;

    public RemoveBestGoodMessage(Points point, int numGood) {
        this.point = point;
        this.numGood = numGood;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().removeBestGood(point, numGood);
            } catch (EnoughBestGoodsRemovedException | NotStorageException | NotCabinException | EndStateException |
                     WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
