package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.util.List;

public class UpdatePlayersListMessage extends Message{
    private List<String> names;

    public UpdatePlayersListMessage(List<String> names){
        this.names=names;
    }

    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().onUpdatePlayerList(names);
    }
}
