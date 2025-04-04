package org.example.Server.Controller.States;

import org.example.Server.Model.Game;

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
}
