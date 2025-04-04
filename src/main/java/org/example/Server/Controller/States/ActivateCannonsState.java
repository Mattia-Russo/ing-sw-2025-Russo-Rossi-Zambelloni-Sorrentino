package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.AlreadyBatteryException;
import org.example.Server.Model.Exceptions.AlreadyCannonException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

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
    public void activateCannons(ArrayList<Points> cannons) {
        if(cannons==null) {
            this.cannons = cannons;
        }else
            throw new AlreadyCannonException("Cannons already activated");
    }

    @Override
    public void useBatteries(ArrayList<Points> batteries){
        if(batteries==null) {
            this.batteries = batteries;
        }else
            throw new AlreadyBatteryException("Batteries already activated");
    }

    @Override
    public void endActivateCannons(){
        game.getCurrentCard().playCard(game, cannons, batteries);
    }

}
