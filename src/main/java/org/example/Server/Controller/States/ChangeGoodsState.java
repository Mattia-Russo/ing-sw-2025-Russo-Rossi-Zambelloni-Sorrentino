package org.example.Server.Controller.States;

import org.example.Server.Model.ComponentsPack.Storage;
import org.example.Server.Model.Exceptions.*;
import org.example.Server.Model.Game;
import org.example.Server.Model.Player;
import org.example.Server.Model.Points;

public class ChangeGoodsState extends PlayerState{
    private Game game;
    private boolean changeGoodsFlag;

    public ChangeGoodsState(Game game){
        this.game = game;
        this.changeGoodsFlag = false;
    }

    public void removeGood(Player player, Points point, int numGood){   // player è il chiamante, point è la coordinata dello storage, numGood è la posizione del good da rimuovere
        Storage storage = player.getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();
        if(storage!=null){
            storage.removeGood(numGood);
        } else {
            throw new NotStorageException("The component of given coordinates is not a storage");
        }
    }

    public void addGood(Player player, Points point, int numGood){
        Storage storage = player.getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();
        if(storage!=null){
            try {
                storage.addGood(game.getCurrentCard().getGoodsList()[numGood]);
            } catch (RedGoodsNotAllowedException | StorageFullException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } else {
            throw new NotStorageException("The component of given coordinates is not a storage");
        }
    }

    public void endChangeGoods(){
        game.getCurrentCard().playCard(game);
    }

}
