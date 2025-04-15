package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPack.Alien;
import org.example.ServerPkg.Model.ComponentsPack.AlienColour;
import org.example.ServerPkg.Model.Exceptions.AlreadyAlienException;
import org.example.ServerPkg.Model.Exceptions.DifferentLifeSupportColourException;
import org.example.ServerPkg.Model.Exceptions.WithoutLifeSupportException;
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
            }else throw new InvalidParameterException("Invalid component");
        }catch (InvalidParameterException | AlreadyAlienException | WithoutLifeSupportException |
        DifferentLifeSupportColourException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    public void endAlienState(){
        player.setReadyForCards(true);
        for (Player p : game.getPlayers()) {
            if (!p.getReadyForCards()) {
                return;
            }
            p.setPlayerState(new WaitingState());
        }
        game.Turn();
    }
}
