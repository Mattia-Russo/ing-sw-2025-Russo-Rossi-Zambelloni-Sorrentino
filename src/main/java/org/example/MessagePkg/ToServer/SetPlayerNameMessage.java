package org.example.MessagePkg.ToServer;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.rmi.RemoteException;

public class SetPlayerNameMessage extends Message {
    String playerName;

    public SetPlayerNameMessage(String playerName){
        this.playerName=playerName;
    }

    @Override
    public void handle(GameController controller, String name) throws RemoteException {
        if(getHandler()!=null) {
            if (controller.checkName(this.playerName, getServer())){
                getHandler().setPlayerName(this.playerName);
                getHandler().setGameUpdater();
                if(controller.getFileLoaded()){
                    controller.addGameUpdater(getServer().getGameUpdater(getHandler().getPlayerName()), getHandler().getPlayerName());
                }
            } else {
                getHandler().notifyNameAlreadyUsed();
            }
        }else{
            System.out.println("Error: Proxy is null, could not set player name");
        }
    }
}
