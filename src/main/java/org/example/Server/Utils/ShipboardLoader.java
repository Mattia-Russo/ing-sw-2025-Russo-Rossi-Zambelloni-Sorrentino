package org.example.Server.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Server.Model.ShipBoard;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class ShipboardLoader {
    private static final String CARDS_JSON_PATH = "/org.example.gc31/cardboard.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static ShipBoard loadLevel2() {
        try {
            List<ShipBoard> boards = loadAllBoards();
            if (boards.size() < 2) {
                throw new IllegalStateException("Need at least 2 board configurations");
            }
            return boards.get(1);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load level 2 shipboard", e);
        }
    }

    public static ShipBoard loadLevel1() {
        try {
            List<ShipBoard> boards = loadAllBoards();
            if (boards.isEmpty()) {
                throw new IllegalStateException("No board configurations found");
            }
            return boards.get(0);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load level 1 shipboard", e);
        }
    }

    private static List<ShipBoard> loadAllBoards() throws Exception {
        try (InputStream is = ShipboardLoader.class.getResourceAsStream(CARDS_JSON_PATH)) {
            if (is == null) {
                throw new RuntimeException("Shipboard file not found at: " + CARDS_JSON_PATH);
            }

            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(is, new TypeReference<List<ShipBoard>>() {});
        }
    }
}