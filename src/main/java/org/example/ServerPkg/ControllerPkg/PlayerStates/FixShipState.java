package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyEmptyPositionException;
import org.example.ServerPkg.Model.Exceptions.InvalidPositionException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

public class FixShipState extends PlayerState {
    private Game game;

    public FixShipState(Game game) {
        this.game = game;
    }

    @Override
    public void removeTile(Points point, Player player){
        try {
            player.getPlayerShipBoard().removeComponent(point.getX(), point.getY());
            new GameView(game);
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
