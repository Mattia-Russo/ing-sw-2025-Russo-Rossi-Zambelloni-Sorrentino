package org.example.Server.Controller.States;

import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

public class LandOnAbandonState extends PlayerState {
    private final Game game;

    public LandOnAbandonState(Game game){
        this.game = game;
    }

    // landed true vuol dire che è atterrato
    @Override
    public void landOnAbandon(boolean landed){
        if(landed){
            game.getCurrentCard().playCard(game);
        } else {
            game.getCurrentCard().setCardState(game);
        }

    }
}
