package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

public class SetPlayerNameMessage extends Message{
    String playerName;

    public SetPlayerNameMessage(String playerName){
        this.playerName=playerName;
    }

    @Override
    public void handle(GameController controller, String playerName){
        if(getProxy()!=null){
            getProxy().setPlayerName(this.playerName);
            System.out.println("proxy non è null");
        }else{
            System.out.println("proxy è null");
        }
    }
}
