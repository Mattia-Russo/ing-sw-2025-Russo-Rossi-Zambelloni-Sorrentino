package org.example.Server.Controller.States;

import org.example.Server.Model.Game;

public class WinEnemyState extends PlayerState{
    private Game game;

    public WinEnemyState(Game game){
        this.game = game;
    }

    public void acceptReward(boolean accept){
        game.getCurrentCard().setAccept(accept);
        game.getCurrentCard().playCard(game);
    }
}
