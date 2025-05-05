package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class LandOnAbandonMessage extends Message {
    private boolean bool;

    public LandOnAbandonMessage(boolean bool) {
        this.bool = bool;
    }

    @Override
    public void handle() {
        super.getProxy().landOnAbandon(bool);
    }
}
