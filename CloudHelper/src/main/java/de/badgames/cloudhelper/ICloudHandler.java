package de.badgames.cloudhelper;

import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

public interface ICloudHandler {

    /**
     * Initializes the CloudHandler.
     */
    void init();

    /**
     * Set the server to "Ingame" so the Cloud can start another server.
     */
    void setInGame();

    /**
     * Set the server to "Lobby"
     * This method should be called when the server is ready to accept players.
     */
    void setLobby();

    /**
     * Gets the name of the server.
     *
     * @return The name of the server.
     */
    String getServerName();

    /**
     * Set the server to "Lobby" so the Cloud can start another server.
     *
     * @param motd The MOTD to set.
     */
    void changeMOTD(String motd);

    /**
     * Get the current MOTD of the server.
     *
     * @return The current MOTD of the server.
     */
    String getMOTD();

    /**
     * Sends a player to the lobby server.
     *
     * @param player The UUID of the player to send.
     */
    boolean sendPlayerToLobby(UUID player);

    /**
     * Send a player to a specific group.
     *
     * @param player The UUID of the player to send.
     * @param group  The name of the group to send the player to.
     * @return true if the player was sent successfully, false otherwise.
     */
    boolean sendPlayerToGroup(UUID player, String group);

    /**
     * Sends a player to a specific server.
     *
     * @param player     The UUID of the player to send.
     * @param serverName The name of the server to send the player to.
     * @return true if the player was sent successfully, false otherwise.
     */
    boolean sendPlayerToServer(UUID player, String serverName);

    /**
     * Sets the max players of the server.
     *
     * @param maxPlayers The max players to set.
     */
    void setMaxPlayers(int maxPlayers);

    /**
     * Set a property to the server.
     *
     * @param key   The key of the property to add.
     * @param value The value of the property to add.
     */
    void setProperty(String key, String value);

    /**
     * Get a property of the server.
     *
     * @param key The key of the property to get.
     * @return The value of the property, or null if it does not exist.
     */
    String getProperty(String key);

    /**
     * Get the max players of the server.
     *
     * @return The max players of the server.
     */
    int getMaxPlayers();

    /**
     * Kick a player form the network.
     *
     * @param player The UUID of the player to kick.
     * @param reason The reason to kick the player.
     */
    void kickPlayer(UUID player, TextComponent reason);
}
