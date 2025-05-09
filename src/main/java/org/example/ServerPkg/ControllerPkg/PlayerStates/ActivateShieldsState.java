package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateShieldsState extends PlayerState {
    ArrayList<Points> shields;
    ArrayList<Points> batteries;
    private final Game game;

    public ActivateShieldsState(Game game){
        this.game = game;
        this.shields =null;
        this.batteries=null;
    }

    public void activateShields(ArrayList<Points> newShields){
        if(shields==null) {
            this.shields = newShields;
        }else
            throw new AlreadyShieldException("shields already activated");
    }

    public void useBatteries(ArrayList<Points> newBatteries){
        if(batteries==null) {
            this.batteries = newBatteries;
        }else
            throw new AlreadyBatteryException("Batteries already activated");
    }

    public void endActivateShields(){
        game.getCurrentCard().playCard(game, shields, batteries);
    }

    @Override
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        shields=null;
        batteries=null;
        endActivateShields();
    }

    @Override
    public void AbandonGame(Player player){
        if(Batteries==null || Shields==null) {
            Batteries = null;
            Shields = null;
        }
        player.abandon();
        endActivateCannons();
    }
}
