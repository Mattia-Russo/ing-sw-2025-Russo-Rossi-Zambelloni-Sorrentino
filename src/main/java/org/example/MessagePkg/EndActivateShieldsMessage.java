package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndActivateShieldsMessage extends Message {
    @Override
    public void handle(ClientProxy proxy) {
        proxy.endActivateShields();
    }
}
