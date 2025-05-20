package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivateShieldsState extends PlayerState implements Serializable {
    ArrayList<Points> shields;
    ArrayList<Points> batteries;
    private final Game game;

    public ActivateShieldsState(Game game){
        this.game = game;
        this.shields =null;
        this.batteries=null;
    }

    public void activateShields(ArrayList<Points> newShields, Player player){
        if(shields==null) {
            this.shields = newShields;
        }else
            new GameView(game, new AlreadyShieldException("shields already activated" + player.getName()));
    }

    public void useBatteries(ArrayList<Points> newBatteries, Player player){
        if(batteries==null) {
            this.batteries = newBatteries;
        }else
            new GameView(game,  new AlreadyBatteryException("Batteries already activated" + player.getName()));
    }

    public void endActivateShields(Player player){
        game.getCurrentCard().playCard(game, shields, batteries);
    }

    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer(disconnectingPlayer);
        shields=null;
        batteries=null;
        endActivateShields(null);
    }

    @Override
    public void AbandonGame(Player player){
        batteries = null;
        shields = null;
        player.abandon(game);
        endActivateShields(null);
    }
}
