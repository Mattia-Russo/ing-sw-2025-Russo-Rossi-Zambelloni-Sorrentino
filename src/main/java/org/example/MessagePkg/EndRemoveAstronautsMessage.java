package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class EndRemoveAstronautsMessage extends Message {
    @Override
    public void handle(ClientProxy proxy) {
        proxy.endRemoveAstronauts();
    }
}
