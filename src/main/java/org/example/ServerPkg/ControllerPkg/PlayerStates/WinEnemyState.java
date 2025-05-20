package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;

public class WinEnemyState extends PlayerState implements Serializable {
    private final Game game;

    public WinEnemyState(Game game){
        this.game = game;
    }

    @Override
    public void acceptReward(boolean accept, Player player){
        game.getCurrentCard().setAccept(accept);
        game.getCurrentCard().playCard(game);
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(game);
        acceptReward(false, null);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer(disconnectingPlayer);
        acceptReward(false, null);    // se abbandona consideriamo come se rifiutasse
    }
}
