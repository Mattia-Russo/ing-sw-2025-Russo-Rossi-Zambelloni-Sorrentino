package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.ServerPkg.Model.Player;

import java.rmi.RemoteException;

public class SetPlayerNameMessage extends Message{
    String playerName;

    public SetPlayerNameMessage(String playerName){
        this.playerName=playerName;
    }

    @Override
    public void handle(GameController controller, String playerName) throws RemoteException {
        if(getHandler()!=null) {
            if (controller.checkName(this.playerName)){
                getHandler().setPlayerName(this.playerName);
                getServer().notifyClient(getHandler().getPlayerName(), "true");
                getHandler().setGameUpdater();
            } else {
                getServer().notifyClient(getHandler().getPlayerName(), "false");
            }
        }else{
            System.out.println("Error: Proxy is null, could not set player name");
        }
    }
}
