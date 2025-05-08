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

import java.security.InvalidParameterException;

public class AddAlienState extends PlayerState{
    private Game game;
    private Player player;
    public AddAlienState(Game game, Player player){
        this.game=game;
        this.player=player;
    }

    @Override
    public void addBrownAlien(Points p){
        try {
            if (player.getPlayerShipBoard().getComponent(p.getX(), p.getY()) != null && player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin() != null) {
                player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin().addAlien(new Alien(AlienColour.BROWN), player.getPlayerShipBoard());
                new GameView(game);
            } else throw new InvalidParameterException("Invalid component");
        }catch (InvalidParameterException | AlreadyAlienException | WithoutLifeSupportException |
                DifferentLifeSupportColourException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    @Override
    public void addPurpleAlien(Points p){
        try{
            if(player.getPlayerShipBoard().getComponent(p.getX(), p.getY())!=null && player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin()!=null ){
                player.getPlayerShipBoard().getComponent(p.getX(), p.getY()).isCabin().addAlien(new Alien(AlienColour.PURPLE), player.getPlayerShipBoard());
                new GameView(game);
            }else throw new InvalidParameterException("Invalid component");
        }catch (InvalidParameterException | AlreadyAlienException | WithoutLifeSupportException |
        DifferentLifeSupportColourException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    @Override
    public void endAlienState(){
        player.setReadyForCards(true);
        for (Player p : game.getPlayers()) {
            if (!p.isAbandoned()) {
                if (!p.getReadyForCards()) {
                    return;
                }
                p.setPlayerState(new WaitingState());
            }
        }
        game.Turn();
    }

    @Override
    public void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
        endAlienState();
    }
}
