package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Storage;
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
    public void removeGood(Points point, int numGood, Player player){
        Storage storage = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex()).getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();
        if(storage!=null){
            storage.removeGood(numGood);
            new GameView(game, null);
        } else {
            new GameView(game, new NotStorageException("The component of given coordinates is not a storage" + player.getName()));
        }
    }

    @Override
    public void addGood(Points point, int numGood, Player player){
        Storage storage = game.getPlayers().get(game.getCurrentCard().getCurrentPlayerIndex()).getPlayerShipBoard().getComponentMatrix()[point.getX()][point.getY()].isStorage();
        if(storage!=null){
            try {
                storage.addGood(game.getCurrentCard().getGoodsList()[numGood]);
                new GameView(game, null);
            } catch (RedGoodsNotAllowedException | StorageFullException e) {
                Exception e1 = new Exception(e.getMessage() + " " + player.getName());
                new GameView(game, e1);
            }
        } else {
            new GameView(game, new NotStorageException("The component of given coordinates is not a storage " + player.getName()));
        }
    }

    @Override
    public void endChangeGoods(Player player){
        game.getCurrentCard().setChangeGoodsFlag(false);
        game.getCurrentCard().playCard(game, 0); // 0 è messo a caso, viene ignorato in questo caso
    }

    @Override
    public void AbandonGame(Player player){
        player.abandon(game);
        endChangeGoods(null);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer){
        game.disconnectPlayer(disconnectingPlayer);
        endChangeGoods(null);
    }

}
