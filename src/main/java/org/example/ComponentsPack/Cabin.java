package org.example.ComponentsPack;

public class Cabin extends Components {
    private int quantity;
    private int capacity;
    public Cabin(TileType tileType, Direction direction, Connector[] connectors, int quantity, int capacity) {
        super(tileType,direction,connectors);
        this.quantity = quantity;
        this.capacity = capacity;
    }

    public int getQuantity() {
        return quantity;
    }
    public int getCapacity() {
        return capacity;
    }
    public int changeQuantity(int amount) {
        int newQuantity = quantity + amount;
        if(newQuantity<0) {
            throw new IllegalArgumentException("Not enough quantity to remove");
        }
        quantity = newQuantity;
        return newQuantity;
    }
}
