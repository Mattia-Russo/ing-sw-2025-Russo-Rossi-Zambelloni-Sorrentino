package org.example.MessagePkg;

public class EndRemoveBestGoodsMessage extends Message{
    @Override
    public void handle() {
        super.getProxy().endRemoveBestGoods();
    }
}
