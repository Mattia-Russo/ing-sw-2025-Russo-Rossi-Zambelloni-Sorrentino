package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class StartGameMessage extends Message {
    @Override
    public void handle(ClientProxy proxy) {
        proxy.startGame();
    }
}
