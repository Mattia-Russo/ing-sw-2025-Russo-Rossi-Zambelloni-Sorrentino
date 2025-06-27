package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.ComponentsPkg.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ComponentsViewTest extends TestCase {

    public void testGetPosX() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(posX, card.getPosX());
    }

    public void testGetPosY() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(posY, card.getPosY());
    }

    public void testGetDirection() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(direction, card.getDirection());
    }

    public void testGetConnectors() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(connectors, card.getConnectors());
    }

    public void testGetId() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(id, card.getId());
    }

    public void testGetType() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(type, card.getType());
    }

    public void testGetAlienColour() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(alienColour, card.getAlienColour());
    }

    public void testGetNumBattery() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(numBattery, card.getNumBattery());
    }

    public void testGetNumAstronauts() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(numAstronauts, card.getNumAstronauts());
    }

    public void testGetGoods() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );

        assertEquals(goods, card.getGoods());
    }

    public void testGetShieldedDirections() {
        int id = 42;
        int posX= 6;
        int posY = 6;
        Direction direction = Direction.NORTH;
        Connector[] connectors = new Connector[]{Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL, Connector.UNIVERSAL};
        int numBattery = 2;
        int numAstronauts = 2;
        GoodsView goodsView1 = new GoodsView(new Goods(GoodsColour.YELLOW));
        GoodsView goodsView2 = new GoodsView(new Goods(GoodsColour.GREEN));
        GoodsView[] goods = new GoodsView[] { goodsView1, goodsView2 };
        Direction shieldedDirection = Direction.NORTH;
        AlienColour alienColour= AlienColour.BROWN;
        String type = "TestType";

        ComponentsView card = new ComponentsView(
                posX,posY, direction,connectors,id,type,numBattery,numAstronauts,goods,shieldedDirection,alienColour
        );
        assertTrue(Arrays.equals(new Direction[]{Direction.NORTH, Direction.NORTH}, card.getShieldedDirections()));
    }
}