package org.example.ServerPkg.ControllerPkg.PlayerStates;

import org.example.ServerPkg.Model.Exceptions.InvalidMethodCallException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

public class LandOnAbandonState extends PlayerState {
    private final Game game;

    public LandOnAbandonState(Game game){
        this.game = game;
    }

    // landed true vuol dire che è atterrato
    @Override
    public void landOnAbandon(boolean landed){
        if(landed){
            game.getCurrentCard().playCard(game);
        } else {
            game.getCurrentCard().setCardState(game);
        }
    }

    @Override
<<<<<<< HEAD
    public void AbandonGame(Player player){
        player.abandon();
=======
    public synchronized void disconnect(Player disconnectingPlayer, Game game){
        game.disconnectPlayer(disconnectingPlayer);
>>>>>>> 9ee630db2862d761e24adf75fa54ed47806b11dd
        landOnAbandon(false);
    }
}
