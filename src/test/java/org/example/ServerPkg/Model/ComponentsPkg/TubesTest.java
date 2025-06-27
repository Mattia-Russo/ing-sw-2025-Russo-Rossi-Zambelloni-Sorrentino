package org.example.ServerPkg.Model.ComponentsPkg;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.ForView.ComponentsView;

public class TubesTest extends TestCase {

    public void testCreateView() {
        int id = 3;
        Direction direction = Direction.WEST;
        Connector[] connectors = {Connector.SINGLE, Connector.DOUBLE, Connector.EMPTY, Connector.UNIVERSAL};

        Tubes tubes = new Tubes(id, direction, connectors);
        tubes.setPosition(6, 10); // x, y

        ComponentsView view = tubes.createView();

        assertEquals(6, view.getPosX());
        assertEquals(10, view.getPosY());
        assertEquals(direction, view.getDirection());
        assertEquals(id, view.getId());
        assertEquals("Tubes", view.getType());
        assertEquals(0, view.getNumBattery());
        assertEquals(0, view.getNumAstronauts());
        assertNull(view.getAlienColour());
        assertNull(view.getGoods());
        assertEquals(connectors.length, view.getConnectors().length);
        for (int i = 0; i < connectors.length; i++) {
            assertEquals(connectors[i], view.getConnectors()[i]);
        }
    }
}