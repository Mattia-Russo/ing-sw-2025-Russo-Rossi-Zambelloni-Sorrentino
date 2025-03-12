package org.example.ComponentsPack;

public class BatteryStorage extends Components{
    private final int capacity;
    private int quantity;

    public BatteryStorage(int capacity, Direction direction, Connector[] connectors){
        super(direction, connectors);
        this.capacity = capacity;
        this.quantity = 0;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setQuantity(int amount){
        int newQuantity = quantity + amount;
        if(newQuantity >= capacity) {
            quantity = capacity;
        } else if(newQuantity < 0){
            // se ho 2 batterie ma ne chiede 3, gli consumo i 2 o no?
        } else {
            quantity = newQuantity;
        }
    }
}
