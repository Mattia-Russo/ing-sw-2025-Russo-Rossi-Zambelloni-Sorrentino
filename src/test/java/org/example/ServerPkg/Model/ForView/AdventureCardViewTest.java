package org.example.ServerPkg.Model.ForView;

import junit.framework.TestCase;
import org.example.ServerPkg.Model.CardPkg.CannonFire;
import org.example.ServerPkg.Model.CardPkg.Meteor;
import org.example.ServerPkg.Model.CardPkg.Planet;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;

import java.util.Collections;
import java.util.List;

public class AdventureCardViewTest extends TestCase {

    public void testGetId() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(id, card.getId());
    }

    public void testGetType() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(type, card.getType());
    }

    public void testGetNumCredits() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(numCredits, card.getNumCredits());
    }

    public void testGetNumAstronauts() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(numAstronauts, card.getNumAstronauts());
    }

    public void testGetNumGoods() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(numGoods, card.getNumGoods());
    }

    public void testGetCannonPower() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(cannonPower, card.getCannonPower());
    }

    public void testGetMeteorList() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(meteorList, card.getMeteorList());
    }

    public void testGetPlanetList() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(planetList, card.getPlanetList());
    }

    public void testGetGoodsList() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(goodsList, card.getGoodsList());
    }

    public void testGetCannonFireList() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(fireList, card.getCannonFireList());
    }

    public void testGetCriteria() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(criteria, card.getCriteria());
    }

    public void testGetPenalties() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(penalties, card.getPenalties());
    }

    public void testGetLostDays() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(lostDays, card.getLostDays());
    }

    public void testGetCommands() {
        int id = 42;
        int numCredits = 15;
        int numAstronauts = 2;
        int numGoods = 3;
        int cannonPower = 1;
        int lostDays = 2;
        String type = "TestType";
        String commands = "do_this";
        String[] criteria = new String[]{"a", "b"};
        String[] penalties = new String[]{"c", "d"};

        // Usa oggetti reali
        Meteor meteor = new Meteor(0, Direction.NORTH);
        List<Meteor> meteorList = Collections.singletonList(meteor);

        PlanetView planetView = new PlanetView(new Planet(1,new Goods[]{new Goods(GoodsColour.GREEN)}));
        List<PlanetView> planetList = Collections.singletonList(planetView);

        GoodsView goodsView = new GoodsView(new Goods(GoodsColour.GREEN));
        List<GoodsView> goodsList = Collections.singletonList(goodsView);

        CannonFire cannonFire = new CannonFire(1, Direction.NORTH);
        List<CannonFire> fireList = Collections.singletonList(cannonFire);

        AdventureCardView card = new AdventureCardView(
                commands, id, type, lostDays, numCredits, numAstronauts, cannonPower,
                meteorList, planetList, goodsList, fireList, numGoods, criteria, penalties
        );

        assertEquals(commands, card.getCommands());
    }
}