package de.badgames.gameCore.team;

import com.cryptomorin.xseries.XMaterial;
import de.badgames.prefix.api.PrefixApi;
import de.badgames.prefix.api.PrefixGroup;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a team in the game.
 */
@Slf4j
@Getter
public abstract class Team implements ConfigurationSerializable, PrefixGroup {

    /**
     * The name of the team.
     */
    protected final String name;

    /**
     * The display name of the team.
     */
    protected final String displayName;

    /**
     * The color of the team name in the tab list.
     */
    protected final String namedTextColor;

    /**
     * The color of the team name in the chat.
     */
    protected final String chatColor;

    /**
     * The material of the team icon.
     */
    protected final XMaterial material;

    /**
     * The list of players in the team.
     */
    protected final ArrayList<UUID> players = new ArrayList<>();

    /**
     * The priority of the team.
     */
    @Setter
    protected int priority = 0;

    /**
     * Constructs a new team.
     * @param name the name of the team.
     * @param displayName the display name of the team.
     * @param namedTextColor the color of the team name in the tab list.
     * @param chatColor the color of the team name in the chat.
     * @param material the material of the team icon.
     */
    public Team(String name, String displayName, String namedTextColor, String chatColor, XMaterial material) {
        this.name = name;
        this.displayName = displayName;
        this.namedTextColor = namedTextColor;
        this.chatColor = chatColor;
        this.material = material;
    }

    /**
     * Constructs a new team based on YAML configuration.
     * @param map the YAML configuration.
     */
    public Team(Map<String, Object> map) {
        this((String) map.get("name"), (String) map.get("display"), (String) map.get("namedTextColor"), (String) map.get("cc"), XMaterial.valueOf((String) map.get("material")));
    }

    /**
     * Checks if the team contains a player.
     * @param player the player to check.
     * @return true if the team contains the player, false otherwise.
     */
    public boolean containsPlayer(@NotNull Player player) {
        return containsPlayer(player.getUniqueId());
    }

    /**
     * Checks if the team contains a player.
     * @param uuid the UUID of the player to check.
     * @return true if the team contains the player, false otherwise.
     */
    @Override
    public boolean containsPlayer(@NotNull UUID uuid) {
        return players.contains(uuid);
    }

    /**
     * Asynchronously checks if the team contains a player.
     * @param uuid the UUID of the player to check.
     * @return a CompletableFuture that will be completed with true if the team contains the player, false otherwise.
     */
    @Override
    public @NotNull CompletableFuture<Boolean> containsPlayerFuture(@NotNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> containsPlayer(uuid));
    }

    /**
     * Gets the color of the team.
     * @return the color of the team.
     */
    @Override
    public @NotNull NamedTextColor getColor() {
        return getConvertedColor();
    }

    /**
     * Get the prefix of the team.
     * @return the prefix of the team.
     */
    @Override
    public @NotNull Component getPrefix() {
        return Component.text(displayName, NamedTextColor.WHITE).appendSpace();
    }

    /**
     * Get the suffix of the team.
     * @return the suffix of the team.
     */
    @Override
    public @NotNull Component getSuffix() {
        return Component.text("",getConvertedColor());
    }

    /**
     * Add a player to the team.
     * @param player the player to add.
     */
    public void addPlayer(Player player) {
        addPlayer(player.getUniqueId());
    }

    /**
     * Add a player to the team.
     * @param player the UUID of the player to add.
     */
    public void addPlayer(UUID player) {
        players.add(player);
    }

    /**
     * Remove a player from the team.
     * @param player the player to remove.
     */
    public void removePlayer(Player player) {
        removePlayer(player.getUniqueId());
    }

    /**
     * Remove a player from the team.
     * @param player the UUID of the player to remove.
     */
    public void removePlayer(UUID player) {
        players.remove(player);
    }

    /**
     * Get the list of players in the team.
     * @return the list of players in the team.
     */
    public List<Player> getPlayers() {
        List<Player> playerList = new ArrayList<>();
        for (UUID uuid : players) {
            Player player = Bukkit.getServer().getPlayer(uuid);
            if (player != null) {
                playerList.add(player);
            }
        }
        return playerList;
    }

    /**
     * Get the list of players in the team.
     * @return the list of players in the team.
     */
    public ArrayList<String> playerLore() {
        ArrayList<String> lore = new ArrayList<>();

        if (players.isEmpty()) {
            lore.add("§7Click to join!");
        } else {
            for (Player player : getPlayers()) {
                lore.add("§r§7- " + chatColor.substring(0, 2) + player.getName());
            }
        }

        return lore;
    }

    /**
     * Check if the team is joinable.
     * @return true if the team is joinable, false otherwise.
     */
    public abstract boolean isJoinable();

    /**
     * Give the kit to a player.
     * @param player the player to give the kit to.
     * @param teleport true if the player should be teleported, false otherwise.
     */
    public void giveKit(Player player, boolean teleport) {
    }

    /**
     * Tick the team.
     */
    public void tick() {
    }

    /**
     * Send a start message to the team.
     */
    public void sendStartMessage() {
    }

    /**
     * Update the prefix of the team.
     * @param prefixesApi the PrefixApi instance.
     * @param player the player to update the prefix for.
     */
    public void updatePrefix(PrefixApi prefixesApi, Player player) {
        if (prefixesApi != null) {
            prefixesApi.setWholeName(player, this);
        }
    }

    /**
     * Get the PrefixApi instance.
     * @return the PrefixApi instance.
     */
    public abstract PrefixApi getPrefixApi();

    /**
     * Join the team.
     * @param player the player to join.
     */
    public void join(Player player) {
        addPlayer(player);
        updatePrefix(getPrefixApi(), player);
    }

    /**
     * Leave the team.
     * @param player the player to leave.
     */
    public void leave(Player player) {
        removePlayer(player);
        updatePrefix(getPrefixApi(), player);
    }

    /**
     * Handle the win condition for the team.
     */
    public void handleWin() {
    }

    /**
     * Serialize the team to a map for YAML.
     * @return the serialized map.
     */
    @Override
    public @NotNull Map<String, Object> serialize() {
        return Map.of(
                "name", name,
                "display", displayName,
                "namedTextColor", namedTextColor,
                "cc", chatColor,
                "material", material
        );
    }

    /**
     * Get color based on the namedTextColor.
     * @return the converted NamedTextColor.
     */
    public NamedTextColor getConvertedColor() {
        return NamedTextColor.NAMES.valueOr(namedTextColor.toLowerCase(), NamedTextColor.WHITE);
    }

    /**
     * Check if two teams are equal.
     * @param obj the object to compare.
     * @return true if the teams are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Team team) {
            return team.getName().equals(name) || team.getMaterial() == material;
        }
        return super.equals(obj);
    }
}
