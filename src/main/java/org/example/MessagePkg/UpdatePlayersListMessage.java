package org.example.MessagePkg;

import org.example.ServerPkg.ControllerPkg.GameController;

import java.util.List;

public class UpdatePlayersListMessage extends Message{
    private final List<String> updatedNames;

    public UpdatePlayersListMessage(List<String> names){
        this.updatedNames=names;
    }

    @Override
    public void handle(GameController controller, String playerName){
        getClient().getUserInterface().onUpdatePlayerList(updatedNames);
    }
}
