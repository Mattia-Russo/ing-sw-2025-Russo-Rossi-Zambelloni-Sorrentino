package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.rmi.RemoteException;

public class LandOnAbandonState extends PlayerState implements Serializable {
    public LandOnAbandonState(Game game, Player player) throws RemoteException {
        super(game, player);
        game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName()).goToLandOnAbandon();
    }

    // landed true vuol dire che è atterrato
    @Override
    public void landOnAbandon(boolean landed, Player player) throws RemoteException {
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame(), player));
        }
        if(landed){
            getGame().getCurrentCard().playCard(getGame());
        } else {
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        player.abandon(getGame());
        landOnAbandon(false, player);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        landOnAbandon(false, disconnectingPlayer);
    }
}
