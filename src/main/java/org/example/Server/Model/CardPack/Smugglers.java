package org.example.Server.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.Server.Controller.States.*;
import org.example.Server.Model.ComponentsPack.Goods;
import org.example.Server.Model.Exceptions.BatteriesLessThenCannonException;
import org.example.Server.Model.Exceptions.InvalidPositionException;
import org.example.Server.Model.Game;
import org.example.Server.Model.Points;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Smugglers extends Enemy{
    private List<Goods> goodsWinList = new ArrayList<Goods>();
    private final int numGoodsLose;
    private int playersIndex;
    private boolean accept;

    @JsonCreator
    public Smugglers(
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("cannonPower") int cannonPower,
            @JsonProperty("goodsLose") int goodsLose,
            @JsonProperty("goods") List<Goods> goodsWinList) {
        super(cardLevel,  lostDays, cannonPower);
        this.numGoodsLose = goodsLose;
        this.goodsWinList=goodsWinList;
        this.playersIndex = -1;
        this.accept=false;
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {return super.getLostDays();}

    public List<Goods> getGoodsWin() {
        return goodsWinList;
    }

    public int getGoodsLost() {
        return numGoodsLose;
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
            if (game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries) > this.getCannonPower()) {
                game.getPlayers().get(playersIndex).setPlayerState(new WinEnemyState(game));
            } else if (game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalCannonPower(cannons, batteries) == this.getCannonPower()) {
                this.setCardState(game);
            } else {
                game.getPlayers().get(playersIndex).setPlayerState(new RemoveBestGoodsState(game));
            }
        }catch(InvalidPositionException | InvalidParameterException | BatteriesLessThenCannonException e){
            System.out.println("Error" + e.getMessage());
            game.getPlayers().get(playersIndex).setPlayerState(new ActivateCannonsState(game));
        }
    }

    @Override
    public void playCard(Game game){
        if (accept){
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
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

    @Override
    public int getNumGoodsLose(){
        return this.numGoodsLose;
    }
    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA GOODSWIN O LOSE E CAMBIA LE RISORSE
}
