package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.*;

public class PickDiscoveredComponentMessage extends Message{
    private int index;

    public PickDiscoveredComponentMessage(int index){
        this.index=index;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()) {
            try {
                controller.getGame().getPlayerByName(playerName).getState().pickDiscoveredComponent(controller.getGame().getPlayerByName(playerName), index);
            } catch (InvalidMethodCallException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
