package org.example.ComponentsPack;

public class Storage extends Components {
    private Goods[] goodsList = new Goods[3];
    private boolean isSpecial;

    public Goods[] getGoodsList() {
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

    public void add(Goods good){
        if(goodsList[goodsList.length-1] == null){
            for (int i = 0; i < goodsList.length-1; i++) {
                if(goodsList[i] == null){
                    goodsList[i] = good;
                    return;
                }
            }
        }
    }
}
