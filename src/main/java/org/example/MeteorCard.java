package org.example;

import java.util.ArrayList;
import java.util.List;

public class MeteorCard implements AdventureCard{
    private final CardEnum cardEnum;
    private final int cardLevel;
    private final List<Meteor> meteorList = new ArrayList<Meteor>();

    public MeteorCard(){
        this.cardEnum = CardEnum.MeteorCard;
        this.cardLevel = 123;
    }

    @Override
    public CardEnum getCardEnum() {
        return cardEnum;
    }

    @Override
    public int getCardLevel() {
        return cardLevel;
    }

    public List<Meteor> getMeteorList() {
        return meteorList;
    }

    @Override
    public void playCard(ArrayList<Player> players) {
        int[] rowOrCol = new int[meteorList.size()];
        for(int i=0; i<meteorList.size(); i++){
            rowOrCol[i] = players.get(0).shoot();
        }

        for (Player p : players) {
            
        }

    }
}
