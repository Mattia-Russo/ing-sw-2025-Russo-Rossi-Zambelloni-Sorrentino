package org.example.ServerPkg.Model.CardPkg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.ControllerPkg.PlayerStates.*;
import org.example.ServerPkg.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.ServerPkg.Model.Exceptions.InvalidPositionException;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Points;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class Slavers extends Enemy{
    private int numAstronauts;
    private int credits;
    private int playersIndex;
    private boolean accept;
    private final int id;

    @JsonCreator
    public Slavers(
            @JsonProperty("id") int id,
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("cannonPower") int cannonPower,
            @JsonProperty("numAstronauts") int numAstronauts,
            @JsonProperty("credits") int credits) {
        super(cardLevel, lostDays, cannonPower);
        this.numAstronauts = numAstronauts;
        this.credits = credits;
        this.playersIndex = -1;
        this.accept=false;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        return new AdventureCardView(id, "Slavers", getLostDays(),credits , numAstronauts,getCannonPower(), null, null, null, null, 0,null,null);
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
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned());

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
        try {
            float power = game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries);
            new GameView(game);
            if ( power > this.getCannonPower()) {
                game.getPlayers().get(playersIndex).setPlayerState(new WinEnemyState(game));
            }else if(power == this.getCannonPower()){
                this.setCardState(game);
            } else {
                game.getPlayers().get(playersIndex).setPlayerState(new RemoveAstronautsState(game));
            }
        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
            System.out.println("Error" + e.getMessage());
            game.getPlayers().get(playersIndex).setPlayerState(new ActivateCannonsState(game));
        }
    }

    @Override
    public void playCard(Game game){
        if (accept){
            game.getPlayers().get(playersIndex).changeCredits(getCredits());
            game.getPlayers().get(playersIndex).changePosition(-getLostDays());
            new GameView(game);
        } else {
            this.playCard(game, 0);
        }
    }

    @Override
    public void playCard(Game game, int ignore){
        game.getPlayers().get(playersIndex).setPlayerState(new WaitingState());
        playersIndex = -1;
        game.Turn();
    }

    @Override
    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    //usage only in test
    public boolean getAccept(){
        return accept;
    }

    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA getCredit O numAstronauts
}
