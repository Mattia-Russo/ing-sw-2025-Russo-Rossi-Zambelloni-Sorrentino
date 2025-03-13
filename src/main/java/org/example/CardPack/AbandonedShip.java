package org.example.CardPack;

import org.example.Player;

public class AbandonedShip extends AdventureCard {
    private int Credits;
    private int Astronauts;

    public AbandonedShip(int CardLevel, int lostDays, int Credits, int Astronauts) {
        super(CardLevel, lostDays);
        this.Credits = Credits;
        this.Astronauts = Astronauts;
    }

    public int getCardLevel(){
        return super.getCardLevel();
    }

    public int getLostDays(){
        return super.getLostDays();
    }

    @Override
    public void playCard(Player p) {
        p.changeCredits(Credits);
    }

    public int getNumAstronauts(){
        return Astronauts;
    }

}
