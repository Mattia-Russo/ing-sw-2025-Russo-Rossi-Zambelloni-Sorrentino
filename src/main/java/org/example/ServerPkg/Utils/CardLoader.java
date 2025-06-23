package org.example.ServerPkg.Utils;

import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.CardPkg.*;
import org.example.ServerPkg.Model.ComponentsPkg.Cannon;
import org.example.ServerPkg.Model.ComponentsPkg.Direction;
import org.example.ServerPkg.Model.ComponentsPkg.Goods;
import org.example.ServerPkg.Model.ComponentsPkg.GoodsColour;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class CardLoader {
    private static final String CARDS_JSON_PATH = "/org.example/JsonPkg/cards.json";

    public static List<AdventureCard> loadPatternDeck() {
        try {
            List<AdventureCard> allCards = loadAllCards();

            // Separate and shuffle levels
            List<AdventureCard> level2 = allCards.stream()
                    .filter(c -> c.getCardLevel() == 2)
                    .collect(Collectors.toList());

            List<AdventureCard> level1 = allCards.stream()
                    .filter(c -> c.getCardLevel() == 1)
                    .collect(Collectors.toList());

            Collections.shuffle(level2);
            Collections.shuffle(level1);

            // Build 2-2-1 pattern
            List<AdventureCard> deck = new ArrayList<>(12);
            for (int cycle = 0; cycle < 4; cycle++) {
                deck.add(level2.get(cycle*2));
                deck.add(level2.get(cycle*2 + 1));
                deck.add(level1.get(cycle));
            }

            return deck;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to build pattern deck", e);
        }
    }

    public static List<AdventureCard> loadFilteredRandomCards(Set<String> allowedTypes) {
        try {
            List<AdventureCard> filtered = loadAllCards().stream()
                    .filter(card -> card.getCardLevel() == 1)
                    .filter(card -> allowedTypes.contains(
                            card.getClass().getSimpleName().toUpperCase()))
                    .collect(Collectors.toList());

            Collections.shuffle(filtered);
            return filtered;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load filtered cards", e);
        }
    }

    private static List<AdventureCard> loadAllCards() throws Exception {
        try (InputStream is = CardLoader.class.getResourceAsStream(CARDS_JSON_PATH)) {
            if (is == null) {
                throw new RuntimeException("Cards file not found at: " + CARDS_JSON_PATH +
                        "\nMake sure the file exists in src/main/resources" + CARDS_JSON_PATH);
            }

            JSONArray jsonArray = new JSONArray(new JSONTokener(is));
            List<AdventureCard> allCards = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                AdventureCard card = parseCard(json);
                if (card != null) {
                    allCards.add(card);
                }
            }

            return allCards;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse cards JSON", e);
        }
    }

    private static AdventureCard parseCard(JSONObject json) {
        String cardName = json.getString("cardname");
        int cardLevel = json.getInt("cardLevel");
        int lostDays = json.getInt("lostDays");
        int id = json.getInt("id");

        switch (cardName.toUpperCase()) {
            case "SLAVERS":
                return new Slavers(id, cardLevel,lostDays, json.getInt("cannonPower"), json.getInt("numAstronauts"), json.getInt("credits"));
            case "SMUGGLERS":
                return new Smugglers(id, cardLevel, lostDays, json.getInt("cannonPower"), json.getInt("goodsLose"), parseGoodsList(json.getJSONArray("goodsWinList")));
            case "PIRATES":
                return new Pirates(id, json.getInt("credit"), parseCannonFire(json.getJSONArray("cannonFiresList")), cardLevel, lostDays, json.getInt("cannonPower"));
            case "PLANETSCARD":
                return new PlanetsCard(id, cardLevel, lostDays, parsePlanet(json.getJSONArray("planets")));
            case "WARZONE":
                JSONArray jsonCriteria = json.getJSONArray("criteria");
                String[] criteria = new String[jsonCriteria.length()];
                JSONArray jsonPenalties = json.getJSONArray("penalties");
                String[] penalties = new String[jsonPenalties.length()];
                for(int i = 0; i < jsonCriteria.length(); i++){
                    criteria[i] = jsonCriteria.getString(i);
                    penalties[i] = jsonPenalties.getString(i);
                }
                return new WarZone(id, cardLevel, lostDays, json.getInt("numAstronauts"), json.getInt("numGoods"), parseCannonFire(json.getJSONArray("cannonFireList")), penalties, criteria);
            case "ABANDONEDSTATION":
                JSONArray goodsArray = json.getJSONArray("goods");
                Goods[] goods = new Goods[goodsArray.length()];
                parseGoodsArray(goodsArray, goods);
                return new AbandonedStation(id, cardLevel, lostDays, json.getInt("numAstronauts"), goods);
            case "EPIDEMIC":
                return new Epidemic(id, cardLevel, lostDays);
            case "METEORCARD":
                return new MeteorCard(id, cardLevel, lostDays, parseMeteor(json.getJSONArray("meteorList")));
            case "OPENSPACE":
                return new OpenSpace(id, cardLevel, lostDays);
            case "ABANDONEDSHIP":
                return new AbandonedShip(id, cardLevel, lostDays, json.getInt("credits"), json.getInt("numAstronauts"));
            case "STARDUST":
                return new Stardust(id, cardLevel, lostDays);
            default:
                return null;
        }
    }

    private static void parseGoodsArray(JSONArray goodsArray, Goods[] goods) {
        for(int i = 0; i < goodsArray.length(); i++){
            JSONObject goodObj = goodsArray.getJSONObject(i);
            String colourStr = goodObj.getString("colour");
            GoodsColour colour = GoodsColour.valueOf(colourStr.toUpperCase());
            goods[i] = new Goods(colour);
        }
    }

    private static List<Goods> parseGoodsList(JSONArray goodsArray) {
        List<Goods> goodsList = new ArrayList<>();

        for (int i = 0; i < goodsArray.length(); i++) {
            JSONObject goodObj = goodsArray.getJSONObject(i);
            String colourStr = goodObj.getString("colour");
            GoodsColour colour = GoodsColour.valueOf(colourStr.toUpperCase());
            goodsList.add(new Goods(colour));
        }

        return goodsList;
    }

    private static List<CannonFire> parseCannonFire(JSONArray fireArray) {
        List<CannonFire> fire = new ArrayList<>();

        for (int i = 0; i < fireArray.length(); i++) {
            JSONObject goodObj = fireArray.getJSONObject(i);
            int type = goodObj.getInt("type");
            Direction direction = Direction.valueOf(goodObj.getString("direction"));
            fire.add(new CannonFire(type, direction));
        }
        return fire;
    }

    private static ArrayList<Planet> parsePlanet(JSONArray jsonPlanets) {
        ArrayList<Planet> planets = new ArrayList<>();

        for (int i = 0; i < jsonPlanets.length(); i++) {
            JSONObject goodObj = jsonPlanets.getJSONObject(i);
            int number = goodObj.getInt("planetNumber");
            JSONArray goods = goodObj.getJSONArray("goods");
            Goods[] goodsArray = new Goods[goods.length()];
            parseGoodsArray(goods, goodsArray);
            planets.add(new Planet(number, goodsArray));
        }
        return planets;
    }

    private static List<Meteor> parseMeteor(JSONArray meteorArray) {
        List<Meteor> meteors = new ArrayList<>();

        for (int i = 0; i < meteorArray.length(); i++) {
            JSONObject goodObj = meteorArray.getJSONObject(i);
            int type = goodObj.getInt("type");
            Direction direction = Direction.valueOf(goodObj.getString("direction"));
            meteors.add(new Meteor(type, direction));
        }
        return meteors;
    }
}