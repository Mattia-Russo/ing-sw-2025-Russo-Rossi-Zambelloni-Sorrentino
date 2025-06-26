package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

import java.io.Serializable;

public class ShipWreckedState extends PlayerState implements Serializable {
    private final Player player;
    public ShipWreckedState(Game game, Player player) {
        super(game);
        this.player= player;
    }

    @Override
    public void chooseWrecked(Points point, Player player){
        player.getPlayerShipBoard().removeWreck(point.getX(), point.getY());
        player.setShipOK(true);
        new GameView(getGame(), null);
        if(getGame().getGameMode()==0) {
            player.setReadyForCards(true);
        }
    }

    @Override
    public void endWreckedState(Player player){
        if (player.getShipOK()) {
            if (getGame().getCurrentCard() != null) {
                getGame().getCurrentCard().setShipWrecked(false);
                getGame().getCurrentCard().setCardState(getGame());
            } else {
                if(getGame().getGameMode()==1) {
                    player.setPlayerState(new AddAlienState(getGame()));
                    new GameView(getGame(), new Exception(player.getName() + ", YOU CAN ADD YOUR ALIENS"));
                }else {
                    for (Player p : getGame().getPlayers()) {
                        if (!p.isAbandoned()) {
                            if (p.getReadyForCards()) {
                                return;
                            }
                            p.setPlayerState(new WaitingState(getGame()));
                            new GameView(getGame(), new Exception(p.getName() + " IS READY FOR CARDS "));
                        }
                    }
                    getGame().Turn();
                }
            }
        }else new GameView(getGame(), new InvalidMethodCallException(player.getName() + ", you have to fix your ship"));
    }

    @Override
    public void AbandonGame(Player player){
        Components c=null;
        int i=5;
        while(c==null && i < 10){
            c=player.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
            i++;
        }
        assert c != null;
        chooseWrecked(new Points(c.getPosX(), c.getPosY()), player);
        player.abandon(getGame());
        autoFix(player);
    }

    private void autoFix(Player player) {
        if (getGame().getCurrentCard() != null) {
            getGame().getCurrentCard().setShipWrecked(false);
            getGame().getCurrentCard().setCardState(getGame());
        } else {
            if(getGame().getGameMode()==1) {
                player.setReadyForCards(true);
            }
            for (Player p : getGame().getPlayers()) {
                if (!p.isAbandoned()) {
                    if (p.getReadyForCards()) {
                        return;
                    }
                }
            }
            getGame().Turn();
        }
    }

    @Override
    public void disconnect(Player disconnectingPlayer){
        Components c=null;
        int i=5;
        while(c==null && i < 10){
            c=disconnectingPlayer.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
            i++;
        }
        assert c != null;
        chooseWrecked(new Points(c.getPosX(), c.getPosY()), player);    // scegliamo noi un pezzo
        getGame().disconnectPlayer(disconnectingPlayer);
        disconnectingPlayer.abandon(getGame());
        autoFix(player);
    }
}
