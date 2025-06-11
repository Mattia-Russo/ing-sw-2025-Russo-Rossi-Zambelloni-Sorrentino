package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.RemoveAstronautsState;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

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
        String command = """
                You are playing the abandoned ship card, you can type:
                land_on_abandon true/false -> true if you want to land, false otherwise
                remove_astronauts x y -> x,y are the coordinates of the cabin where you want to remove the astronauts
                end_remove_astronauts -> to end remove astronauts state""";
                                   
        return new AdventureCardView(command, id, "AbandonedShip", getLostDays(), Credits, numAstronauts,0, null, null, null, null,0,null, null);
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
        } while (playersIndex < game.getPlayers().size() && (game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalAstronauts() < this.numAstronauts
                || game.getPlayers().get(playersIndex).isAbandoned()));

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            new GameView(game, new Exception("LAND ON ABANDON " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnAbandonState(game));
        }
    }

    @Override
    public void playCard(Game game){
        Player p = game.getPlayers().get(playersIndex);
        p.changeCredits(this.Credits);
        p.changePosition(-this.getLostDays() + game.getOccupiedPositions(p, -this.getLostDays()));
        new GameView(game, new Exception("REMOVE ASTRONAUTS " + game.getPlayers().get(playersIndex).getName()));
        p.setPlayerState(new RemoveAstronautsState(game));
        this.playersIndex=game.getPlayers().size()-1;
    }
}
