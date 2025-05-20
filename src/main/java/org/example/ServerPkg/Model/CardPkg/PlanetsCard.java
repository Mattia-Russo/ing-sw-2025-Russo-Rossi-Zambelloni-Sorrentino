package org.example.ServerPkg.Model.CardPkg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnPlanetsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.PlanetView;
import org.example.ServerPkg.Model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanetsCard extends AdventureCard implements Serializable{
    private ArrayList<Planet> planets = new ArrayList<Planet>();
    private int playersIndex;
    private boolean planetsVisited[];
    private boolean changeGoodsFlag;
    private int currentPlanetIndex;
    private final int id;

    @JsonCreator
    public PlanetsCard(
            @JsonProperty("id") int id,
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int numDays,
            @JsonProperty("planets") ArrayList<Planet> planets) {
        super(cardLevel, numDays);
        this.planets=planets;
        this.playersIndex = -1;
        this.planetsVisited = new boolean[planets.size()];
        this.changeGoodsFlag = true;
        this.currentPlanetIndex = -1;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        List<PlanetView> p = new ArrayList<>();
        for(Planet planet : planets) {
            p.add(new PlanetView(planet));
        }
        return new AdventureCardView(id, "PlanetCard", getLostDays(),0 , 0,0, null, p, null, null, 0,null,null);
    }

    public List<Planet> getPlanets(){
        return planets;
    }

    public int getLostDays(){
        return super.getLostDays();
    }

    @Override
    public void setCardState(Game game){
        do {
            playersIndex++;
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).isAbandoned());

        if(playersIndex == game.getPlayers().size()){
            playersIndex = -1;
            game.Turn();
        } else {
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnPlanetsState(game));
        }
    }

    @Override
    public void playCard(Game game, int numPlanet) {
        game.getPlayers().get(playersIndex).changeOnPlanet();
        if (changeGoodsFlag){
            this.currentPlanetIndex = numPlanet;
            this.planetsVisited[numPlanet] = true;
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
        } else {
            game.getPlayers().get(playersIndex).changePosition(-this.getLostDays());
            game.getPlayers().get(playersIndex).setPlayerState(new WaitingState(game));
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
        return planets.get(currentPlanetIndex).getGoodsList();
    }

    @Override
    public boolean[] getPlanetsVisited(){
        return planetsVisited;
    }

    @Override
    public int getCurrentPlayerIndex(){
        return playersIndex;
    }

    // usage only for tests
    public void setPlanetIndex(int planetIndex){
        this.currentPlanetIndex = planetIndex;
    }

    // usage only for tests
    public boolean getChangeGoodsFlag(){
        return changeGoodsFlag;
    }

    // usage only for tests
    public int getCurrentPlanetIndex(){
        return currentPlanetIndex;
    }
}
