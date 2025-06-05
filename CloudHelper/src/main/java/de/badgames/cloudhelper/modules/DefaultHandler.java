package de.badgames.cloudhelper.modules;

import de.badgames.cloudhelper.ICloudHandler;
import net.kyori.adventure.text.TextComponent;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * CloudHandler for anything that isn't Bukkit based or has any other Helper available.
 */
public class DefaultHandler implements ICloudHandler {

    HashMap<String, String> properties = new HashMap<>();

    @Override
    public void init() {
        // Can't change.
        Logger.getGlobal().warning("You are using the DefaultHandler!");
    }

    @Override
    public void setInGame() {
        // Can't change.
        Logger.getGlobal().warning("You set the State to In Game!");
    }

    @Override
    public String getServerName() {
        return "Default";
    }

    @Override
    public void changeMOTD(String motd) {
        // Can't change.
        Logger.getGlobal().warning("You set the MOTD to: " + motd + "!");
    }

    @Override
    public String getMOTD() {
        return "";
    }

    @Override
    public boolean sendPlayerToLobby(UUID player) {
        // Can't change.
        Logger.getGlobal().warning("You sent a player to the lobby!");
        return false;
    }

    @Override
    public boolean sendPlayerToGroup(UUID player, String group) {
        // Can't change.
        Logger.getGlobal().warning("You sent a player to the group " + group + "!");
        return false;
    }

    @Override
    public boolean sendPlayerToServer(UUID player, String serverName) {
        // No can do.
        Logger.getGlobal().warning("You sent a player to the " + serverName + "!");
        return false;
    }

    @Override
    public void setProperty(String key, String value) {
        properties.put(key, value);
    }

    @Override
    public String getProperty(String key) {
        return properties.get(key);
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        // Can't change.
        Logger.getGlobal().warning("You set the max players to: " + maxPlayers + "!");
    }

    @Override
    public int getMaxPlayers() {
        return -1;
    }

    @Override
    public void kickPlayer(UUID playerUUID, TextComponent reason) {
        Logger.getGlobal().warning("You kicked player " + playerUUID + "!");
    }
}
