package org.example.MessagePkg;

import org.example.ServerPkg.TCPPkg.ClientProxy;
import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class UseBatteriesMessage extends Message {
    private ArrayList<Points> batteries;

    public UseBatteriesMessage(ArrayList<Points> batteries){
        this.batteries=batteries;
    }

    @Override
    public void handle(ClientProxy proxy) {
        proxy.useBatteries(batteries);
    }

}
