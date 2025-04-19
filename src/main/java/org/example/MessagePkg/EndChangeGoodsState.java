package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class EndChangeGoodsState extends Message {
    @Override
    public void handle(ClientProxy proxy) {
        proxy.endChangeGoodsState();
    }
}
