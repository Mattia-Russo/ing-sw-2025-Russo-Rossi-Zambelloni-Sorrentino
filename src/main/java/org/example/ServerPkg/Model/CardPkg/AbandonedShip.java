package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.RemoveAstronautsState;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;

import java.io.Serializable;

public class AbandonedShip extends AdventureCard implements Serializable {
    private final int Credits;
    private final int numAstronauts;
    private int playersIndex;
    private final int id;


    public AbandonedShip(int id, int CardLevel, int lostDays, int Credits, int numAstronauts) {
        super(CardLevel, lostDays);
        this.Credits = Credits;
        this.numAstronauts = numAstronauts;
        this.playersIndex = -1;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        return new AdventureCardView(id, "AbandonedShip", getLostDays(), Credits, numAstronauts,0, null, null, null, null,0,null, null);
    }

    public int getCardLevel(){
        return super.getCardLevel();
    }

    public int getLostDays(){
        return super.getLostDays();
    }

    public int getCredits(){
        return Credits;
    }

    @Override
    public int getNumAstronauts(){
        return numAstronauts;
    }

    @Override
    public void setCardState(Game game){
        do{
            playersIndex++;
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned()
                && game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalAstronauts() < this.numAstronauts);

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            new GameView(game, new Exception("LAND ON ABANDON " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnAbandonState(game));
        }
    }

    @Override
    public void playCard(Game game){
        game.getPlayers().get(playersIndex).getPlayerShipBoard().setNumAstronauts(-this.numAstronauts);
        game.getPlayers().get(playersIndex).changeCredits(this.Credits);
        game.getPlayers().get(playersIndex).changePosition(-this.getLostDays());
        game.getPlayers().get(playersIndex).setPlayerState(new RemoveAstronautsState(game));
        this.playersIndex=game.getPlayers().size()-1;
    }

    @Override
    public int getCurrentPlayerIndex(){
        return playersIndex;
    }
}
