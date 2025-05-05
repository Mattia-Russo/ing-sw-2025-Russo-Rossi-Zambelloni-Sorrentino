package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class CreateLobbyMessage extends Message {
    private int numPlayers;
    private int shipboardLevel;
    private int gameMode;

    public CreateLobbyMessage(int numPlayers, int shipboardLevel, int gameMode) {
        this.numPlayers = numPlayers;
        this.shipboardLevel = shipboardLevel;
        this.gameMode = gameMode;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.createLobby(numPlayers, shipboardLevel, gameMode);
    }
}
