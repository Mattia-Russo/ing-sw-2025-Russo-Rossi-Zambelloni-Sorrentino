package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;
import org.example.UIPkg.GameUpdater;

public class SetPlayerNameMessage extends Message{
    String playerName;
    String gameUpdater;

    public SetPlayerNameMessage(String playerName, String gameUpdater){
        this.playerName=playerName;
        this.gameUpdater=gameUpdater;
    }

    @Override
    public void handle(GameController controller, String playerName){
        if(getProxy()!=null){
            getProxy().setPlayerName(this.playerName);
            getProxy().setUI(this.gameUpdater, controller);
        }else{
            System.out.println("Error: Proxy is null, could not set player name");
        }
    }
}
