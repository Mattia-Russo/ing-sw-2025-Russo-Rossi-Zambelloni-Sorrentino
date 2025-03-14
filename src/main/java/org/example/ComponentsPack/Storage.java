package org.example.ComponentsPack;

public class Storage extends Components {
    private Goods[] goodsList = new Goods[3];
    private final boolean isSpecial;

    public Storage(boolean isSpecial, Direction direction, Connector[] connectors) {
        super(direction, connectors);
        this.isSpecial = isSpecial;
        goodsList = null;
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
