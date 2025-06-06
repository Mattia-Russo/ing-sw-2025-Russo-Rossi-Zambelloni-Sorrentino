package org.example.ServerPkg.Model.ForView;

import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdventureCardView implements Serializable{
    private final int id;
    private final int numCredits;
    private final int numAstronauts;
    private final int numGoods;
    private final int cannonPower;
    private final List<Meteor> meteorList;
    private final List<PlanetView> planetList;
    private final List<GoodsView> goodsList;
    private final List<CannonFire> cannonFireList;
    private final String type;
    private final String[] criteria;
    private final String[] penalties;
    private final String commands;
    private final int lostDays;

    public AdventureCardView(String commands,int id, String type, int lostDays, int numCredits, int numAstronauts, int cannonPower, List<Meteor> meteorList, List<PlanetView> planets, List<GoodsView> goods, List<CannonFire> fire, int numLostGoods, String[] criteria, String[] penalties) {
        this.cannonFireList = fire != null ? fire : new ArrayList<>();
        this.id = id;
        this.numCredits = numCredits;
        this.numAstronauts = numAstronauts;
        this.cannonPower = cannonPower;
        this.meteorList = meteorList != null ? meteorList : new ArrayList<>();
        this.planetList = planets != null ? planets : new ArrayList<>();
        this.numGoods = numLostGoods;
        this.goodsList = goods != null ? goods : new ArrayList<>();
        this.type = type;
        this.criteria = criteria != null ? criteria : new String[0];
        this.penalties = penalties != null ? penalties : new String[0];
        this.lostDays = lostDays;
        this.commands = commands;
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

    public String getCommands(){return commands;}
}
