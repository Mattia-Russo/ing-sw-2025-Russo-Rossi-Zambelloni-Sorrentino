package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPack.Storage;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

public class ChangeGoodsState extends PlayerState{
    private final Game game;

    public ChangeGoodsState(Game game){
        this.game = game;
    }

    // point è la coordinata dello storage, numGood è la posizione del good da rimuovere
    @Override
    public void removeGood(Points point, int numGood){
        Storage storage = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex()).getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();
        if(storage!=null){
            storage.removeGood(numGood);
            new GameView(game);
        } else {
            throw new NotStorageException("The component of given coordinates is not a storage");
        }
    }

    @Override
    public void addGood(Points point, int numGood){
        Storage storage = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex()).getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();
        if(storage!=null){
            try {
                storage.addGood(game.getCurrentCard().getGoodsList()[numGood]);
                new GameView(game);
            } catch (RedGoodsNotAllowedException | StorageFullException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } else {
            throw new NotStorageException("The component of given coordinates is not a storage");
        }
    }

    @Override
    public void endChangeGoods(){
        game.getCurrentCard().setChangeGoodsFlag(false);
        game.getCurrentCard().playCard(game, 0); // 0 è messo a caso, viene ignorato in questo caso
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon();
        endChangeGoods();
    }

}
