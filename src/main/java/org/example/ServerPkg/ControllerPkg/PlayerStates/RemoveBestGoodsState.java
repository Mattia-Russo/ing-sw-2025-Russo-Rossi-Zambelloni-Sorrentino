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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class RemoveBestGoodsState extends PlayerState implements Serializable {
    private int goodsRemoved;
    private int batteriesRemoved;

    public RemoveBestGoodsState(Game game){
        super(game);
        this.goodsRemoved=0;
        this.batteriesRemoved=0;
    }

    @Override
    public void removeBestGood(Points point, int numGood, Player player){

        if(goodsRemoved == getGame().getCurrentCard().getNumGoodsLose()){
            new GameView(getGame(), new EnoughBestGoodsRemovedException("You've removed enough goods, don't need more " + player.getName()));
        } else {
            Player currentPlayer = getGame().getPlayers().get(getGame().getCurrentCard().getCurrentPlayerIndex());
            ArrayList<Goods> goodsList = currentPlayer.getPlayerShipBoard().getTotalGoods();
            goodsList.sort(Comparator.comparing(Goods::getColour)); // ordina i goods in base al colore, da REd a BLUE
            Components c = currentPlayer.getPlayerShipBoard().getComponent(point.getX(), point.getY());
            if(c!=null) {
                Storage storage = c.isStorage();
                if (storage != null) {
                    if (storage.getGoods()[numGood].getColour() == goodsList.getFirst().getColour()) {
                        storage.removeGood(numGood);
                        goodsRemoved++;
                        new GameView(getGame(), null);
                    } else {
                        new GameView(getGame(), new NotStorageException("You've not selected the best good you have " + player.getName()));
                    }
                } else {
                    new GameView(getGame(), new NotStorageException("The component of given coordinates is not a storage " + player.getName()));
                }
            } new GameView(getGame(), new NotStorageException("No component in these coordinates " + player.getName()));
        }
    }

    @Override
    public void removeBatteries(Points point, Player player){
        if (goodsRemoved + batteriesRemoved == getGame().getCurrentCard().getNumGoodsLose()){
            new GameView(getGame(), new EnoughBatteriesRemovedException("You've removed enough batteries " + player.getName()));
        } else {
            Player currentPlayer = getGame().getPlayers().get(getGame().getCurrentCard().getCurrentPlayerIndex());
            ArrayList<Goods> goodsList = currentPlayer.getPlayerShipBoard().getTotalGoods();
            if(!goodsList.isEmpty()){
                new GameView(getGame(), new RemoveBatteriesBeforeGoodsException("You have to remove goods before batteries " + player.getName()));
            } else {
                Components c = currentPlayer.getPlayerShipBoard().getComponent(point.getX(),point.getY());
                if(c!=null) {
                    BatteryStorage storage = c.isBatteryStorage();
                    if (storage != null) {
                        try {
                            storage.setQuantity(-1, currentPlayer.getPlayerShipBoard());
                            batteriesRemoved++;
                            new GameView(getGame(), null);
                        } catch (ValueUnderZeroException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        new GameView(getGame(), new NotBatteryStorageException("The component of given coordinates is not a battery storage " + player.getName()));
                    }
                }else new GameView(getGame(), new NotStorageException("No component in these coordinates " + player.getName()));
            }
        }
    }

    @Override
    public void endRemoveBestGoods(Player player){
        if(goodsRemoved + batteriesRemoved < getGame().getCurrentCard().getNumGoodsLose()){
            new GameView(getGame(), new NotEnoughBestGoodsRemovedException("Cannot end this phase, need to remove more goods " + player.getName()));
        } else {
            getGame().getCurrentCard().setCardState(getGame());
        }
    }

    @Override
    public void AbandonGame(Player player){
        removeBestGoodsLeft(player, getGame());
        new GameView(getGame(), null);
        player.abandon(getGame());
        endRemoveBestGoods(null);
    }

    @Override
    public void disconnect(Player p){
        // rimuovere noi i good migliori
        removeBestGoodsLeft(p, getGame());
        getGame().disconnectPlayer(p);
        endRemoveBestGoods(null);
    }

    private void removeBestGoodsLeft(Player player, Game game) {
        ArrayList<Goods> goodsList = player.getPlayerShipBoard().getTotalGoods();
        goodsList.sort(Comparator.comparing(Goods::getColour)); // ordina i goods in base al colore, da REd a BLUE

        while(this.goodsRemoved < game.getCurrentCard().getNumGoodsLose() || !goodsList.isEmpty()){
            Goods good = goodsList.getFirst();
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

        for (int i=4; i< player.getPlayerShipBoard().getComponentMatrix().length +4 && itemsToRemove > 0; i++){
            for(int j=5; j < player.getPlayerShipBoard().getComponentMatrix()[i].length + 5; j++){
                Components c = player.getPlayerShipBoard().getComponent(i, j);
                if(c!=null) {
                    if (c.isBatteryStorage() != null) {
                        if (((BatteryStorage) c).getQuantity() >= itemsToRemove) {
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
}
