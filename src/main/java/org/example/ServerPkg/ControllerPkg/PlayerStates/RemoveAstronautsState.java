package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;

public class RemoveAstronautsState extends PlayerState implements Serializable {
    private int astronautsRemoved;
    public RemoveAstronautsState(Game game) {
        super(game);
        this.astronautsRemoved=0;
    }

    @Override
    public void removeAstronauts(Points point, Player player){
        if(astronautsRemoved == getGame().getCurrentCard().getNumAstronauts()){
            new GameView(getGame(), new EnoughAstronautsRemovedException("You've removed enough astronauts, don't need more " + player.getName()));
        } else{
            Player currentPlayer = getGame().getPlayers().get(getGame().getCurrentCard().getCurrentPlayerIndex());
            Cabin cabin = currentPlayer.getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isCabin();

            if(cabin!=null){
                if(cabin.getAlien()!=null){
                    cabin.removeAlien();
                    astronautsRemoved++;
                } else if (cabin.getNumAstronauts()!=0){
                    cabin.changeNumAstronauts(-1);
                    astronautsRemoved++;
                }
                new GameView(getGame(), null);
            } else {
                new GameView(getGame(), new NotCabinException("The component of given coordinates is not a cabin " + player.getName()));
            }
        }
    }

    @Override
    public void endRemoveAstronauts(Player player){
        if(astronautsRemoved < getGame().getCurrentCard().getNumAstronauts()){
            new GameView(getGame(), new NotEnoughAstronautsRemovedException("Cannot end this phase, need to remove more astronauts " + player.getName()));
        } else {
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player){
        removeLeftAstronauts(player, getGame());
        new GameView(getGame(), null);
        player.abandon(getGame());
        endRemoveAstronauts(null);
    }

    @Override
    public void disconnect(Player p){
        // rimuovere noi gli astronauti
        removeLeftAstronauts(p, getGame());
        getGame().disconnectPlayer(p);
        new GameView(getGame(), null);
        endRemoveAstronauts(null);
    }

    private void removeLeftAstronauts(Player p, Game game) {
        int astronautsToRemove = game.getCurrentCard().getNumAstronauts() - astronautsRemoved;

        for (int i=0; i< p.getPlayerShipBoard().getComponentMatrix().length && astronautsToRemove > 0; i++){
            for(int j=0; j < p.getPlayerShipBoard().getComponentMatrix()[i].length && astronautsToRemove > 0; j++){
                Components c = p.getPlayerShipBoard().getComponentMatrix()[i][j];
                if(c.isCabin() != null){
                    if(c.isCabin().hasAlien()!=null){
                        c.isCabin().removeAlien();
                        astronautsRemoved++;
                        astronautsToRemove--;
                    }else if(((Cabin) c).getNumAstronauts() >= astronautsToRemove){
                        ((Cabin) c).changeNumAstronauts(-astronautsToRemove);
                        astronautsRemoved +=  astronautsToRemove;
                        astronautsToRemove = 0;
                    } else {
                        ((Cabin) c).changeNumAstronauts(-((Cabin) c).getNumAstronauts());
                        astronautsRemoved += ((Cabin) c).getNumAstronauts();
                        astronautsToRemove -= ((Cabin) c).getNumAstronauts();
                    }
                }
            }
        }
    }

}
