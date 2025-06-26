package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.MessagePkg.NotifyChangeGoodsMessage;
import org.example.ServerPkg.ConnectionsPkg.Handler;
import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.Storage;
import org.example.ServerPkg.Model.Exceptions.*;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ChangeGoodsState extends PlayerState implements Serializable {
    public ChangeGoodsState(Game game, Player player) throws RemoteException {
        super(game, player);
        Handler handler = game.getController().getNameServerMap().get(getPlayer().getName()).getHandlerByName(getPlayer().getName());
        NotifyChangeGoodsMessage message = new NotifyChangeGoodsMessage();
        handler.sendMessage(message);
    }

    // point è la coordinata dello storage, numGood è la posizione del good da rimuovere
    @Override
    public void removeGood(Points point, int numGood, Player player){
        Components c = player.getPlayerShipBoard().getComponent(point.getY(), point.getX());
        if(c!=null){
            Storage storage = c.isStorage();
            if(storage!=null) {
                if (numGood < storage.getCapacity()) {
                    storage.removeGood(numGood);
                    new GameView(getGame(), null);
                } else new GameView(getGame(), new Exception("Index must be below capacity " + player.getName()));
            }new GameView(getGame(), new NotStorageException("The component of given coordinates is not a storage" + player.getName()));
        } else {
            new GameView(getGame(), new NotStorageException("No component in these coordinates " + player.getName()));
        }
    }

    @Override
    public void addGood(Points point, int numGood, Player player){
        Components c = player.getPlayerShipBoard().getComponent(point.getY(), point.getX());
        if(c!=null){
            Storage storage = c.isStorage();
            if(storage!=null) {
                try {
                    Goods good = getGame().getCurrentCard().getGoodsList()[numGood];
                    if (numGood < getGame().getCurrentCard().getGoodsList().length && good != null) {
                        if(!good.isTaken()) {
                            storage.addGood(getGame().getCurrentCard().getGoodsList()[numGood]);
                            getGame().getCurrentCard().getGoodsList()[numGood] = null;
                            new GameView(getGame(), null);
                        } else new GameView(getGame(), new Exception("You've already taken this good " + player.getName()));
                    } else new GameView(getGame(), new Exception("Index must be between the bounds or you have already picked it " + player.getName()));
                } catch (RedGoodsNotAllowedException | StorageFullException e) {
                    Exception e1 = new Exception(e.getMessage() + " " + player.getName());
                    new GameView(getGame(), e1);
                }
            } else new GameView(getGame(), new NotStorageException("The component of given coordinates is not a storage " + player.getName()));
        } else new GameView(getGame(), new NotStorageException("No component in these coordinates " + player.getName()));
    }

    @Override
    public void endChangeGoods(Player player) throws RemoteException {
        getGame().getCurrentCard().setChangeGoodsFlag(false);
        if(!player.isAbandoned()) {
            player.setPlayerState(new WaitingState(getGame(), player));
        }
        getGame().getCurrentCard().playCard(getGame(), 0); // 0 è messo a caso, viene ignorato in questo caso
    }

    @Override
    public void AbandonGame(Player player) throws RemoteException {
        player.abandon(getGame());
        endChangeGoods(player);
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer) throws RemoteException {
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        endChangeGoods(disconnectingPlayer);
    }

}
