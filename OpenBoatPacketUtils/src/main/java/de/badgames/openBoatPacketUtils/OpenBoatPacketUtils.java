package de.badgames.openBoatPacketUtils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.badgames.openBoatPacketUtils.packets.OpenBoatPacketBase;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * OpenBoatPacketUtils is a utility class for sending and receiving OpenBoat packets.
 */
@AllArgsConstructor
public class OpenBoatPacketUtils implements PluginMessageListener {

    /**
     * The plugin instance.
     */
    private JavaPlugin plugin;

    /**
     * The map of players and their versions.
     */
    private final HashMap<Player, Integer> playerVersionMap = new HashMap<>();

    /**
     * Register the plugin channels
     */
    public void onEnable() {
        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "openboatutils:settings");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "openboatutils:settings", this);
    }

    /**
     * Unregister the plugin channels
     */
    public void onDisable() {
        //make sure to unregister the registered channels in case of a reload
        plugin.getServer().getMessenger().unregisterOutgoingPluginChannel(plugin);
        plugin.getServer().getMessenger().unregisterIncomingPluginChannel(plugin);
    }

    @Override
    public void onPluginMessageReceived(String channel, @NotNull Player player, byte[] message) {
        if (!channel.equals("openboatutils:settings")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        short id = in.readShort();
        if (id == 0) {
            int version = in.readInt();
            playerVersionMap.put(player, version);
        }
    }

    /**
     * Send a OpenBoatPacket to a player.
     * @param player The player to send the packet to.
     * @param packet The packet to send.
     */
    public void sendPacket(Player player, OpenBoatPacketBase packet) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        packet.writePacket(out);
        player.sendPluginMessage(plugin, "openboatutils:settings", out.toByteArray());
    }

    /**
     * Get the version of a player.
     * @param player The player to get the version of.
     * @return The version of the player. -1 if the player is not registered.
     */
    public int getPlayerVersion(Player player) {
        if (playerVersionMap.containsKey(player)) {
            return playerVersionMap.get(player);
        }

        return -1;
    }
}
