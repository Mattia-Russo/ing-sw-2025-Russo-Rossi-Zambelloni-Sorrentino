package org.example.Model.ComponentsPack;

import org.example.Model.Exceptions.OverloadedCapacityException;
import org.example.Model.Exceptions.ValueUnderZeroException;
import org.example.Model.ShipBoard;

public class BatteryStorage extends Components{
    private final int capacity;
    private int quantity;

    public BatteryStorage(int capacity, Direction direction, Connector[] connectors){
        super(direction, connectors);
        this.capacity = capacity;
        this.quantity = capacity;
    }

    public int getQuantity(){
        return quantity;
    }

    public int getCapacity(){
        return capacity;
    }

    public void setQuantity(int amount, ShipBoard s) {
        if (quantity + amount < 0) {
            throw new ValueUnderZeroException("Not enough batteries in this Battery Storage!");
        }
        if (amount + quantity > capacity) {
            throw new OverloadedCapacityException("Invalid amount: exceeds capacity.");
        }
        s.setTotalBattery(amount);
        quantity += amount;
    }

    @Override
    public BatteryStorage isBatteryStorage(){
        return this;
    }

    @Override
    public void place(ShipBoard ship){
        ship.setTotalBattery(this.capacity);
    }

    @Override
    public void remove(ShipBoard ship){
        ship.setTotalBattery(-this.quantity);
    }
}
