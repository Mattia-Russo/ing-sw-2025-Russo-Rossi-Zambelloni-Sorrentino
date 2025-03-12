package org.example.CardPack;

import org.example.Player;

public abstract class AbandonedShip extends LoseDays{
    private final int cardLevel;
    private int Credits;
    private int Astronauts;

    @Override
    public int getCardLevel() {
        return cardLevel;
    }



    @Override
    public void playCard(Player p) {
        p.changeCredits(Credits);
    }

    public int getCredits(){
        return Credits;
    }

    public int getNumAstronauts(){
        return Astronauts;
    }

}
