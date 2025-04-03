package org.example.Server.Controller.States;

import org.example.Server.Model.Game;
import org.example.Server.Model.Player;

public class LandOnAbandonState extends PlayerState {
    private Game game;

    public LandOnAbandonState(Game game){
        this.game = game;
    }

    // player è il riferimento al giocatore che ha fatto la chiamata, landed true vuol dire che è atterrato
    public void landOnAbandon(Player p, boolean landed){
        if(landed){
            game.getCurrentCard().playCard(game);
        } else {
            game.getCurrentCard().setCardState(game);
        }

    }
}
