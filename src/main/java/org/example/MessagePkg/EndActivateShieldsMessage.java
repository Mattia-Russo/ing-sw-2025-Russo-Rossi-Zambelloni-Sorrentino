package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndActivateShieldsMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endActivateShields();
    }
}
