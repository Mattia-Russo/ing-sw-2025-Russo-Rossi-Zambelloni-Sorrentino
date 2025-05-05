package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;

public class EndRemoveBestGoodsMessage extends Message{
    @Override
    public void handle() {
        super.getProxy().endRemoveBestGoods();
    }
}
