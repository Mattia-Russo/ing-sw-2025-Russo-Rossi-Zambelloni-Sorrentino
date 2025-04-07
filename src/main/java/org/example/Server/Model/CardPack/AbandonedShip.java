package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Controller.States.LandOnAbandonState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Game;

public class AbandonedShip extends AdventureCard {
    private int Credits;
    private int numAstronauts;
    private int playersIndex;

    @JsonCreator
    public AbandonedShip(
            @JsonProperty("cardLevel") int CardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("credits") int Credits,
            @JsonProperty("numAstronauts") int numAstronauts) {
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
        do {
            playersIndex++;
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned()
                && game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalAstronauts() < this.numAstronauts);

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnAbandonState(game));
        }
    }

    @Override
    public void playCard(Game game){
        game.getPlayers().get(playersIndex).getPlayerShipBoard().setNumAstronauts(-this.numAstronauts);
        game.getPlayers().get(playersIndex).changeCredits(this.Credits);
        game.getPlayers().get(playersIndex).changePosition(-this.getLostDays());
        game.getPlayers().get(playersIndex).setPlayerState(new WaitingState());
        this.playersIndex=-1;
        game.Turn();
    }

    @Override
    public int getCurrentPlayerIndex(){
        return playersIndex;
    }
}
