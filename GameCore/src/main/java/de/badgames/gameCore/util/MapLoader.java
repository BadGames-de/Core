package de.badgames.gameCore.util;

import de.badgames.gameCore.map.GenericMap;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This class is used to load maps from the maps folder.
 * @param <T> The type of the map
 */
@Getter
public class MapLoader<T extends GenericMap> {

    /**
     * The map configs.
     */
    private final HashMap<T, YamlConfiguration> mapConfigs = new HashMap<>();

    /**
     * The map loader constructor.
     * @param plugin The plugin instance
     * @param clazz The class of the map
     */
    public MapLoader(JavaPlugin plugin, Class<T> clazz) {
        loadAllMaps(plugin, clazz);
    }

    /**
     * Loads all maps from the maps folder.
     * @param plugin The plugin instance
     * @param clazz The class of the map
     */
    protected void loadAllMaps(JavaPlugin plugin, Class<T> clazz) {
        File mapsFolder = new File(plugin.getDataFolder(), "maps");

        if (!mapsFolder.exists()) {
            plugin.getLogger().info("Maps folder does not exist, creating it...");
            if (!mapsFolder.mkdirs()) {
                plugin.getLogger().severe("Failed to create maps folder!");
                throw new NullPointerException("Maps folder is null");
            }
        }

        File[] mapFiles = mapsFolder.listFiles();

        if (mapFiles == null || mapFiles.length == 0) {
            throw new NullPointerException("No maps found in maps folder");
        }

        for (File file : mapFiles) {
            if (file.isDirectory())
                continue;

            if (!file.getName().endsWith(".yml"))
                continue;

            try {
                YamlConfiguration mapYaml = YamlConfiguration.loadConfiguration(file);

                if (!mapYaml.isSet("map"))
                    throw new NullPointerException("Invalid YAML, missing map section.");

                T mapObject = mapYaml.getSerializable("map", clazz);

                if (mapObject == null)
                    throw new NullPointerException("Map object is null");

                mapConfigs.put(mapObject, mapYaml);
            } catch (Exception exception) {
                plugin.getLogger().severe("Error while loading map " + file.getName());
                plugin.getLogger().severe(exception.getMessage());
            }
        }
    }

    /**
     * Gets the map config for the given key.
     * @param key The key of the map
     * @return The map config
     */
    public YamlConfiguration getMap(T key) {
        return mapConfigs.get(key);
    }

    /**
     * Gets the map config for the given key.
     * @param key The key of the map
     * @return The map config
     */
    public YamlConfiguration getMapByName(String key) {
        return mapConfigs.keySet().stream()
                .filter(map -> map.getName().equalsIgnoreCase(key))
                .findFirst()
                .map(mapConfigs::get)
                .orElse(null);
    }

    /**
     * Get the amount of maps.
     * @return The amount of maps
     */
    public int getMapCount() {
        return mapConfigs.size();
    }

    /**
     * Get all maps.
     * @return The list of all maps
     */
    public List<T> getAllMaps() {
        return mapConfigs.keySet().stream().toList();
    }

    /**
     * Get a random map from the list of maps.
     * @return A random map
     */
    public T getRandomMap() {
        return getAllMaps().get(new Random().nextInt(getMapCount()));
    }
}
