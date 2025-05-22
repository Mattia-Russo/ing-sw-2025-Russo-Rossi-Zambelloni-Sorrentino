package org.example.ServerPkg.Utils;

import org.example.ServerPkg.Model.ComponentsPkg.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TileLoader {
    private static final String COMPONENT_JSON_PATH = "/org.example.gc31/JsonPkg/tiles.json";

    public static List<Components> loadFilteredTiles() {
        try {
            return loadAllTiles().stream()
                    .filter(c -> !"LifeSupportSystem".equalsIgnoreCase(c.getClass().getSimpleName()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to load filtered tiles", e);
        }
    }

    public static List<Components> loadAllTiles() {
        List<Components> components = new ArrayList<>();
        try (InputStream is = TileLoader.class.getResourceAsStream(COMPONENT_JSON_PATH)) {
            JSONArray jsonArray = new JSONArray(new JSONTokener(is));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                Components component = parseComponent(json);
                if (component != null) {
                    components.add(component);
                }
            }
            return components;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse tiles JSON", e);
        }
    }

    private static Components parseComponent(JSONObject json) {
        String type = json.getString("type");
        Direction direction = Direction.valueOf(json.getString("direction"));
        Connector[] connectors = parseConnectors(json.getJSONArray("connectors"));
        int id = json.getInt("id");

        switch (type.toUpperCase()) {
            case "CABIN":
                boolean isCentral = json.getBoolean("isCentral");
                return new Cabin(id, isCentral, direction, connectors);
            case "BATTERYSTORAGE":
                return new BatteryStorage(id, json.getInt("capacity"), direction, connectors);
            case "STORAGE":
                boolean isSpecial = json.optBoolean("isSpecial", false);
                return new Storage(id, isSpecial, direction, connectors, json.getInt("capacity"));
            case "ENGINE":
                return new Engine(id,json.getInt("power"), direction, connectors);
            case "CANNON":
                return new Cannon(id, json.getInt("power"), direction, connectors);
            case "LIFESUPPORTSYSTEM":
                AlienColour colour = AlienColour.valueOf(json.getString("colour"));
                return new LifeSupportSystem(id,  colour, direction, connectors);
            case "SHIELD":
                Direction direction2 = Direction.valueOf(json.getString("direction2"));
                return new Shield(id, direction, connectors, direction2);
            case "TUBES":
                return new Tubes(id, direction, connectors);
            default:
                return null;
        }
    }

    private static Connector[] parseConnectors(JSONArray jsonArray) {
        Connector[] connectors = new Connector[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            connectors[i] = Connector.valueOf(jsonArray.getString(i));
        }
        return connectors;
    }
}
