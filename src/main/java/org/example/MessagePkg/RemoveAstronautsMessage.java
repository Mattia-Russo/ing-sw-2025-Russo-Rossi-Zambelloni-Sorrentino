package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

public class RemoveAstronautsMessage extends Message {
    private Points point;

    public void RemoveAstronautsMessage(Points point) {
        this.point = point;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.removeAstronauts(point);
    }
}
