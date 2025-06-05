package de.badgames.cloudhelper;

import de.badgames.cloudhelper.modules.*;

import java.util.Map;
import java.util.logging.Logger;

/**
 * CloudHelper is a utility class that provides a way to interact with different cloud environments.
 */
public class CloudHelper {

    private static ICloudHandler cloudHandler;

    protected static final Map<String, ICloudHandler> cloudHandlerClassMap =
            Map.of("eu.cloudnetservice.modules.bridge.BridgeServiceHelper", new CloudNETHandler(),
                    "eu.thesimplecloud.api.ICloudAPI", new SimpleCloudV2Handler(),
                    "org.bukkit.Bukkit", new BukkitHandler());

    /**
     * <p>Initializes the CloudHelper and sets the cloud handler.<br>
     * This method should be called in the onEnable() method of your plugin.<br>
     * And currently supports these 4 environments:</p>
     * <ul>
     *  <li>Default (no cloud, not Bukkit based(like Velocity or Bungeecord))</li>
     *  <li>Bukkit (no cloud)</li>
     *  <li>CloudNet V4</li>
     *  <li>SimpleCloud V2</li>
     *  <li>SimpleCloud V3</li>
     * </ul>
     */
    public static void init() {
        if (cloudHandler != null) {
            Logger.getGlobal().warning("CloudHelper is already initialized!");
            return;
        }
        cloudHandlerClassMap.forEach((classPackage, handler) -> {
            if (cloudHandler != null) return;
            try {
                Class.forName(classPackage);
                Logger.getGlobal().info("Found cloud handler: " + handler.getClass().getSimpleName());
                cloudHandler = handler;
            } catch (Exception ignore) {
            }
        });

        if (cloudHandler == null) {
            if (System.getenv("SIMPLECLOUD_UNIQUE_ID") != null) {
                cloudHandler = new SimpleCloudV3Handler();
            } else {
                cloudHandler = new DefaultHandler();
            }
        }

        Logger.getGlobal().info("Using cloud handler: " + cloudHandler.getClass().getSimpleName());
        cloudHandler.init();
    }

    /**
     * Returns the current cloud handler.
     * @return The current cloud handler.
     */
    public static ICloudHandler getCloudHandler() {
        return cloudHandler;
    }
}
