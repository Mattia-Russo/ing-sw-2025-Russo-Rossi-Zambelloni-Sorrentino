package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.AlreadyBatteryException;
import org.example.ServerPkg.Model.Exceptions.AlreadyCannonException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class ActivateCannonsState extends PlayerState implements Serializable {
    private ArrayList<Points> cannons;
    private ArrayList<Points> batteries;

    public ActivateCannonsState(Game game, Player player) throws RemoteException {
        super(game, player);
        game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName()).goToActivateCannons();
        this.cannons=null;
        this.batteries=null;
    }

    @Override
    public void activateCannons(ArrayList<Points> newCannons, Player player) {
        if (cannons == null) {
            this.cannons = newCannons;
        } else {
            new GameView(getGame(), new AlreadyCannonException("Cannons already activated" + player.getName()));
        }
    }

    @Override
    public void useBatteries(ArrayList<Points> newBatteries, Player player){
        if(batteries==null) {
            this.batteries = newBatteries;
        } else {
            new GameView( getGame(), new AlreadyBatteryException("Batteries already used" + player.getName()));
        }
    }

    @Override
    public void endActivateCannons(Player player) throws RemoteException {
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame(), player));
        }
        getGame().getCurrentCard().playCard(getGame(), cannons, batteries);
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        batteries = null;
        cannons = null;
        player.abandon(getGame());
        endActivateCannons(player);
    }

    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        cannons=null;
        batteries=null;
        endActivateCannons(disconnectingPlayer);
    }

}
