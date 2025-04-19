package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class EndActivateEnginesMessage extends Message {
    @Override
    public void handle(ClientProxy proxy) {
        proxy.endActivateEngines();
    }
}
