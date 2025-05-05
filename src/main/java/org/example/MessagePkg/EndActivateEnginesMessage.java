package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndActivateEnginesMessage extends Message {
    @Override
    public void handle() {
        super.getProxy().endActivateEngines();
    }
}
