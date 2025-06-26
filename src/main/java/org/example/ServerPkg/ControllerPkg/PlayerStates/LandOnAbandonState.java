package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;

public class LandOnAbandonState extends PlayerState implements Serializable {
    public LandOnAbandonState(Game game){
        super(game, null);
    }

    // landed true vuol dire che è atterrato
    @Override
    public void landOnAbandon(boolean landed, Player player){
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame()));
        }
        if(landed){
            getGame().getCurrentCard().playCard(getGame());
        } else {
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(getGame());
        landOnAbandon(false, player);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        landOnAbandon(false, disconnectingPlayer);
    }
}
