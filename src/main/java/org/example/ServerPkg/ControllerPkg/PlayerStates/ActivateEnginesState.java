package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyEngineException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivateEnginesState extends PlayerState implements Serializable {
    ArrayList<Points> engines;
    ArrayList<Points> batteries;

    public ActivateEnginesState(Game game) {
        super(game, null);
        this.engines =null;
        this.batteries =null;
    }

    public void activateEngines(ArrayList<Points> newEngines, Player player){
        if(engines ==null){
            this.engines = newEngines;
        }else
            new GameView(getGame(), new AlreadyEngineException("Engine already activated" + player.getName()));
    }


    public void useBatteries(ArrayList<Points> newBatteries, Player player){
        if(batteries ==null) {
            this.batteries = newBatteries;
        }else
            new GameView( getGame(), new AlreadyBatteryException("Batteries already used" + player.getName()));
    }

    public void endActivateEngines(Player player){
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame()));
        }
        getGame().getCurrentCard().playCard(getGame(), engines, batteries);
    }

    @Override
    public void AbandonGame(Player player){
        batteries = null;
        engines = null;
        player.abandon(getGame());
        endActivateEngines(player);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        engines =null;
        batteries =null;
        endActivateEngines(disconnectingPlayer);
    }
}
