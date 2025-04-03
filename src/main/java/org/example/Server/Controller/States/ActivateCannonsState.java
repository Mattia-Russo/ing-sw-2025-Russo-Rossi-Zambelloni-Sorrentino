package org.example.Server.Controller.States;

import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class ActivateCannonsState extends PlayerState {
    private Game game;
    private ArrayList<Points> cannons;
    private ArrayList<Points> batteries;

    public ActivateCannonsState(Game game){
        this.game = game;
        this.cannons=null;
        this.batteries=null;
    }

    public void activateCannons(ArrayList<Points> cannons){
        this.cannons = cannons;
    }

    public void useBatteries(ArrayList<Points> batteries){
        this.batteries = batteries;
    }

    public void endActivateCannons(){
        game.getCurrentCard().playCard(game, cannons, batteries);
    }

}
