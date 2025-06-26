package org.example.MessagePkg.ToClient;

import org.example.MessagePkg.Message;
import org.example.ServerPkg.ControllerPkg.GameController;

import java.util.List;

public class LobbyJoinedMessage extends Message {
    List<String> names;
    int numPlayer;
    int shipBoardLevel;
    int gameMode;

    public LobbyJoinedMessage(int numPlayer, int shipBoardLevel, int gameMode, List<String> names){
        this.numPlayer=numPlayer;
        this.shipBoardLevel=shipBoardLevel;
        this.gameMode=gameMode;
        this.names=names;
    }

    @Override
    public void handle(GameController controller, String playerName){
        super.getClient().getUserInterface().onLobbyJoined(names, numPlayer, shipBoardLevel, gameMode);
    }
}
