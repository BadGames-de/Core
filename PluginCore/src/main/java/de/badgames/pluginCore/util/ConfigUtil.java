package de.badgames.pluginCore.util;

import com.cryptomorin.xseries.XItemStack;
import de.badgames.pluginCore.PluginCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigUtil {

    public static final HashMap<String, Location> locationsCache = new HashMap<>();

    /**
     * Check if a value exists in the config file.
     * @param path Path to the value in the config file.
     * @return True if the value exists, false if not.
     */
    public static boolean exists(String path) {
        return exists(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Check if a value exists in the config file.
     * @param path Path to the value in the config file.
     * @return True if the value exists, false if not.
     */
    public static boolean exists(FileConfiguration config, String path) {
        return config.isSet(path);
    }

    /**
     * Set a value in the config file.
     * @param path Path to the value in the config file.
     * @param value The value to save.
     */
    public static void set(String path, Object value) {
        set(PluginCore.getInstance().getPlugin().getConfig(), null, path, value);
    }

    /**
     * Set a value in the config file.
     * @param path Path to the value in the config file.
     * @param value The value to save.
     */
    public static void set(FileConfiguration config, File file, String path, Object value) {
        config.set(path, value);
        if (file == null) {
            PluginCore.getInstance().getPlugin().saveConfig();
        } else {
            try {
                config.save(file);
            } catch (IOException e) {
                PluginCore.getInstance().getPlugin().getLogger().severe("Could not save config file: " + file.getName());
            }
        }
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static Object get(String path) {
        return get(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static Object get(FileConfiguration config, String path) {
        return config.get(path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static <T> T get(String path, Class<T> clazz) {
        return get(PluginCore.getInstance().getPlugin().getConfig(), path, clazz);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static <T> T get(FileConfiguration config, String path, Class<T> clazz) {
        return config.getObject(path, clazz);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static <T> List<T> getList(String path) {
        return getList(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static <T> List<T> getList(FileConfiguration config, String path) {
        return (List<T>) config.getList(path, new ArrayList<T>());
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static List<String> getStringList(String path) {
        return getStringList(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static List<String> getStringList(FileConfiguration config, String path) {
        return config.getStringList(path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static String getString(String path) {
        return getString(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static String getString(FileConfiguration config, String path) {
        return config.getString(path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static int getInt(String path) {
        return getInt(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static int getInt(FileConfiguration config, String path) {
        return config.getInt(path);
    }


    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static double getDouble(String path) {
        return getDouble(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static double getDouble(FileConfiguration config, String path) {
        return config.getDouble(path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static boolean getBoolean(String path) {
        return getBoolean(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get a value from the config file.
     * @param path Path to the value in the config file.
     * @return The value from the config file.
     */
    public static boolean getBoolean(FileConfiguration config, String path) {
        return config.getBoolean(path);
    }

    /**
     * Get an item from the config file.
     * @param path Path to the item in the config file.
     * @return The item from the config file.
     */
    public static ItemStack getItem(String path) {
        return getItem(PluginCore.getInstance().getPlugin().getConfig(), path);
    }

    /**
     * Get an item from the config file.
     * @param path Path to the item in the config file.
     * @return The item from the config file.
     */
    public static ItemStack getItem(FileConfiguration config, String path) {
        return XItemStack.deserialize(config.getConfigurationSection(path));
    }

    /**
     * Set an item in the config file.
     * @param path Path to the item in the config file.
     * @param item The item to save.
     */
    public static void setItem(String path, ItemStack item) {
        setItem(PluginCore.getInstance().getPlugin().getConfig(), null, path, item);
    }

    /**
     * Set an item in the config file.
     * @param path Path to the item in the config file.
     * @param item The item to save.
     */
    public static void setItem(FileConfiguration config, File file, String path, ItemStack item) {
        set(config, file, path, XItemStack.serialize(item));
    }

    /**
     * Set a location in the config file.
     *
     * @param locationName Name of the location in the config file.
     * @param loc          The location to save.
     */
    public static void setLocation(String locationName, Location loc) {
        setLocation(PluginCore.getInstance().getPlugin().getConfig(), null, locationName, loc);
    }

    /**
     * Set a location in the config file.
     *
     * @param locationName Name of the location in the config file.
     * @param loc          The location to save.
     */
    public static void setLocation(FileConfiguration config, File file, String locationName, Location loc) {
        set(config, file, locationName, loc);
        locationsCache.put(locationName, loc);
    }

    /**
     * Get a location by name in the vampires world
     *
     * @param locationName Name of the location in the config file
     * @return The location in the specified world
     */
    public static Location getLocation(String locationName) {
        return getLocation(PluginCore.getInstance().getPlugin().getConfig(), locationName);
    }

    /**
     * Get a location by name in the vampires world
     *
     * @param locationName Name of the location in the config file
     * @return The location in the specified world
     */
    public static Location getLocation(FileConfiguration config, String locationName) {

        // Send a warning when the location doesn't exist yet
        if (!exists(config, locationName)) {
            Bukkit.broadcast(Component.text("Location " + locationName + " not found!").color(NamedTextColor.RED));
            return null;
        }

        // Check if the location has already been cached
        if (locationsCache.containsKey(locationName)) {
            return locationsCache.get(locationName).clone();
        }

        // Make the location
        Location location = config.getLocation(locationName);

        // Cache the location for use in the future
        locationsCache.put(locationName, location);
        return location.clone();
    }

    /**
     * Get a location by name in the vampires world
     *
     * @param locationName Name of the location in the config file
     * @return The location in the specified world
     */
    public static Location getLocation(String locationName, String overwriteWorld) {
        return getLocation(PluginCore.getInstance().getPlugin().getConfig(), locationName, overwriteWorld);
    }

    /**
     * Get a location by name in the vampires world
     *
     * @param locationName Name of the location in the config file
     * @return The location in the specified world
     */
    public static Location getLocation(FileConfiguration config, String locationName, String overwriteWorld) {

        Location location = getLocation(config, locationName);

        if (location != null && overwriteWorld != null && !overwriteWorld.isBlank()) {
            location.setWorld(Bukkit.getWorld(overwriteWorld));
        }

        return location;
    }
}
