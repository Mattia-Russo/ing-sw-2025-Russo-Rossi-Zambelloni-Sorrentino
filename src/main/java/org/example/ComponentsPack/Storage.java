package org.example.ComponentsPack;

import java.util.Arrays;

public class Storage extends Components {
    private Goods[] goodsList = new Goods[3];
    private final boolean isSpecial;
    private final int capacity;

    public Storage(boolean isSpecial, Direction direction, Connector[] connectors, int capacity) {
        super(direction, connectors);
        this.isSpecial = isSpecial;
        this.capacity = capacity;
        this.goodsList = new Goods[capacity];
        Arrays.fill(goodsList, null);
    }

    public Goods[] getGoods() {
        return goodsList;
    }

    public boolean getIsSpecial() {
        return isSpecial;
    }

    public void removeGood(Goods good){
        for (int i = 0; i < goodsList.length; i++) {
            if(goodsList[i] == good){
                goodsList[i] = null;
            }
        }
    }

    public void addGood(Goods good){
        for (int i = 0; i < goodsList.length; i++) {
            if(goodsList[i] == null){
                goodsList[i] = good;
                return;
            }
        }
    }
}
