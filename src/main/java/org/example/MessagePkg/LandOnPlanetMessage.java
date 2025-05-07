package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;

public class LandOnPlanetMessage extends Message {
    private boolean bool;
    private int numPlanet;

    public LandOnPlanetMessage(boolean bool, int numPlanet) {
        this.bool = bool;
        this.numPlanet = numPlanet;
    }

    @Override
    public void handle(GameController controller, String playerName) {
        if(checkClient()){
            try{
                controller.getGame().getPlayerByName(playerName).getState().landOnPlanet(bool, numPlanet);
            } catch (PlanetAlreadyVisitedException | EndStateException | WaitingStateException |
                     AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
