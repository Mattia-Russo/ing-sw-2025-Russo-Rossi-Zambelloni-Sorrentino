package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;

public class LandOnAbandonState extends PlayerState implements Serializable {
    private final Game game;

    public LandOnAbandonState(Game game){
        this.game = game;
    }

    // landed true vuol dire che è atterrato
    @Override
    public void landOnAbandon(boolean landed, Player player){
        if(landed){
            game.getCurrentCard().playCard(game);
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(game);
        landOnAbandon(false, null);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer(disconnectingPlayer);
        landOnAbandon(false, null);
    }
}
