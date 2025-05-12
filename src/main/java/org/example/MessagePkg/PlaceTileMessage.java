package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.Points;

public class PlaceTileMessage extends Message{
    private Points point;

    public PlaceTileMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            controller.getGame().getPlayerByName(playerName).getState().placeTile(controller.getGame().getPlayerByName(playerName), point);
        }
    }
}
