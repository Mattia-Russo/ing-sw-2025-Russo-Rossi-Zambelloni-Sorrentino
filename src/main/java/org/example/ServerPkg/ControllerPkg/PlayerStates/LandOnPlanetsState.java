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
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame()));
        }

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
        landOnPlanet(false, 0, player);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        landOnPlanet(false, 0, disconnectingPlayer);
    }

}
