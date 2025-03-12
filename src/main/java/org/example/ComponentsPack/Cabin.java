package org.example.ComponentsPack;

public class Cabin extends Components {
    private int quantity;
    private int capacity;

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
