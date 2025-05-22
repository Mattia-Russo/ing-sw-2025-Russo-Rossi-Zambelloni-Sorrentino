package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Exceptions.AbandonedStateException;
import org.example.ServerPkg.Model.Exceptions.EndStateException;
import org.example.ServerPkg.Model.Exceptions.NotStorageException;
import org.example.ServerPkg.Model.Exceptions.WaitingStateException;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class PickBookedTileMessage extends Message {
    private int index;

    public PickBookedTileMessage(int index){
        this.index=index;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(checkClient()){
            Player player= controller.getGame().getPlayerByName(playerName);
            player.getState().pickBookedTile(index, player);
        }
    }
}
