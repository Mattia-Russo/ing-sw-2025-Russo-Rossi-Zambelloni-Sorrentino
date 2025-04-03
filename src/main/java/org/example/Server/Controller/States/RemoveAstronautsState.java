package org.example.Server.Controller.States;

import org.example.Server.Model.ComponentsPack.Cabin;
import org.example.Server.Model.ComponentsPack.Storage;
import org.example.Server.Model.Exceptions.*;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

public class RemoveAstronautsState extends PlayerState{
    private Game game;
    private int astronautsRemoved;

    public RemoveAstronautsState(Game game) {
        this.game = game;
        this.astronautsRemoved=0;
    }

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
            } else {
                throw new NotCabinException("The component of given coordinates is not a cabin");
            }
        }
    }

    public void endRemoveAstronauts(){
        if(astronautsRemoved < game.getCurrentCard().getNumGoodsLose()){
            throw new NotEnoughAstronautsRemovedException("Cannot end this phase, need to remove more astronauts");
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

}
