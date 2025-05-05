package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class StartGameMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().startGame();
    }
}
