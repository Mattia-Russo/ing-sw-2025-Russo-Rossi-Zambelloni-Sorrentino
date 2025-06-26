package org.example.ServerPkg.ControllerPkg.PlayerStates;


import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ActivateShieldsState extends PlayerState implements Serializable {
    ArrayList<Points> shields;
    ArrayList<Points> batteries;

    public ActivateShieldsState(Game game, Player player) throws RemoteException {
        super(game, player);
        game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName()).goToActivateShields();
        this.shields =null;
        this.batteries=null;
    }

    public void activateShields(ArrayList<Points> newShields, Player player){
        if(shields==null) {
            this.shields = newShields;
        }else
            new GameView(getGame(), new AlreadyShieldException("shields already activated" + player.getName()));
    }

    public void useBatteries(ArrayList<Points> newBatteries, Player player){
        if(batteries==null) {
            this.batteries = newBatteries;
        }else
            new GameView(getGame(),  new AlreadyBatteryException("Batteries already activated" + player.getName()));
    }

    public void endActivateShields(Player player) throws RemoteException {
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame(), player));
        }
        getGame().getCurrentCard().playCard(getGame(), shields, batteries);
    }

    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        shields=null;
        batteries=null;
        endActivateShields(disconnectingPlayer);
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        batteries = null;
        shields = null;
        player.abandon(getGame());
        endActivateShields(player);
    }
}
