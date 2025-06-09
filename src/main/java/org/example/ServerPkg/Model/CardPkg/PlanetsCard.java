package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnPlanetsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.ForView.PlanetView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlanetsCard extends AdventureCard implements Serializable{
    private final ArrayList<Planet> planets;
    private int playersIndex;
    private final boolean[] planetsVisited;
    private boolean changeGoodsFlag;
    private int currentPlanetIndex;
    private final int id;

    public PlanetsCard(int id,int cardLevel, int numDays,ArrayList<Planet> planets) {
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
        String command = """
               You are playing planet card, you can type:
               land_on_planet true/false numPlanet true if you want to land, false otherwise; numPlanet is the number of Planet where you want to land
               add_good x y numGood -> x,y are the coordinates of the storage where you want to add the good, numGood is the number of goods you want to add
               remove_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove
               end_change_goods -> if you want to terminate the exchanging goods phase""";

        return new AdventureCardView(command, id, "PlanetCard", getLostDays(),0 , 0,0, null, p, null, null, 0,null,null);
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
            new GameView(game, new Exception("LAND ON PLANET " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnPlanetsState(game));
        }
    }

    @Override
    public void playCard(Game game, int numPlanet) {
        game.getPlayers().get(playersIndex).changeOnPlanet();
        if (changeGoodsFlag){
            this.currentPlanetIndex = numPlanet;
            this.planetsVisited[numPlanet] = true;
            new GameView(game, new Exception("LAND ON PLANET (Change goods) " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
        } else {
            game.getPlayers().get(playersIndex).changePosition(-this.getLostDays() + + game.getOccupiedPositions(game.getPlayers().get(playersIndex), -this.getLostDays()));
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
