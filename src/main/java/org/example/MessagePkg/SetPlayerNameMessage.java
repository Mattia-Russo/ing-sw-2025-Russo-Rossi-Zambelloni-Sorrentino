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
