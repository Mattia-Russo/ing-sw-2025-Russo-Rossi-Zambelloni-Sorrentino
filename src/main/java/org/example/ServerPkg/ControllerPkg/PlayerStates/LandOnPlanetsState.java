package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

public class LandOnPlanetsState extends PlayerState {
    private final Game game;

    public LandOnPlanetsState(Game game){
        this.game = game;
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet){
        if(landed){
            if(game.getCurrentCard().getPlanetsVisited()[numPlanet]){
               throw new PlanetAlreadyVisitedException("Planet " + numPlanet + " already visited, choose another one");
            } else {
                game.getCurrentCard().playCard(game, numPlanet);
            }
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

    @Override
<<<<<<< HEAD
    public void AbandonGame(Player player){
        player.abandon();
=======
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
>>>>>>> 9ee630db2862d761e24adf75fa54ed47806b11dd
        landOnPlanet(false, 0);
    }

}
