package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;
import org.example.ServerPkg.Model.Points;

public class ShipWreckedState extends PlayerState {
    private final Game game;
    private Player player;
    public ShipWreckedState(Game game, Player player) {
        this.game = game;
        this.player=player;
    }

    @Override
    public void chooseWrecked(Points point){
        player.getPlayerShipBoard().removeWreck(point.getY(), point.getX());
        player.setShipOK(true);
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
                            if (!p.getReadyForCards()) {
                                return;
                            }
                            p.setPlayerState(new WaitingState());
                        }
                        game.Turn();
                    }
                }
            }else throw new InvalidMethodCallException("Fix your ship");
        }catch (InvalidMethodCallException e){
            System.out.println("ERROR " + e.getMessage());
        }
    }
}
