package de.badgames.pluginCore;

import de.badgames.pluginCore.inventory.Screens;
import de.badgames.pluginCore.listener.InventoryListener;
import lombok.Getter;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class PluginCore {

    @Getter
    private static PluginCore instance;

    @Getter
    private JavaPlugin plugin;

    @Getter
    private Screens screens;

    @Getter
    private static final NamedTextColor primaryColor = NamedTextColor.LIGHT_PURPLE;
    @Getter
    private static final NamedTextColor secondaryColor = NamedTextColor.DARK_PURPLE;

    public static void init(JavaPlugin plugin) {
        instance = new PluginCore();
        instance.screens = new Screens();
        instance.plugin = plugin;

        plugin.getLogger().info(" ");
        plugin.getLogger().info("Starting PluginCore..");

        plugin.getLogger().info("Initializing listeners..");
        Listener[] listeners = new Listener[] { new InventoryListener() };
        Arrays.stream(listeners).forEach(listener -> plugin.getServer().getPluginManager().registerEvents(listener, plugin));

        plugin.getLogger().info(" ");
    }

    public static void unload() {
        instance.screens.unload();
        instance.plugin.getLogger().info("PluginCore unloaded successfully.");
        instance = null;
    }

    public static String getMiniMessageGradient() {
        return "<gradient:light_purple:dark_purple>";
    }
}
