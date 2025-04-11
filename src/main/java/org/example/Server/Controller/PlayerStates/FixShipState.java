package org.example.Server.Controller.PlayerStates;

import org.example.Server.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.Server.Model.Exceptions.InvalidPositionException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

public class FixShipState extends PlayerState {
    private Game game;

    public FixShipState(Game game) {
        this.game = game;
    }

    @Override
    public void removeTile(Points point, Player player){
        try {
            player.getPlayerShipBoard().removeComponent(point.getX(), point.getY());
        } catch(InvalidPositionException | AlreadyEmptyPositionException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void endFixShip(Player player){
        player.setShipOK(true);
        game.checkAllPlayersShip();

    }
}
