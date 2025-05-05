package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class PingMessage extends Message {
    private final String key;

    public PingMessage(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    @Override
    public void handle() {
        // Non gestita normalmente, gestita direttamente in run()
    }
}