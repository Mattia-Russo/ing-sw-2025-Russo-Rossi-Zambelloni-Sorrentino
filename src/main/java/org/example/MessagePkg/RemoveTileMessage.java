package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Points;

public class RemoveTileMessage extends Message{
    private Points point;

    public RemoveTileMessage(Points point){
        this.point=point;
    }

    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()) {
            controller.getGame().getPlayerByName(playerName).getState().removeTile(point, controller.getGame().getPlayerByName(playerName));
        }
    }
}
