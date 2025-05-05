package org.example.MessagePkg;

import org.example.ServerPkg.Model.Points;

import java.util.ArrayList;

public class ActivateEnginesMessage extends Message {
    private ArrayList<Points> engines;

    public ActivateEnginesMessage(ArrayList<Points> engines){
        this.engines=engines;
    }

    @Override
    public void handle() {
        super.getProxy().activateEngines(engines);
    }

}
