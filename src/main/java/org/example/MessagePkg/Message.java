package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public void handle(ClientProxy proxy) {}
}
