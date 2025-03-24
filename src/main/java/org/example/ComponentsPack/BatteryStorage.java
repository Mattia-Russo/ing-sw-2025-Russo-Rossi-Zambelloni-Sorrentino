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
        try{
            if(amount >= capacity || amount + quantity >= capacity){
                throw new IllegalArgumentException("Invalid Amount");
            }
            quantity += amount;
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
