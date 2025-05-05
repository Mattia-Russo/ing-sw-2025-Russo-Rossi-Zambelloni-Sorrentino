package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import java.io.Serializable;

public abstract class Message implements Serializable {
    private ClientProxy proxy;

    public Message() {
        this.proxy = null;
    }

    public void setProxy(ClientProxy proxy) {
        this.proxy = proxy;
    }

    public ClientProxy getProxy(){
        return this.proxy;
    }

    // Ogni sottoclasse dovrà implementare questo metodo
    public abstract void handle();
}
