package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.Model.ComponentsPkg.Alien;
import org.example.ServerPkg.Model.ComponentsPkg.AlienColour;
import org.example.ServerPkg.Model.Exceptions.AlreadyAlienException;
import org.example.ServerPkg.Model.Exceptions.DifferentLifeSupportColourException;
import org.example.ServerPkg.Model.Exceptions.WithoutLifeSupportException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.security.InvalidParameterException;

public class AddAlienState extends PlayerState implements Serializable {
    private boolean positionSet=false;
    public AddAlienState(Game game, Player player) throws RemoteException {
        super(game,player);
        Handler handler = game.getController().getNameServerMap().get(player.getName()).getHandlerByName(player.getName());
        handler.goToAddAlien();
    }

    @Override
    public void addBrownAlien(Points p, Player player){
        try {
            if (player.getPlayerShipBoard().getComponent(p.getY(), p.getX()) != null && player.getPlayerShipBoard().getComponent(p.getY(), p.getX()).isCabin() != null) {
                player.getPlayerShipBoard().getComponent(p.getY(), p.getX()).isCabin().addAlien(new Alien(AlienColour.BROWN), player.getPlayerShipBoard());
                new GameView(getGame(), null);
            } else throw new InvalidParameterException("Invalid component");
        }catch (InvalidParameterException | AlreadyAlienException | WithoutLifeSupportException |
                DifferentLifeSupportColourException e){
            Exception e1 = new Exception(e.getMessage() + " " + player.getName());
            new GameView(getGame(), e1);
        }
    }

    @Override
    public void selectPosition(int position, Player player){
        try {
            if(position >= -3 && position <= 0) {
                if(getGame().getGameMode()==0 && position==0){
                    position++;
                } else {
                    switch(position){
                        case 0:
                            position++;
                            break;
                        case -1:
                            position--;
                            break;
                        default:
                            position-=2;
                    }
                }
                for (Player p : getGame().getPlayers()) {
                    if (p.isPosValid() && p.getPosition() == position) {
                        throw new InvalidParameterException("Position already taken");
                    }
                }
                player.setPosition(position);
                positionSet = true;
                new GameView(getGame(), null);
            }else throw new InvalidParameterException("Invalid position");
        }catch (InvalidParameterException e){
            new GameView(getGame(), new Exception(e.getMessage() + " " + player.getName()));
        }
    }

    @Override
    public void addPurpleAlien(Points p, Player player){
        try{
            if(player.getPlayerShipBoard().getComponent(p.getY(), p.getX())!=null && player.getPlayerShipBoard().getComponent(p.getY(), p.getX()).isCabin()!=null ){
                player.getPlayerShipBoard().getComponent(p.getY(), p.getX()).isCabin().addAlien(new Alien(AlienColour.PURPLE), player.getPlayerShipBoard());
                new GameView(getGame(), null);
            }else throw new InvalidParameterException("Invalid component");
        }catch (InvalidParameterException | AlreadyAlienException | WithoutLifeSupportException |
        DifferentLifeSupportColourException e){
            Exception e1 = new Exception(e.getMessage() + " " + player.getName());
            new GameView(getGame(), e1);
        }
    }

    @Override
    public void endAlienState(Player player) throws RemoteException {
        if(positionSet) {
            player.setReadyForCards(true);
            new GameView(getGame(), new Exception("READY FOR CARDS " + player.getName()));
            for (Player p : getGame().getPlayers()) {
                if (!p.isAbandoned()) {
                    if (!p.getReadyForCards()) {
                        return;
                    }
                    p.setPlayerState(new WaitingState(getGame(), p));
                }
            }
            getGame().Turn();
        }else new GameView(getGame(), new Exception("You must select a position " + player.getName()));
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        player.abandon(getGame());
        endAlienState(player);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        endAlienState(disconnectingPlayer);
    }
}
