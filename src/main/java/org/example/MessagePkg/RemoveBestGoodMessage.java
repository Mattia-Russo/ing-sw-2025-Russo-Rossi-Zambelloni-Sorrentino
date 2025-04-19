package org.example.MessagePkg;

import org.example.ServerPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

public class RemoveBestGoodMessage extends Message {
    private Points point;
    private int numGood;

    public RemoveBestGoodMessage(Points point, int numGood) {
        this.point = point;
        this.numGood = numGood;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.removeBestGood(point, numGood);
    }
}
