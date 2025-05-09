package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

public class WinEnemyState extends PlayerState{
    private final Game game;

    public WinEnemyState(Game game){
        this.game = game;
    }

    @Override
    public void acceptReward(boolean accept){
        game.getCurrentCard().setAccept(accept);
        game.getCurrentCard().playCard(game);
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon();
        acceptReward(false);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        acceptReward(false);    // se abbandona consideriamo come se rifiutasse
    }
}
