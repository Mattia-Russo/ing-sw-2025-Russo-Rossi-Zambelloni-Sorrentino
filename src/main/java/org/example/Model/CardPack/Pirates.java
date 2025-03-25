package org.example.Model.CardPack;

import java.util.ArrayList;
import java.util.List;

public class Pirates extends Enemy{
    private int credit;
    private List<CannonFire> cannonFiresList = new ArrayList<CannonFire>();

    public Pirates(int credit, List<CannonFire> cannonFiresList, int cardLevel, int lostDays, int cannonPower) {
        super(cardLevel, lostDays, cannonPower);
        this.cannonFiresList = cannonFiresList;
        this.credit = credit;
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getCredit() {
        return credit;
    }

    public int getLostDays() {return super.getLostDays();}

    public List<CannonFire> getCannonFireList() {
        return cannonFiresList;
    }

    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA credit O prende lista colpi
}
