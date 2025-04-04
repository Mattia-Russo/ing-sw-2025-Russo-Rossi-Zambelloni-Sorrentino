package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.AlreadyBatteryException;
import org.example.Server.Model.Exceptions.AlreadyShieldException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class ActivateShieldsState extends PlayerState {
    ArrayList<Points> Shields;
    ArrayList<Points> Batteries;
    private final Game game;

    public ActivateShieldsState(Game game){
        this.game = game;
        this.Shields =null;
        this.Batteries=null;
    }

    public void activateShields(ArrayList<Points> newShields){
        if(Shields==null) {
            this.Shields = newShields;
        }else
            throw new AlreadyShieldException("Shields already activated");
    }

    public void useBatteries(ArrayList<Points> newBatteries){
        if(Batteries==null) {
            this.Batteries = newBatteries;
        }else
            throw new AlreadyBatteryException("Batteries already activated");
    }

    public void endActivateShields(){
        game.getCurrentCard().playCard(game, Shields, Batteries);
    }
}
