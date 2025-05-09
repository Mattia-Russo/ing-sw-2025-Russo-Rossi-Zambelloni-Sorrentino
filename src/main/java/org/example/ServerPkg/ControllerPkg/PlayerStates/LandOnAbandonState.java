package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;
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
    public void AbandonGame(Player player){
        player.abandon();
        landOnAbandon(false);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        landOnAbandon(false);
    }
}
