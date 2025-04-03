package org.example.Server.Controller.States;

import org.example.Server.Model.Points;

import java.util.ArrayList;

public class ActivateEnginesState extends PlayerState {
    ArrayList<Points> Engines;
    ArrayList<Points> Batteries;

    public ActivateEnginesState() {
    }

    public ArrayList<Points> getEngines() {return Engines;}

    public ArrayList<Points> getBatteries(){return Batteries;}
}
