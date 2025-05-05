package org.example.MessagePkg;

public class EndChangeGoodsState extends Message {
    @Override
    public void handle() {
        super.getProxy().endChangeGoodsState();
    }
}
