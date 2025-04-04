package org.example.Server.Controller.States;

import org.example.Server.Model.Exceptions.AlreadyBatteryException;
import org.example.Server.Model.Exceptions.AlreadyEngineException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class ActivateEnginesState extends PlayerState {
    ArrayList<Points> Engines;
    ArrayList<Points> Batteries;
    private Game game;

    public ActivateEnginesState(Game game) {
        this.game = game;
        this.Engines=null;
        this.Batteries=null;
    }

    public void activateEngines(ArrayList<Points> engines){
        if(Engines==null){
            this.Engines = engines;
        }else
            throw new AlreadyEngineException("Engine already activated");
    }

    public void useBatteries(ArrayList<Points> batteries){
        if(Batteries==null) {
            this.Batteries = batteries;
        }else
            throw new AlreadyBatteryException("Batteries already activated");
    }

    public void endActivateEngine(){
        game.getCurrentCard().playCard(game,Engines, Batteries);
    }
}
