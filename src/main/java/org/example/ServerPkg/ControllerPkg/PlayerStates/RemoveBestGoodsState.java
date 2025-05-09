package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.BatteryStorage;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.Storage;
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
                    new GameView(game, null);
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
                        new GameView(game, null);
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

    @Override
    public void AbandonGame(Player player){
        removeBestGoodsLeft(player, game);
        new GameView(game, null);
        player.abandon();
        endRemoveBestGoods();
    }

    @Override
    public void disconnect(Player p, Game game){
        // rimuovere noi i good migliori
        removeBestGoodsLeft(p, game);
        game.disconnectPlayer(p);
        endRemoveBestGoods();
    }

    private void removeBestGoodsLeft(Player player, Game game) {
        ArrayList<Goods> goodsList = player.getPlayerShipBoard().getTotalGoods();
        goodsList.sort(Comparator.comparing(Goods::getColour)); // ordina i goods in base al colore, da REd a BLUE

        while(this.goodsRemoved < game.getCurrentCard().getNumGoodsLose() || !goodsList.isEmpty()){
            Goods good = goodsList.get(0);
            Storage storage = good.getStorage();
            int i;
            for(i=0; i < storage.getGoods().length; i++){   // individuo l'indice del good
                if (storage.getGoods()[i] == good){
                    break;
                }
            }
            storage.removeGood(i);
            good.setStorage(null);
            this.goodsRemoved++;
        }

        int itemsToRemove = game.getCurrentCard().getNumGoodsLose() - goodsRemoved;

        for (int i=0; i< player.getPlayerShipBoard().getComponentMatrix().length && itemsToRemove > 0; i++){
            for(int j=0; j < player.getPlayerShipBoard().getComponentMatrix()[i].length; j++){
                Components c = player.getPlayerShipBoard().getComponentMatrix()[i][j];
                if(c.isBatteryStorage() != null){
                    if(((BatteryStorage) c).getQuantity() >= itemsToRemove){
                        ((BatteryStorage) c).setQuantity(-itemsToRemove, player.getPlayerShipBoard());
                        batteriesRemoved += itemsToRemove;
                        break;
                    } else {
                        ((BatteryStorage) c).setQuantity(-((BatteryStorage) c).getQuantity(), player.getPlayerShipBoard());
                        batteriesRemoved += ((BatteryStorage) c).getQuantity();
                    }
                }
            }
        }
    }
}
