package org.example.ServerPkg.ControllerPkg.PlayerStates;

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
import java.security.InvalidParameterException;

public class AddAlienState extends PlayerState implements Serializable {
    public AddAlienState(Game game){
        super(game);
    }

    @Override
    public void addBrownAlien(Points p, Player player){
        try {
            if (player.getPlayerShipBoard().getComponent(p.getX(), p.getY()) != null && player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin() != null) {
                player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin().addAlien(new Alien(AlienColour.BROWN), player.getPlayerShipBoard());
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
            switch (position) {
                case 1:
                    position = 0;
                    break;
                case 2:
                    position = -1;
                    break;
                case 3:
                    position = -2;
                    break;
                case 4:
                    position = -3;
                    break;
                default:
                    throw new InvalidParameterException("Invalid position");
            }
            for(Player p : getGame().getPlayers()){
                if(p.getPosition() == position){
                    throw new InvalidParameterException("Position already taken");
                }
            }
            player.setPosition(position);
        }catch (InvalidParameterException e){
            new GameView(getGame(), new Exception(e.getMessage() + " " + player.getName()));
        }
    }

    @Override
    public void addPurpleAlien(Points p, Player player){
        try{
            if(player.getPlayerShipBoard().getComponent(p.getX(), p.getY())!=null && player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin()!=null ){
                player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin().addAlien(new Alien(AlienColour.PURPLE), player.getPlayerShipBoard());
                new GameView(getGame(), null);
            }else throw new InvalidParameterException("Invalid component");
        }catch (InvalidParameterException | AlreadyAlienException | WithoutLifeSupportException |
        DifferentLifeSupportColourException e){
            Exception e1 = new Exception(e.getMessage() + " " + player.getName());
            new GameView(getGame(), e1);
        }
    }

    @Override
    public void endAlienState(Player player){
        player.setReadyForCards(true);
        new GameView(getGame(), new Exception(player.getName() + " IS READY FOR CARDS "));
        for (Player p : getGame().getPlayers()) {
            if (!p.isAbandoned()) {
                if (!p.getReadyForCards()) {
                    return;
                }
                p.setPlayerState(new WaitingState(getGame()));
            }
        }
        getGame().Turn();
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(getGame());
        endAlienState(null);
    }
    
    @Override
    public void disconnect(Player disconnectingPlayer){
        getGame().disconnectPlayer(disconnectingPlayer);
        endAlienState(null);
    }
}
