package de.badgames.cloudhelper.modules;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import de.badgames.cloudhelper.ICloudHandler;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.UUID;

/**
 * Handler for TimoCloud.
 */
public class TimoCloudHandler implements ICloudHandler {


    @Override
    public void init() {
        // Nothing to do?
    }

    @Override
    public void setInGame() {
        TimoCloudAPI.getUniversalAPI().getServer(getServerName()).setState("INGAME");
    }

    @Override
    public void setLobby() {
        TimoCloudAPI.getUniversalAPI().getServer(getServerName()).setState("LOBBY");
    }

    @Override
    public String getServerName() {
        var thisInstance = TimoCloudAPI.getUniversalAPI().getThisInstance();
        return thisInstance.getName() + "-" + thisInstance.getId();
    }

    /**
     * TimoCloud does not support changing the MOTD, so this method does nothing.
     * @param motd The MOTD to set.
     */
    @Override
    public void changeMOTD(String motd) {
        // Can't change.
    }

    @Override
    public String getMOTD() {
        return TimoCloudAPI.getBukkitAPI().getThisServer().getMotd();
    }

    @Override
    public boolean sendPlayerToLobby(UUID player) {
        return sendPlayerToGroup(player, "lobby");
    }

    @Override
    public boolean sendPlayerToGroup(UUID player, String group) {
        var servers = TimoCloudAPI.getUniversalAPI().getServerGroup(group).getServers().stream()
                .filter(x -> !x.getState().equalsIgnoreCase("INGAME") && !x.getState().equalsIgnoreCase("FULL"))
                .toList();
        var server = servers.get((int) (Math.random() * servers.size()));
        return TimoCloudAPI.getUniversalAPI().getPlayer(player).sendToServer(server).awaitResponse();
    }

    @Override
    public boolean sendPlayerToServer(UUID player, String serverName) {
        return TimoCloudAPI.getUniversalAPI().getPlayer(player).sendToServer(TimoCloudAPI.getUniversalAPI().getServer(serverName)).awaitResponse();
    }

    /**
     * TimoCloud does not support setting the max players, so this method does nothing.
     * @param maxPlayers The maximum number of players to set.
     */
    @Override
    public void setMaxPlayers(int maxPlayers) {
        // Can't edit.
    }

    /**
     * TimoCloud does not support properties, so this method does nothing.
     * @param key The key of the property to set.
     * @param value The value of the property to set.
     */
    @Override
    public void setProperty(String key, String value) {
        // Can't edit.
    }

    /**
     * TimoCloud does not support properties, so we return an empty string.
     * @param key The key of the property to get.
     * @return An empty string.
     */
    @Override
    public String getProperty(String key) {
        return "";
    }

    @Override
    public int getMaxPlayers() {
        return TimoCloudAPI.getBukkitAPI().getThisServer().getMaxPlayerCount();
    }

    /**
     * The TimoCloud API does not support kicking players directly, so we are accessing the proxy command to kick players. This might not be the best way, but it works.
     * @param player The UUID of the player to kick.
     * @param reason The reason to kick the player.
     */
    @Override
    public void kickPlayer(UUID player, TextComponent reason) {
        var playerObject = TimoCloudAPI.getUniversalAPI().getPlayer(player);
        playerObject.getProxy().executeCommand("kick " + playerObject.getName() + " " + LegacyComponentSerializer.legacySection().serialize(reason));
    }
}
