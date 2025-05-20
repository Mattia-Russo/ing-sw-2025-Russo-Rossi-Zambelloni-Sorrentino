package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyCannonException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivateCannonsState extends PlayerState implements Serializable {
    private final Game game;
    private ArrayList<Points> cannons;
    private ArrayList<Points> batteries;

    public ActivateCannonsState(Game game){
        this.game = game;
        this.cannons=null;
        this.batteries=null;
    }

    @Override
    public void activateCannons(ArrayList<Points> newCannons, Player player) {
        if (cannons == null) {
            this.cannons = newCannons;
        } else {
            new GameView(game, new AlreadyCannonException("Cannons already activated" + player.getName()));
        }
    }

    @Override
    public void useBatteries(ArrayList<Points> newBatteries, Player player){
        if(batteries==null) {
            this.batteries = newBatteries;
        } else {
            new GameView( game, new AlreadyBatteryException("Batteries already used" + player.getName()));
        }
    }

    @Override
    public void endActivateCannons(Player player){
        game.getCurrentCard().playCard(game, cannons, batteries);
    }

    @Override
    public void AbandonGame(Player player){
        batteries = null;
        cannons = null;
        player.abandon(game);
        endActivateCannons(null);
    }

    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer(disconnectingPlayer);
        cannons=null;
        batteries=null;
        endActivateCannons(null);
    }

}
