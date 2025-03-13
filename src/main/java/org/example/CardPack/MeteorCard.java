package org.example.CardPack;

import org.example.ComponentsPack.Components;
import org.example.ComponentsPack.Connector;
import org.example.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class MeteorCard extends AdventureCard {
    private final List<Meteor> meteorList = new ArrayList<Meteor>();

    public MeteorCard(int cardLevel, int lostDays){
        super(cardLevel, lostDays);
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
   
    //controllo se scudi in quella direzione (getIfShielded(direction) in shipboard)
    //controllo se connettore esposto (getIfExposed(direction, component) in Components)
    //controllo se cannoni singoli puntati (getIfSingleCannon(direction, numOrCol) in Shipboard
    // se false: getIfDoubleCannon(direction, numOrCol)  poi attende decisione utente)

}
