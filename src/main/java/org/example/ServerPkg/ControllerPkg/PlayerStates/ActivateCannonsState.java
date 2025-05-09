package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyCannonException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateCannonsState extends PlayerState {
    private final Game game;
    private ArrayList<Points> cannons;
    private ArrayList<Points> batteries;

    public ActivateCannonsState(Game game){
        this.game = game;
        this.cannons=null;
        this.batteries=null;
    }

    @Override
    public void activateCannons(ArrayList<Points> newCannons) {
        if (cannons == null) {
            this.cannons = newCannons;
        } else {
            throw new AlreadyCannonException("Cannons already activated");
        }
    }

    @Override
    public void useBatteries(ArrayList<Points> newBatteries){
        if(batteries==null) {
            this.batteries = newBatteries;
        } else {
            throw new AlreadyBatteryException("Batteries already used");
        }
    }

    @Override
    public void endActivateCannons(){
        game.getCurrentCard().playCard(game, cannons, batteries);
    }

    @Override
<<<<<<< HEAD
    public void AbandonGame(Player player){
        if(batteries==null || cannons==null) {
            batteries = null;
            cannons = null;
        }
        player.abandon();
        endActivateCannons();
    }
=======
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        cannons=null;
        batteries=null;
        endActivateCannons();
    }

>>>>>>> 9ee630db2862d761e24adf75fa54ed47806b11dd
}
