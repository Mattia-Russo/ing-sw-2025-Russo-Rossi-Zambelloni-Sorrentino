package org.example.ServerPkg.Model.ComponentsPkg;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.ServerPkg.Model.Exceptions.OverloadedCapacityException;
import org.example.ServerPkg.Model.Exceptions.ValueUnderZeroException;
import org.example.ServerPkg.Model.ForView.ComponentsView;
import org.example.ServerPkg.Model.ShipBoard;

public class BatteryStorage extends Components{
    private final int capacity;
    private int quantity;
    private final int id;

    @JsonCreator
    public BatteryStorage(
            @JsonProperty("id") int id,
           @JsonProperty("capacity") int capacity,
           @JsonProperty("direction") Direction direction,
           @JsonProperty("connectors") Connector[] connectors){
        super(direction, connectors);
        this.capacity = capacity;
        this.quantity = capacity;
        this.id = id;
    }

    @Override
    public ComponentsView createView(){
        return new ComponentsView(getDirection(), getConnectors(), id,"BatteryStorage", getQuantity() ,0, null, null, null);
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
