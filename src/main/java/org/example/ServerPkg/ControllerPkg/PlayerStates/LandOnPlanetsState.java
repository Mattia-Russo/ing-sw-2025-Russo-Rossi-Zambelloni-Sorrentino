package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.ServerPkg.Model.Game;

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

}
