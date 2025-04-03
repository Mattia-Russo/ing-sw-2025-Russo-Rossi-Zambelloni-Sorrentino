package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.LandState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.Game;

public class AbandonedShip extends AdventureCard {
    private int Credits;
    private int Astronauts;
    private int playersIndex;

    public AbandonedShip(int CardLevel, int lostDays, int Credits, int Astronauts) {
        super(CardLevel, lostDays);
        this.Credits = Credits;
        this.Astronauts = Astronauts;
        int playersIndex = 0;
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
        return Astronauts;
    }

    @Override
    public void setCardState(Game game){
        while (playersIndex < game.getPlayers().size() && !game.getPlayers().get(playersIndex).isAbandoned()){
            playersIndex++;
        }

        game.getPlayers().get(playersIndex).setPlayerState(new LandState(game));
        playersIndex++;

        for (int i = playersIndex; i < game.getPlayers().size(); i++){
            if (!game.getPlayers().get(i).isAbandoned()){
                game.getPlayers().get(i).setPlayerState(new WaitingState());
            }
        }
    }

    @Override
    public void playCard(Game game){
        game.getPlayers().get(playersIndex-1).getPlayerShipBoard().setNumAstronauts(-this.Astronauts);
        game.getPlayers().get(playersIndex-1).changeCredits(this.Credits);
        game.getPlayers().get(playersIndex-1).changePosition(-this.getLostDays());
        game.getPlayers().get(playersIndex-1).setPlayerState(new WaitingState());
        this.playersIndex=0;
        game.Turn();
    }
}
