package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;

public class WinEnemyState extends PlayerState implements Serializable {
    public WinEnemyState(Game game){
        super(game);
    }

    @Override
    public void acceptReward(boolean accept, Player player){
        getGame().getCurrentCard().setAccept(accept);
        getGame().getCurrentCard().playCard(getGame());
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(getGame());
        acceptReward(false, null);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer(disconnectingPlayer);
        acceptReward(false, null);    // se abbandona consideriamo come se rifiutasse
    }
}
