package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

public class RemoveBatteriesMessage extends Message {
    private Points point;

    public RemoveBatteriesMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().removeBatteries(point);
            } catch (EnoughBatteriesRemovedException | NotBatteryStorageException |
                     RemoveBatteriesBeforeGoodsException | NotCabinException | EndStateException | WaitingStateException | AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
