package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.AlreadyBatteryException;
import org.example.Server.Model.Exceptions.AlreadyCannonException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class ActivateCannonsState extends PlayerState {
    private Game game;
    private ArrayList<Points> Cannons;
    private ArrayList<Points> Batteries;

    public ActivateCannonsState(Game game){
        this.game = game;
        this.Cannons=null;
        this.Batteries=null;
    }

    public void activateCannons(ArrayList<Points> cannons) {
        if(Cannons==null) {
            this.Cannons = cannons;
        }else
            throw new AlreadyCannonException("Cannons already activated");
    }

    public void useBatteries(ArrayList<Points> batteries){
        if(Batteries==null) {
            this.Batteries = batteries;
        }else
            throw new AlreadyBatteryException("Batteries already activated");
    }

    public void endActivateCannons(){
        game.getCurrentCard().playCard(game, Cannons, Batteries);
    }

}
