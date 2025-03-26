package org.example.Model.CardPack;

import org.example.Model.ComponentsPack.Goods;

import java.util.ArrayList;
import java.util.List;

public class Smugglers extends Enemy{
    private List<Goods> goodsWinList = new ArrayList<Goods>();
    private int goodsLose;

    public Smugglers(int cardLevel, int lostDays, int cannonPower, int goodsLose, List<Goods> goodsWinList) {
        super(cardLevel,  lostDays, cannonPower);
        this.goodsLose = goodsLose;
        this.goodsWinList=goodsWinList;
    }

    public int getCannonPower() {
        return super.getCannonPower();
    }

    public int getCardLevel() {
        return super.getCardLevel();
    }

    public int getLostDays() {return super.getLostDays();}

    public List<Goods> getGoodsWin() {
        return goodsWinList;
    }

    public int getGoodsLost() {
        return goodsLose;
    }

    //CONTROLLER CALCOLA POTENZA DI FUOCO USANDO UN METODO SUL MODEL , CHIAMA GETCANNONPOWER,
    // CONFRONTA POI O CHIAMA GOODSWIN O LOSE E CAMBIA LE RISORSE
}
