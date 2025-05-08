package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;

public class EndAddAlienMessage extends Message{

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            controller.getGame().getPlayerByName(playerName).getState().endAlienState();
        }
    }
}
