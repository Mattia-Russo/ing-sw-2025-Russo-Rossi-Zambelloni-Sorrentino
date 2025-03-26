package org.example.Model.ComponentsPack;

import org.example.Model.Exceptions.OverloadedCapacityException;
import org.example.Model.Exceptions.ValueUnderZeroException;

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

    public void setQuantity(int amount) {
        if (amount <= 0) {
            throw new ValueUnderZeroException("Amount must be greater than zero.");
        }
        if (amount + quantity > capacity) {
            throw new OverloadedCapacityException("Invalid amount: exceeds capacity.");
        }
        quantity += amount;
    }

}
