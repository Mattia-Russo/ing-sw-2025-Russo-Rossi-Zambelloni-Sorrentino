package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.ComponentsPkg.Components;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;
import org.example.ServerPkg.Model.ForView.GameView;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

public class ShipWreckedState extends PlayerState {
    private final Game game;
    private final Player player;
    public ShipWreckedState(Game game, Player player) {
        this.game = game;
        this.player= player;
    }

    @Override
    public void chooseWrecked(Points point){
        player.getPlayerShipBoard().removeWreck(point.getY(), point.getX());
        player.setShipOK(true);
        new GameView(game);
        if(game.getGameMode()==0) {
            player.setReadyForCards(true);
        }
    }

    @Override
    public void endWreckedState(){
        try {
            if (player.getShipOK()) {
                if (game.getCurrentCard() != null) {
                    game.getCurrentCard().setShipWrecked(false);
                    game.getCurrentCard().setCardState(game);
                } else {
                    if(game.getGameMode()==1) {
                        player.setPlayerState(new AddAlienState(game, player));
                    }else {
                        for (Player p : game.getPlayers()) {
                            if (!p.isAbandoned()) {
                                if (!p.getReadyForCards()) {
                                    return;
                                }
                                p.setPlayerState(new WaitingState());
                            }
                        }
                        game.Turn();
                    }
                }
            }else throw new InvalidMethodCallException("Fix your ship");
        }catch (InvalidMethodCallException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }

    @Override
    public void AbandonGame(Player player){
        Components c=null;
        int i=0;
        while(c==null && i < 5){
            c=player.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
            i++;
        }
        chooseWrecked(new Points(c.getPosX(), c.getPosY()));
        player.abandon();
        if (game.getCurrentCard() != null) {
            game.getCurrentCard().setShipWrecked(false);
            game.getCurrentCard().setCardState(game);
        } else {
            if(game.getGameMode()==1) {
                player.setReadyForCards(true);
            }
            for (Player p : game.getPlayers()) {
                if (!p.isAbandoned()) {
                    if (!p.getReadyForCards()) {
                        return;
                    }
                }
            }
            game.Turn();
        }
    }
  
    @Override
    public void disconnect(Player disconnectingPlayer, Game game){
        Components c=null;
        int i=0;
        while(c==null && i < 5){
            c=disconnectingPlayer.getPlayerShipBoard().getFirstComponent(Direction.WEST, i);
            i++;
        }
        chooseWrecked(new Points(c.getPosX(), c.getPosY()));    // scegliamo noi un pezzo
        game.disconnectPlayer(disconnectingPlayer);

        if (game.getCurrentCard() != null) {
            game.getCurrentCard().setShipWrecked(false);
            game.getCurrentCard().setCardState(game);
        } else {
            if(game.getGameMode()==1) {
                player.setReadyForCards(true);
            }
            for (Player p : game.getPlayers()) {
                if (!p.isAbandoned()) {
                    if (!p.getReadyForCards()) {
                        return;
                    }
                }
            }
            game.Turn();
        }
    }
}
