package org.example.Server.Controller.States;

import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

public class ShipWreckedState extends PlayerState {
    private final Game game;

    public ShipWreckedState(Game game) {
        this.game = game;
    }

    public void chooseWrecked(Player player, Points point){
        player.getPlayerShipBoard().removeWreck(point.getX(), point.getY());
    }

    public void endWreckedState(){
        if(game.getCurrentCard()!=null){
            game.getCurrentCard().setCardState(game);
        } else {
            game.Turn();
        }
    }


}
