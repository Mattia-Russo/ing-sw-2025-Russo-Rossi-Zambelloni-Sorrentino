package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

public class LandOnPlanetsState extends PlayerState {
    private final Game game;

    public LandOnPlanetsState(Game game){
        this.game = game;
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet, Player player){
        if(landed){
            if(game.getCurrentCard().getPlanetsVisited()[numPlanet]){
               new GameView(game, new PlanetAlreadyVisitedException("Planet " + numPlanet + " already visited, choose another one " + player.getName()));
            } else {
                game.getCurrentCard().playCard(game, numPlanet);
            }
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(game);
        landOnPlanet(false, 0, null);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        landOnPlanet(false, 0, null);
    }

}
