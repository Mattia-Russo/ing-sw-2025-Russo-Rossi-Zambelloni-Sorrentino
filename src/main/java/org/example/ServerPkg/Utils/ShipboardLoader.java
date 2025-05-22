package org.example.ServerPkg.Utils;

import org.example.ServerPkg.Model.ShipBoard;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShipboardLoader {
    private static final String CARDS_JSON_PATH = "/org.example/JsonPkg/cardboard.json";

    public static ShipBoard loadLevel2() {
        try {
            List<ShipBoard> boards = loadAllBoards();
            if (boards.size() < 2) {
                throw new IllegalStateException("Need at least 2 board configurations");
            }
            return boards.get(1); // Secondo elemento per livello 2
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
            return boards.get(0); // Primo elemento per livello 1
        } catch (Exception e) {
            throw new RuntimeException("Failed to load level 1 shipboard", e);
        }
    }

    private static List<ShipBoard> loadAllBoards() throws Exception {
        try (InputStream is = ShipboardLoader.class.getResourceAsStream(CARDS_JSON_PATH)) {
            if (is == null) {
                throw new RuntimeException("Shipboard file not found at: " + CARDS_JSON_PATH);
            }

            JSONArray jsonArray = new JSONArray(new JSONTokener(is));
            List<ShipBoard> boards = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                boards.add(parseShipBoard(json));
            }
            return boards;
        }
    }

    private static ShipBoard parseShipBoard(JSONObject json) {
        // Parsing della matrice delle posizioni disponibili
        JSONArray matrixJson = json.getJSONArray("availablePositionMatrix");
        boolean[][] availablePositions = new boolean[matrixJson.length()][];

        for (int i = 0; i < matrixJson.length(); i++) {
            JSONArray row = matrixJson.getJSONArray(i);
            availablePositions[i] = new boolean[row.length()];
            for (int j = 0; j < row.length(); j++) {
                availablePositions[i][j] = row.getBoolean(j);
            }
        }
        return new ShipBoard(availablePositions,json.getInt("matrixWidth"), json.getInt("matrixHeight"));
    }
}