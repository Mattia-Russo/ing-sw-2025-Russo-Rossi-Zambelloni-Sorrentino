package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.MessagePkg.NotifyWinEnemyMessage;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.rmi.RemoteException;

public class WinEnemyState extends PlayerState implements Serializable {
    public WinEnemyState(Game game,Player player) throws RemoteException {
        super(game, player);
        game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName()).goToWinEnemy();
    }

    @Override
    public void acceptReward(boolean accept, Player player) throws RemoteException {
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame(), player));
        }
        getGame().getCurrentCard().setAccept(accept);
        getGame().getCurrentCard().playCard(getGame());
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        player.abandon(getGame());
        acceptReward(false, player);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        acceptReward(false, disconnectingPlayer);    // se abbandona consideriamo come se rifiutasse
    }
}
