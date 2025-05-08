package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.CardPack.AdventureCard;
import org.example.ServerPkg.Model.CardPack.CannonFire;
import org.example.ServerPkg.Model.CardPack.Meteor;
import org.example.ServerPkg.Model.CardPack.Planet;
import org.example.ServerPkg.Model.ComponentsPack.Goods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdventureCardView implements Serializable{
    private final int id;
    private final int numCredits;
    private final int numAstronauts;
    private final int numGoods;
    private final int cannonPower;
    private List<Meteor> meteorList = new ArrayList<Meteor>();
    private List<PlanetView> planetList = new ArrayList<>();
    private List<GoodsView> goodsList = new ArrayList<>();
    private List<CannonFire> cannonFireList = new ArrayList<>();
    private final String type;
    private final String[] criteria;
    private final String[] penalties;
    private final int lostDays;

    public AdventureCardView(int id, String type, int lostDays, int numCredits, int numAstronauts, int cannonPower, List<Meteor> meteorList, List<PlanetView> planets, List<GoodsView> goods, List<CannonFire> fire, int numLostGoods, String[] criteria, String[] penalties) {
        this.cannonFireList = fire;
        this.id = id;
        this.numCredits = numCredits;
        this.numAstronauts = numAstronauts;
        this.cannonPower = cannonPower;
        this.meteorList = meteorList;
        this.planetList = planets;
        this.numGoods = numLostGoods;
        this.goodsList = goods;
        this.type = type;
        this.criteria = criteria;
        this.penalties = penalties;
        this.lostDays = lostDays;
    }

    public int getId(){
        return id;
    }

    public String getType(){
        return type;
    }

    public int getNumCredits(){
        return numCredits;
    }

    public int getNumAstronauts(){
        return numAstronauts;
    }

    public int getNumGoods(){
        return numGoods;
    }

    public int getCannonPower(){
        return cannonPower;
    }

    public List<Meteor> getMeteorList(){
        return meteorList;
    }

    public List<PlanetView> getPlanetList(){
        return planetList;
    }

    public List<GoodsView> getGoodsList(){
        return goodsList;
    }

    public List<CannonFire> getCannonFireList(){
        return cannonFireList;
    }

    public String[] getCriteria(){
        return criteria;
    }

    public String[] getPenalties(){
        return penalties;
    }

    public int getLostDays(){
        return lostDays;
    }
}
