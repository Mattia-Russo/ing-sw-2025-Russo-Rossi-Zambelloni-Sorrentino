package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;

public class LandOnPlanetsState extends PlayerState implements Serializable {
    public LandOnPlanetsState(Game game){
        super(game);
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet, Player player){
        if(landed){
            if(getGame().getCurrentCard().getPlanetsVisited()[numPlanet]){
               new GameView(getGame(), new PlanetAlreadyVisitedException("Planet " + numPlanet + " already visited, choose another one " + player.getName()));
            } else {
                getGame().getCurrentCard().playCard(getGame(), numPlanet);
            }
        } else {
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(getGame());
        landOnPlanet(false, 0, null);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer(disconnectingPlayer);
        landOnPlanet(false, 0, null);
    }

}
