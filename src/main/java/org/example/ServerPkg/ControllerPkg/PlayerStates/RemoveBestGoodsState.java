package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPack.BatteryStorage;
import org.example.ServerPkg.Model.ComponentsPack.Goods;
import org.example.ServerPkg.Model.ComponentsPack.Storage;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;
import java.util.Comparator;

public class RemoveBestGoodsState extends PlayerState{
    private final Game game;
    private int goodsRemoved;
    private int batteriesRemoved;

    public RemoveBestGoodsState(Game game){
        this.game=game;
        this.goodsRemoved=0;
        this.batteriesRemoved=0;
    }

    @Override
    public void removeBestGood(Points point, int numGood){

        if(goodsRemoved == game.getCurrentCard().getNumGoodsLose()){
            throw new EnoughBestGoodsRemovedException("You've removed enough goods, don't need more");
        } else {
            Player currentPlayer = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex());
            ArrayList<Goods> goodsList = currentPlayer.getPlayerShipBoard().getTotalGoods();
            goodsList.sort(Comparator.comparing(Goods::getColour)); // ordina i goods in base al colore, da REd a BLUE
            Storage storage = currentPlayer.getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();

            if(storage!=null){
                if(storage.getGoods()[numGood].getColour() == goodsList.get(0).getColour()){
                    storage.removeGood(numGood);
                    goodsRemoved++;
                    new GameView(game);
                } else {
                    throw new NotStorageException("You've not selected the best good you have");
                }
            } else {
                throw new NotStorageException("The component of given coordinates is not a storage");
            }
        }
    }

    @Override
    public void removeBatteries(Points point){
        if (goodsRemoved + batteriesRemoved == game.getCurrentCard().getNumGoodsLose()){
            throw new EnoughBatteriesRemovedException("You've removed enough batteries");
        } else {
            Player currentPlayer = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex());
            ArrayList<Goods> goodsList = currentPlayer.getPlayerShipBoard().getTotalGoods();
            if(!goodsList.isEmpty()){
                throw new RemoveBatteriesBeforeGoodsException("You have to remove goods before batteries");
            } else {
                BatteryStorage storage = currentPlayer.getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isBatteryStorage();
                if(storage!=null){
                    try {
                        storage.setQuantity(-1, currentPlayer.getPlayerShipBoard());
                        batteriesRemoved++;
                        new GameView(game);
                    } catch (ValueUnderZeroException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    throw new NotBatteryStorageException("The component of given coordinates is not a battery storage");
                }
            }
        }
    }

    @Override
    public void endRemoveBestGoods(){
        if(goodsRemoved + batteriesRemoved < game.getCurrentCard().getNumGoodsLose()){
            throw new NotEnoughBestGoodsRemovedException("Cannot end this phase, need to remove more goods");
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }
}
