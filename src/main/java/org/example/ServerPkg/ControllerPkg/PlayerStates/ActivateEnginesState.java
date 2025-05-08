package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyEngineException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateEnginesState extends PlayerState {
    ArrayList<Points> Engines;
    ArrayList<Points> Batteries;
    private final Game game;

    public ActivateEnginesState(Game game) {
        this.game = game;
        this.Engines=null;
        this.Batteries=null;
    }

    public void activateEngines(ArrayList<Points> newEngines){
        if(Engines==null){
            this.Engines = newEngines;
        }else
            throw new AlreadyEngineException("Engine already activated");
    }

    public void useBatteries(ArrayList<Points> newBatteries){
        if(Batteries==null) {
            this.Batteries = newBatteries;
        }else
            throw new AlreadyBatteryException("Batteries already activated");
    }

    public void endActivateEngine(){
        game.getCurrentCard().playCard(game,Engines, Batteries);
    }

    @Override
    public synchronized void disconnect(Player disconnectingPlayer){
        disconnectingPlayer.abandon();
        //TBD
    }
}
