package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class ExitGameMessage extends Message{
    @Override
    public void handle() {
        super.getProxy().exitGame();
    }
}
