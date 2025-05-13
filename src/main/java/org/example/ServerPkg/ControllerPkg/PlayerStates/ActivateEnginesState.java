package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyEngineException;
import org.example.ServerPkg.Model.ForView.GameView;
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

    public void activateEngines(ArrayList<Points> newEngines, Player player){
        if(Engines==null){
            this.Engines = newEngines;
        }else
            new GameView(game, new AlreadyEngineException("Engine already activated" + player.getName()));
    }


    public void useBatteries(ArrayList<Points> newBatteries, Player player){
        if(Batteries==null) {
            this.Batteries = newBatteries;
        }else
            new GameView( game, new AlreadyBatteryException("Batteries already used" + player.getName()));
    }

    public void endActivateEngines(Player player){
        game.getCurrentCard().playCard(game,Engines, Batteries);
    }

    @Override
    public void AbandonGame(Player player){
        Batteries = null;
        Engines = null;
        player.abandon(game);
        endActivateEngines(null);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer(disconnectingPlayer);
        Engines=null;
        Batteries=null;
        endActivateEngines(null);
    }
}
