package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class ExitGameMessage extends Message{
    @Override
    public void handle(ClientProxy proxy) {
        proxy.exitGame();
    }
}
