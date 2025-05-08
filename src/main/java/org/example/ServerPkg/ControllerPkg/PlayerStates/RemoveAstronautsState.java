package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPack.Cabin;
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
        if(astronautsRemoved < game.getCurrentCard().getNumGoodsLose()){
            throw new NotEnoughAstronautsRemovedException("Cannot end this phase, need to remove more astronauts");
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

}
