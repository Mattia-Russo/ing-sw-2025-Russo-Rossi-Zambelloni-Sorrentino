package org.example.ServerPkg.Model.CardPack;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPack.Goods;
import org.example.ServerPkg.Model.Game;

public class AbandonedStation extends AdventureCard{
    private final int numAstronauts;
    private final Goods[] goodsList;
    private int playersIndex;
    boolean changeGoodsFlag;

    @JsonCreator
    public AbandonedStation(
            @JsonProperty("cardLevel") int cardLevel,
            @JsonProperty("lostDays") int lostDays,
            @JsonProperty("numAstronauts") int numAstronauts,
            @JsonProperty("goods") Goods[] goodsList) {
        super(cardLevel, lostDays);
        this.numAstronauts = numAstronauts;
        this.goodsList = goodsList;
        this.playersIndex = -1;
        this.changeGoodsFlag = true;
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {
        return super.getLostDays();
    }

    @Override
    public int getNumAstronauts() {
        return numAstronauts;
    }

    @Override
    public Goods[] getGoodsList() {
        return goodsList;
    }

    @Override
    public void setCardState(Game game) {
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
    public void playCard(Game game) {
        if (changeGoodsFlag){
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
        } else {
            game.getPlayers().get(playersIndex).changePosition(-this.getLostDays());
            game.getPlayers().get(playersIndex).setPlayerState(new WaitingState());
            playersIndex = -1;
            this.changeGoodsFlag = true;
            game.Turn();
        }
    }

    @Override
    public void setChangeGoodsFlag(boolean changeGoodsFlag) {
        this.changeGoodsFlag = changeGoodsFlag;
    }

    @Override
    public int getCurrentPlayerIndex(){
        return playersIndex;
    }

    // usage only for testing
    public boolean getChangeGoodsFlag() {
        return this.changeGoodsFlag;
    }
}
