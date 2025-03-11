package org.example.CardPack;

import org.example.ComponentsPack.Goods;

import java.util.ArrayList;
import java.util.List;

public class Planet extends PlanetsCard{
    private int planetNumber;
    private boolean isOccupied;
    private final List<Goods> goodsList = new ArrayList<Goods>();

    public boolean getIsOccupied() {
        return isOccupied;
    }

    public void changeIsOccupied(boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public int getPlanetNumber() {
        return planetNumber;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }
}
