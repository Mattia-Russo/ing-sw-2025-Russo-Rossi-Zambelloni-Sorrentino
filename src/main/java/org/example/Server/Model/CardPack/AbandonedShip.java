package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.LandOnAbandonState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Game;

public class AbandonedShip extends AdventureCard {
    private int Credits;
    private int numAstronauts;
    private int playersIndex;

    public AbandonedShip(int CardLevel, int lostDays, int Credits, int numAstronauts) {
        super(CardLevel, lostDays);
        this.Credits = Credits;
        this.numAstronauts = numAstronauts;
        this.playersIndex = -1;
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
        playersIndex++;
        while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned()
                && game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalAstronauts()<this.numAstronauts){
            playersIndex++;
        }

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnAbandonState(game));
        }
    }

    @Override
    public void playCard(Game game){
        game.getPlayers().get(playersIndex-1).getPlayerShipBoard().setNumAstronauts(-this.numAstronauts);
        game.getPlayers().get(playersIndex-1).changeCredits(this.Credits);
        game.getPlayers().get(playersIndex-1).changePosition(-this.getLostDays());
        game.getPlayers().get(playersIndex-1).setPlayerState(new WaitingState());
        this.playersIndex=0;
        game.Turn();
    }

    @Override
    public int getCurrentPlanetIndex(){
        return playersIndex;
    }
}
