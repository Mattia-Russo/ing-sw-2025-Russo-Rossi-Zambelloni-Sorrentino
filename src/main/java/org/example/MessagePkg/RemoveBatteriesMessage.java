package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

public class RemoveBatteriesMessage extends Message {
    private Points point;

    public RemoveBatteriesMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.removeBatteries(point);
    }
}
