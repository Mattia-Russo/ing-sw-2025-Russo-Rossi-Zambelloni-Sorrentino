package org.example.ServerPkg.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ServerPkg.Model.CardPkg.AdventureCard;
import org.example.ServerPkg.Model.CardPkg.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class CardLoader {
    private static final String CARDS_JSON_PATH = "/org.example.gc31/cards.json";
    private static final ObjectMapper mapper = new ObjectMapper();

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

            // Verify we have enough cards
            if (level2.size() < 8 || level1.size() < 4) {
                throw new IllegalStateException("Insufficient cards for pattern (need 8 L2 and 4 L1)");
            }

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
            return filtered.subList(0, Math.min(8, filtered.size()));

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

            // Configure mapper for better error handling
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);

            // Register all subtypes (verify these match your JSON 'cardname' values exactly)
            mapper.registerSubtypes(
                    new NamedType(Slavers.class, "SLAVERS"),
                    new NamedType(Smugglers.class, "SMUGGLERS"),
                    new NamedType(Pirates.class, "PIRATES"),
                    new NamedType(PlanetsCard.class, "PLANETSCARD"),
                    new NamedType(WarZone.class, "WARZONE"),
                    new NamedType(AbandonedStation.class, "ABANDONEDSTATION"),
                    new NamedType(Epidemic.class, "EPIDEMIC"),
                    new NamedType(MeteorCard.class, "METEORCARD"),
                    new NamedType(OpenSpace.class, "OPENSPACE"),
                    new NamedType(AbandonedShip.class, "ABANDONEDSHIP"),
                    new NamedType(Stardust.class, "STARDUST")
            );

            List<AdventureCard> allCards = mapper.readValue(is, new TypeReference<List<AdventureCard>>() {});
            System.out.println("Loaded cards: " + allCards.size());
            return allCards;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse cards JSON", e);
        }
    }
}