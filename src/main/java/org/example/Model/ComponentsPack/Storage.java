package org.example.Model.ComponentsPack;

import org.example.Model.Exceptions.RedGoodsNotAllowedException;
import org.example.Model.Exceptions.StorageFullException;

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
                good.setStorage(null);
            }
        }
    }

    /*
    NEL CONTROLLER DOBBIAMO SCRIVERE QUESTO:
    try {
        storage.addGood(redGood);
    } catch (RedGoodsNotAllowedException e) {
        System.out.println("Errore: " + e.getMessage());
    } catch (StorageFullException e) {
        System.out.println("Errore: " + e.getMessage());
    }
    * */
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

    public int getCapacity() {return capacity;}
}
