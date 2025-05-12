package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.PickTileWithDeckException;

public class PickComponentTileMessage extends Message{
    @Override
    public void handle(GameController controller, String playerName){
        if(checkClient()) {
            try {
                controller.getGame().getPlayerByName(playerName).getState().pickComponentTile(controller.getGame().getPlayerByName(playerName));
            } catch (PickTileWithDeckException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}
