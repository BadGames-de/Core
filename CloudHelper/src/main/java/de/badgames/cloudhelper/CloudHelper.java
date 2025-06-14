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
                    "cloud.timo.TimoCloud.api.TimoCloudAPI", new TimoCloudHandler());

    /**
     * <p>Initializes the CloudHelper and sets the cloud handler.<br>
     * This method should be called in the onEnable() method of your plugin.<br>
     * And currently supports these 4 environments:</p>
     * <ul>
     *  <li>Default (no cloud, not Bukkit based(like Velocity or Bungeecord))</li>
     *  <li>Bukkit (no cloud)</li>
     *  <li>TimoCloud - VERY slow updates and currently unstable</li>
     *  <li>CloudNet V4 - Slow updates</li>
     *  <li>SimpleCloud V2 - EoS (dropped support by official devs, so I won't deal with it.)</li>
     *  <li>SimpleCloud V3 - Fast updates</li>
     * </ul>
     */
    public static void init() {
        if (cloudHandler != null) {
            Logger.getGlobal().warning("CloudHelper is already initialized!");
            return;
        }

        if (System.getenv("SIMPLECLOUD_UNIQUE_ID") != null) {
            cloudHandler = new SimpleCloudV3Handler();
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
            try {
                Class.forName("org.bukkit.Bukkit");
                cloudHandler = new BukkitHandler();
            } catch (Exception ignore) {
                cloudHandler = new DefaultHandler();
            }
        }

        Logger.getGlobal().info("Using cloud handler: " + cloudHandler.getClass().getSimpleName());
        cloudHandler.init();
    }

    /**
     * Returns the current cloud handler.
     *
     * @return The current cloud handler.
     */
    public static ICloudHandler getCloudHandler() {
        return cloudHandler;
    }
}
