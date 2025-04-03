package org.example.Server.Model.CardPack;

import org.example.Server.Controller.States.*;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.util.ArrayList;

public class Slavers extends Enemy{
    private int numAstronauts;
    private int credits;
    private int playersIndex;
    private boolean accept;

    public Slavers(int cardLevel, int lostDays, int cannonPower, int numAstronauts, int credits) {
        super(cardLevel, lostDays, cannonPower);
        this.numAstronauts = numAstronauts;
        this.credits = credits;
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {return super.getLostDays();}

    public int getNumAstronauts() {
        return numAstronauts;
    }

    public int getCredits() {
        return credits;
    }

    @Override
    public void setCardState(Game game) {
        do {
            playersIndex++;
        } while (playersIndex < game.getPlayers().size() && !game.getPlayers().get(playersIndex).isAbandoned());

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            game.getPlayers().get(playersIndex).setPlayerState(new ActivateCannonsState(game));
        }

        for (int i = playersIndex + 1; i < game.getPlayers().size(); i++){
            if (!game.getPlayers().get(i).isAbandoned()){
                game.getPlayers().get(i).setPlayerState(new WaitingState());
            }
        }
    }

    @Override
    public void playCard(Game game, ArrayList<Points> cannons, ArrayList<Points> batteries) {
        if (game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries) > this.getCannonPower()) {
            game.getPlayers().get(playersIndex).setPlayerState(new WinEnemyState(game));
        }else if(game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries) == this.getCannonPower()){
            this.setCardState(game);
        } else {
            game.getPlayers().get(playersIndex).setPlayerState(new RemoveAstronautsState(game));
        }
    }

    @Override
    public void playCard(Game game){
        if (accept){
            game.getPlayers().get(playersIndex).changeCredits(getCredits());
            game.getPlayers().get(playersIndex).changePosition(-getLostDays());
        } else {
            this.playCard(game, 0);
        }
    }

    @Override
    public void playCard(Game game, int ignore){
        playersIndex = -1;
        game.getPlayers().get(playersIndex).setPlayerState(new WaitingState());
        game.Turn();
    }

    @Override
    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA getCredit O numAstronauts
}
