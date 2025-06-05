package de.badgames.gameCore.map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

/**
 * Interface for a map in the game.
 */
public interface IMap extends ConfigurationSerializable {
    /**
     * The name of the Map.
     * @return The name of the map.
     */
    String getName();
}