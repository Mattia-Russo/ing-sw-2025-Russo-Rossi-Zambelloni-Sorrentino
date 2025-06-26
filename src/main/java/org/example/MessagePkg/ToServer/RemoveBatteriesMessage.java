package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.rmi.RemoteException;

public class RemoveBatteriesMessage extends Message {
    private Points point;

    public RemoveBatteriesMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().removeBatteries(point, player);
            } catch (EnoughBatteriesRemovedException | NotBatteryStorageException |
                     RemoveBatteriesBeforeGoodsException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
