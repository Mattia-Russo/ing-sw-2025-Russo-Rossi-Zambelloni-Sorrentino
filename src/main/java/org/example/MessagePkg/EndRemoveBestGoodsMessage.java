package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;

public class EndRemoveBestGoodsMessage extends Message{
    @Override
    public void handle(ClientProxy proxy) {
        proxy.endRemoveBestGoods();
    }
}
