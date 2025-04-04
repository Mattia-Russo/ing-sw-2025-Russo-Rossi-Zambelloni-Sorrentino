package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.PlanetAlreadyVisitedException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

public class LandOnPlanetsState extends PlayerState {
    private final Game game;

    public LandOnPlanetsState(Game game){
        this.game = game;
    }

    @Override
    public void landOnPlanet(boolean landed, int numPlanet){
        if(landed){
            if(game.getCurrentCard().isPlanetsVisited()[numPlanet]){
               throw new PlanetAlreadyVisitedException("Planet " + numPlanet + " already visited, choose another one");
            } else {
                game.getCurrentCard().playCard(game, numPlanet);
            }
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

}
