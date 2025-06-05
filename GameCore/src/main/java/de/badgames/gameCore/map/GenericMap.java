package de.badgames.gameCore.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a map.
 */
@Getter
@ToString
@AllArgsConstructor
public class GenericMap implements IMap {

    /**
     * The name of the map.
     */
    protected String name;

    /**
     * The name of the world.
     */
    protected String worldName;

    /**
     * The author of the map.
     */
    protected String author;

    public GenericMap(Map<String, Object> serialize) {
        this((String) serialize.get("name"), (String) serialize.get("world"), (String) serialize.getOrDefault("author", "Unknown"));
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> serialize = new HashMap<>();

        serialize.put("name", name);
        serialize.put("world", worldName);
        serialize.put("author", author);

        return serialize;
    }
}