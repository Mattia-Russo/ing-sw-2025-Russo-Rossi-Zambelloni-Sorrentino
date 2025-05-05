package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndActivateCannonsMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endActivateCannons();
    }
}
