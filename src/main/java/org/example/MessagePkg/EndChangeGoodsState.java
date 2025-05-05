package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndChangeGoodsState extends Message {
    @Override
    public void handle(ClientProxy proxy) {
        proxy.endChangeGoodsState();
    }
}
