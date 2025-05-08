package org.example.ServerPkg.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import org.example.ServerPkg.Model.ComponentsPkg.*;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class TileLoader {
    private static final String COMPONENT_JSON_PATH = "/org.example.gc31/tiles.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Components> loadFilteredTiles() {
        try {
            return loadAllTiles().stream()
                    .filter(c-> !"LIFESUPPORTSYSTEM".equalsIgnoreCase(c.getClass().getSimpleName()))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("Failed to load filtered tiles", e);
        }
    }

    public static List<Components> loadAllTiles(){
        try (InputStream is = TileLoader.class.getResourceAsStream(COMPONENT_JSON_PATH)) {

            // Configure mapper for better error handling
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);

            mapper.registerSubtypes(
                    new NamedType(BatteryStorage.class,  "BATTERYSTORAGE"),
                    new NamedType(Cabin.class, "CABIN"),
                    new NamedType(Cannon.class,  "CANNON"),
                    new NamedType(Engine.class, "ENGINE"),
                    new NamedType(LifeSupportSystem.class, "LIFESUPPORTSYSTEM"),
                    new NamedType(Shield.class, "SHIELD"),
                    new NamedType(Storage.class, "STORAGE"),
                    new NamedType(Tubes.class, "TUBES")
            );

            return mapper.readValue(is, new TypeReference<List<Components>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse tiles JSON", e);
        }
    }
}
