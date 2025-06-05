package de.badgames.cloudhelper.modules;

import de.badgames.cloudhelper.ICloudHandler;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * CloudHandler for Bukkit
 */
public class BukkitHandler implements ICloudHandler {

    HashMap<String, String> properties = new HashMap<>();

    @Override
    public void init() {
        // Can't change.
        Bukkit.getLogger().warning("You are using the BukkitHandler!");
    }

    @Override
    public void setInGame() {
        // Can't change.
        Bukkit.getLogger().warning("You set the state to In Game!");
    }

    @Override
    public String getServerName() {
        return Bukkit.getServerName();
    }

    @Override
    public void changeMOTD(String motd) {
        // Can't change.
        Bukkit.getLogger().warning("You set the MOTD to: " + motd + "!");
    }

    @Override
    public String getMOTD() {
        return Bukkit.getMotd();
    }

    @Override
    public boolean sendPlayerToLobby(UUID player) {
        // Can't change.
        Bukkit.getLogger().warning("You sent a player to the lobby!");
        return true;
    }

    @Override
    public boolean sendPlayerToGroup(UUID player, String group) {
        // Can't change.
        Bukkit.getLogger().warning("You sent a player to the group " + group + "!");
        return true;
    }

    @Override
    public boolean sendPlayerToServer(UUID player, String serverName) {
        // No can do.
        Bukkit.getLogger().warning("You sent a player to the " + serverName + "!");
        return true;
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
        Bukkit.getLogger().warning("You set the max players to: " + maxPlayers + "!");
    }

    @Override
    public int getMaxPlayers() {
        return Bukkit.getMaxPlayers();
    }

    @Override
    public void kickPlayer(UUID playerUUID, TextComponent reason) {
        Player player = Bukkit.getPlayer(playerUUID);
        if (player != null)
            player.kickPlayer(reason.content());
    }
}
