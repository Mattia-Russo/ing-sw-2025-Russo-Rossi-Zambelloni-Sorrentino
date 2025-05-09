package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPack.Cannon;
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

    public void endActivateEngines(){
        game.getCurrentCard().playCard(game,Engines, Batteries);
    }

    @Override
<<<<<<< HEAD
    public void AbandonGame(Player player){
        if(Batteries==null || Engines==null) {
            Batteries = null;
            Engines = null;
        }
        player.abandon();
        endActivateCannons();
=======
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        Engines=null;
        Batteries=null;
        endActivateEngines();
>>>>>>> 9ee630db2862d761e24adf75fa54ed47806b11dd
    }
}
