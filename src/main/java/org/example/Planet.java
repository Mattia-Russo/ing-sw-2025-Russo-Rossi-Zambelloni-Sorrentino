package org.example;

import java.util.ArrayList;

public class Planet extends PlanetsCard{
    private int planetNumber;
    private boolean isOccupied;
    private List<Goods> goodsList = new ArrayList<Goods>();

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
