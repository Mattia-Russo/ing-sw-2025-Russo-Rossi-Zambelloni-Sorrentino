package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndRemoveAstronautsMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endRemoveAstronauts();
    }
}
