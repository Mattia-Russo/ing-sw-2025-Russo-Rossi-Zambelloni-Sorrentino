package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Controller.States.ChangeGoodsState;
import org.example.Server.Controller.States.LandOnAbandonState;
import org.example.Server.Controller.States.LandOnPlanetsState;
import org.example.Server.Controller.States.WaitingState;
import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.Game;

import java.util.ArrayList;
import java.util.List;

public class PlanetsCard extends AdventureCard {
    private List<Planet> planets = new ArrayList<Planet>();
    private int playersIndex;
    private boolean planetsVisited[];
    private boolean changeGoodsFlag;
    private int currentPlanet;

    @JsonCreator
    public PlanetsCard(
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int numDays,
            @JsonProperty("planets") List<Planet> planets) {
        super(cardLevel, numDays);
        this.planets=planets;
        this.playersIndex = -1;
        this.planetsVisited = new boolean[planets.size()];
        this.changeGoodsFlag = true;
        this.currentPlanet = 0;
    }

    public List<Planet> getPlanets(){
        return planets;
    }

    public int getLostDays(){
        return super.getLostDays();
    }

    @Override
    public void setCardState(Game game){
        playersIndex++;
        while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned()){
            playersIndex++;
        }

        if(playersIndex == game.getPlayers().size()){
            playersIndex = 0;
            game.Turn();
        } else {
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnPlanetsState(game));
        }
    }

    @Override
    public void playCard(Game game, int numPlanet) {
        game.getPlayers().get(playersIndex).changeOnPlanet();
        if (changeGoodsFlag){
            this.currentPlanet = numPlanet;
            this.planetsVisited[numPlanet] = true;
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
        } else {
            game.getPlayers().get(playersIndex).changePosition(-this.getLostDays());
            game.getPlayers().get(playersIndex).setPlayerState(new WaitingState());
            this.changeGoodsFlag = true;
            this.setCardState(game);
        }

    }

    @Override
    public void setChangeGoodsFlag(boolean changeGoodsFlag) {
        this.changeGoodsFlag = changeGoodsFlag;
    }

    @Override
    public Goods[] getGoodsList(){
        return planets.get(currentPlanet).getGoodsList();
    }

    @Override
    public boolean[] isPlanetsVisited(){
        return planetsVisited;
    }

    @Override
    public int getCurrentPlayerIndex(){
        return playersIndex;
    }
}
