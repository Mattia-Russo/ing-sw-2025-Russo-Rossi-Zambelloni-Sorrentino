package org.example.ComponentsPack;

import junit.framework.TestCase;
import org.example.Model.ComponentsPack.BatteryStorage;
import org.example.Model.ComponentsPack.Connector;
import org.example.Model.ComponentsPack.Direction;

public class BatteryStorageTest extends TestCase {

    public void testGetQuantity() {
        BatteryStorage b = new BatteryStorage(3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(0, b.getQuantity());
    }

    public void testGetCapacity() {
        BatteryStorage b = new BatteryStorage(3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(3, b.getCapacity());

    }

    public void testSetQuantity() {
        BatteryStorage b = new BatteryStorage(3, Direction.NORTH, new Connector[]{Connector.UNIVERSAL, Connector.EMPTY, Connector.SINGLE, Connector.DOUBLE});
        assertEquals(0, b.getQuantity());
        b.setQuantity(2);
        assertEquals(2, b.getQuantity());
    }
}