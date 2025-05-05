package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class JoinLobbyMessage extends Message{
    @Override
    public void handle() {
        super.getProxy().joinLobby();
    }
}
