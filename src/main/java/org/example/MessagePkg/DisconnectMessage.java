package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.AlreadyShieldException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Game;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class DisconnectMessage extends Message{
    private Game game;
    private Player disconnectingPlayer;

    public DisconnectMessage(Player disconnectingPlayer, Game game){
        this.game=game;
        this.disconnectingPlayer=disconnectingPlayer;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            getClient().getServer().getController().getGame().getPlayerByName(getClient().getPlayerName()).getState().disconnect(disconnectingPlayer, game);
            }
        }
}
