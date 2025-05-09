package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Cabin;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

public class RemoveAstronautsState extends PlayerState{
    private final Game game;
    private int astronautsRemoved;

    public RemoveAstronautsState(Game game) {
        this.game = game;
        this.astronautsRemoved=0;
    }

    @Override
    public void removeAstronauts(Points point){
        if(astronautsRemoved == game.getCurrentCard().getNumAstronauts()){
            throw new EnoughAstronautsRemovedException("You've removed enough astronauts, don't need more");
        } else{
            Player currentPlayer = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex());
            Cabin cabin = currentPlayer.getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isCabin();

            if(cabin!=null){
                if(cabin.getAlien()!=null){
                    cabin.removeAlien();
                    astronautsRemoved++;
                } else if (cabin.getNumAstronauts()!=0){
                    cabin.changeNumAstronauts(-1);
                    astronautsRemoved++;
                }
                new GameView(game);
            } else {
                throw new NotCabinException("The component of given coordinates is not a cabin");
            }
        }
    }

    @Override
    public void endRemoveAstronauts(){
        if(astronautsRemoved < game.getCurrentCard().getNumAstronauts()){
            throw new NotEnoughAstronautsRemovedException("Cannot end this phase, need to remove more astronauts");
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

    @Override
    public void AbandonGame(Player player){
        removeLeftAstronauts(player, game);
        player.abandon();
        endRemoveAstronauts();
    }

    @Override
    public void disconnect(Player p, Game game){
        // rimuovere noi gli astronauti
        removeLeftAstronauts(p, game);
        game.disconnectPlayer(p);
        endRemoveAstronauts();
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
