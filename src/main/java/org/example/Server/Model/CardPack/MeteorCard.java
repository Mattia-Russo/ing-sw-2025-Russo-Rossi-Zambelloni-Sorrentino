package org.example.Server.Model.CardPack;

import java.util.ArrayList;
import java.util.List;

public class MeteorCard extends AdventureCard {
    private List<Meteor> meteorList = new ArrayList<Meteor>();

    public MeteorCard(int cardLevel, int lostDays, List<Meteor> meteorList){
        super(cardLevel, lostDays);
        this.meteorList=meteorList;
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }
}
