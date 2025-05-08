package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

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

    @Override
    public synchronized void disconnect(Player disconnectingPlayer){
        disconnectingPlayer.abandon();
        game.getCurrentCard().setCardState(game);
    }
}
