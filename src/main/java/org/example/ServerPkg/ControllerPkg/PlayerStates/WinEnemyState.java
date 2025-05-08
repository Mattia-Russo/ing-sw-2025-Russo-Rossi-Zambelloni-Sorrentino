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
    public synchronized void disconnect(Player disconnectingPlayer){
        disconnectingPlayer.abandon();
        game.getCurrentCard().playCard(disconnectingPlayer, game);
    }
}
