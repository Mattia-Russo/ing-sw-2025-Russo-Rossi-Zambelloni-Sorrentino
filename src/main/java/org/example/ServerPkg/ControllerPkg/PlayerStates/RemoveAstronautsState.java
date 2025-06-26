package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.MessagePkg.NotifyLandOnAbandonMessage;
import org.example.MessagePkg.NotifyRemoveAstronautsMessage;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.rmi.RemoteException;

public class RemoveAstronautsState extends PlayerState implements Serializable {
    private int astronautsRemoved;
    public RemoveAstronautsState(Game game, Player player) throws RemoteException {
        super(game, player);
        Handler handler = game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName());
        NotifyRemoveAstronautsMessage message = new NotifyRemoveAstronautsMessage();
        handler.sendMessage(message);
        this.astronautsRemoved=0;
    }

    @Override
    public void removeAstronauts(Points point, Player player){
        if(astronautsRemoved == getGame().getCurrentCard().getNumAstronauts()){
            new GameView(getGame(), new EnoughAstronautsRemovedException("You've removed enough astronauts, don't need more " + player.getName()));
        } else{
            Components c = player.getPlayerShipBoard().getComponent(point.getY(), point.getX());
            if(c!=null) {
                Cabin cabin = c.isCabin();
                if (cabin != null) {
                    if (cabin.getAlien() != null) {
                        cabin.removeAlien(player.getPlayerShipBoard());
                        astronautsRemoved++;
                    } else if (cabin.getNumAstronauts() != 0) {
                        cabin.changeNumAstronauts(-1, player.getPlayerShipBoard());
                        astronautsRemoved++;
                    }
                    new GameView(getGame(), null);
                } else {
                    new GameView(getGame(), new NotCabinException("The component of given coordinates is not a cabin " + player.getName()));
                }
            }else new GameView(getGame(), new NotStorageException("No component in these coordinates " + player.getName()));
        }
    }

    @Override
    public void endRemoveAstronauts(Player player) throws RemoteException {
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame()));
        }

        if(astronautsRemoved < getGame().getCurrentCard().getNumAstronauts()){
            new GameView(getGame(), new NotEnoughAstronautsRemovedException("Cannot end this phase, you need to remove more astronauts " + player.getName()));
        } else {
            if(!player.isAbandoned()) {
                player.setPlayerState(new WaitingState(getGame()));
            }
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        removeLeftAstronauts(player, getGame());
        new GameView(getGame(), null);
        player.abandon(getGame());
        endRemoveAstronauts(player);
    }

    @Override
    public void disconnect(Player p) throws RemoteException {
        removeLeftAstronauts(p, getGame());
        getGame().disconnectPlayer(p);
        p.abandon(getGame());
        new GameView(getGame(), null);
        endRemoveAstronauts(p);
    }

    private void removeLeftAstronauts(Player p, Game game) {
        int astronautsToRemove = game.getCurrentCard().getNumAstronauts() - astronautsRemoved;

        for (int i=5; i< p.getPlayerShipBoard().getComponentMatrix().length + 5 && astronautsToRemove > 0; i++){
            for(int j=4; j < p.getPlayerShipBoard().getComponentMatrix()[i].length + 4 && astronautsToRemove > 0; j++){
                Components c = p.getPlayerShipBoard().getComponent(i,j);
                if(c!= null) {
                    if (c.isCabin() != null) {
                        if (c.isCabin().hasAlien() != null) {
                            c.isCabin().removeAlien(p.getPlayerShipBoard());
                            astronautsRemoved++;
                            astronautsToRemove--;
                        } else if (((Cabin) c).getNumAstronauts() >= astronautsToRemove) {
                            ((Cabin) c).changeNumAstronauts(-astronautsToRemove, p.getPlayerShipBoard() );
                            astronautsRemoved += astronautsToRemove;
                            astronautsToRemove = 0;
                        } else {
                            ((Cabin) c).changeNumAstronauts(-((Cabin) c).getNumAstronauts(), p.getPlayerShipBoard());
                            astronautsRemoved += ((Cabin) c).getNumAstronauts();
                            astronautsToRemove -= ((Cabin) c).getNumAstronauts();
                        }
                    }
                }
            }
        }
    }

}
