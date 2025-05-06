package org.example.MessagePkg;

import org.example.ServerPkg.ConnectionsPkg.TCPPkg.ClientProxy;
import java.io.Serializable;

public class Message implements Serializable {
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
    public void handle(){};
}
