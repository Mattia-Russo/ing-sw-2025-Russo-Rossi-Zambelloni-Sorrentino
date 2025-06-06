package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class LandOnPlanetMessage extends Message {
    private final boolean bool;
    private final int numPlanet;

    public LandOnPlanetMessage(boolean bool, int numPlanet) {
        this.bool = bool;
        this.numPlanet = numPlanet;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            try{
                Player player= controller.getGame().getPlayerByName(playerName);
                player.getState().landOnPlanet(bool, numPlanet, player);
            } catch (PlanetAlreadyVisitedException | EndStateException | WaitingStateException |
                     AbandonedStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
