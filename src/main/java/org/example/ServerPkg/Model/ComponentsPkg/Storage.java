package org.example.ServerPkg.Model.ComponentsPkg;

import org.example.ServerPkg.Model.Exceptions.RedGoodsNotAllowedException;
import org.example.ServerPkg.Model.Exceptions.StorageFullException;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ForView.GoodsView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Storage extends Components implements Serializable {
    private final Goods[] goodsList;
    private final boolean isSpecial;
    private final int capacity;
    private final int id;

    public Storage(int id, boolean isSpecial, Direction direction, Connector[] connectors, int capacity) {
        super(direction, connectors);
        this.isSpecial = isSpecial;
        this.capacity = capacity;
        this.goodsList = new Goods[capacity];
        Arrays.fill(goodsList, null);
        this.id = id;
    }

    @Override
    public ComponentsView createView(){
        GoodsView[] goodsView = new GoodsView[capacity];

        for (int i = 0; i < goodsList.length; i++) {
            if(goodsList[i]!=null){
                goodsView[i] = new GoodsView(goodsList[i]);
            }
        }
        if(isSpecial){
            return new ComponentsView(getPosX(),getPosY(), getDirection(), getConnectors(), id,"Special Storage", 0 ,0, goodsView, null, null);
        }
        return new ComponentsView(getPosX(),getPosY(), getDirection(), getConnectors(), id,"Storage", 0 ,0, goodsView, null, null);
    }

    public Goods[] getGoods() {
        return goodsList;
    }

    public boolean getIsSpecial() {
        return isSpecial;
    }

    public void removeGood(int i){
        if(goodsList[i]!=null){
            goodsList[i].setStorage(null);
        }
        goodsList[i] = null;
    }

    public void addGood(Goods good) throws RedGoodsNotAllowedException, StorageFullException {
        if (good.getColour() == GoodsColour.RED && !this.isSpecial) {
            throw new RedGoodsNotAllowedException("Red good not allowed in a normal storage!");
        }

        for (int i = 0; i < goodsList.length; i++) {
            if (goodsList[i] == null) {
                goodsList[i] = good;
                good.setStorage(this);
                return;
            }
        }
        throw new StorageFullException("Storage full!");
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void addStorage(ArrayList<Goods> list){
        for (Goods goods : goodsList) {
            if (goods != null) {
                list.add(goods);
            }
        }
    }

    @Override
    public Storage isStorage(){
        return this;
    }
}
