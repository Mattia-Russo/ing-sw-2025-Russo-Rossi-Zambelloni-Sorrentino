package org.example.ServerPkg.Model.CardPkg;

import org.example.ServerPkg.ControllerPkg.PlayerStates.ChangeGoodsState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.LandOnAbandonState;
import org.example.ServerPkg.ControllerPkg.PlayerStates.WaitingState;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ForView.AdventureCardView;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.ForView.GoodsView;
import org.example.ServerPkg.Model.Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbandonedStation extends AdventureCard implements Serializable {
    private final int numAstronauts;
    private final Goods[] goodsList;
    private int playersIndex;
    boolean changeGoodsFlag;
    private final int id;


    public AbandonedStation( int id,int cardLevel, int lostDays,int numAstronauts,Goods[] goodsList) {
        super(cardLevel, lostDays);
        this.numAstronauts = numAstronauts;
        this.goodsList = goodsList;
        this.playersIndex = -1;
        this.changeGoodsFlag = true;
        this.id = id;
    }

    @Override
    public AdventureCardView createView(){
        List<GoodsView> goods = new ArrayList<>();
        for(Goods good : goodsList){
            goods.add(new GoodsView(good));
        }
        String command = """
                You are playing the abandoned station card, you can type:
                land_on_abandon true/false -> true if you want to land, false otherwise
                add_good x y numGood -> x,y are the coordinates of the storage where you want to add the good, numGood is the number of goods you want to add
                remove_good x y numGood -> x,y are the coordinates of the storage where you want to remove the good, numGood is the number of goods you want to remove""";

        return new AdventureCardView(command, id, "AbandonedStation", getLostDays(),0 , numAstronauts,0, null, null, goods, null, 0,null,null);
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
        } while (playersIndex < game.getPlayers().size() && game.getPlayers().get(playersIndex).getPlayerShipBoard().getTotalAstronauts() < this.numAstronauts && game.getPlayers().get(playersIndex).isAbandoned());

        if(playersIndex == game.getPlayers().size()){
            game.Turn();
        } else {
            new GameView(game, new Exception("LAND ON ABANDON " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new LandOnAbandonState(game));
        }
    }

    @Override
    public void playCard(Game game) {
        this.playCard(game, 0);
    }

    @Override
    public void playCard(Game game, int ignore) {
        if (changeGoodsFlag){
            new GameView(game, new Exception("LAND ON ABANDON(Change goods) " + game.getPlayers().get(playersIndex).getName()));
            game.getPlayers().get(playersIndex).setPlayerState(new ChangeGoodsState(game));
        } else {
            game.getPlayers().get(playersIndex).changePosition(-this.getLostDays());
            game.getPlayers().get(playersIndex).setPlayerState(new WaitingState(game));
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
