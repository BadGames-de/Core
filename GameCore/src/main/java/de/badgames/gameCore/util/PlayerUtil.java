package de.badgames.gameCore.util;

import com.cryptomorin.xseries.XSound;
import de.badgames.dbm.SQLWorker;
import de.badgames.dbm.entities.Achievement;
import de.badgames.dbm.entities.Stats;
import de.badgames.dbm.entities.Title;
import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * Utility class for player-related operations.
 */
@Slf4j
public class PlayerUtil {

    /**
     * SQLWorker instance for database operations.
     */
    private static SQLWorker sqlWorker;

    /**
     * Initialize the PlayerUtil.
     *
     * @param sqlWorker SQLWorker instance to be used for database operations.
     */
    public static void init(SQLWorker sqlWorker) {
        PlayerUtil.sqlWorker = sqlWorker;
    }

    /**
     * Get the first block below the player.
     *
     * @param player The player to check.
     * @return The first block below the player.
     */
    public static Location getFirstBlockBelow(Player player) {
        Location location = player.getLocation();
        var locationCalc = getFirstBlock(location, 0);
        return locationCalc != null ? locationCalc : location;
    }

    /**
     * Recursively get the first block below the given location.
     *
     * @param location  The location to check.
     * @param loopCount The current loop count. (limit of 15)
     * @return The first block below the location, or null if not found.
     */
    public static Location getFirstBlock(Location location, int loopCount) {
        World world = location.getWorld();
        Block below = world.getBlockAt(location);

        if (!below.isCollidable() && loopCount < 15) {
            Location loc = below.getLocation().add(0, -1, 0);
            loc = getFirstBlock(loc, ++loopCount);

            if (loc != null) {
                return loc;
            }

            return location;
        }

        return null;
    }

    /**
     * Calculate the distance between two players. (Ignoring Y)
     *
     * @param p1 Player one.
     * @param p2 Player two.
     * @return the distance.
     */
    public static double getDistance(Player p1, Player p2) {
        return getDistance(p1, p2, true);
    }

    /**
     * Calculate the distance between two players.
     *
     * @param p1      Player one.
     * @param p2      Player two.
     * @param ignoreY If the Y position should be ignored.
     * @return the distance.
     */
    public static double getDistance(Player p1, Player p2, boolean ignoreY) {
        return getDistance(p1.getLocation(), p2.getLocation(), ignoreY, true);
    }

    /**
     * Calculate the distance between two locations. (Ignoring Y)
     *
     * @param loc1 Location one.
     * @param loc2 Location two.
     * @return the distance.
     */
    public static double getDistance(Location loc1, Location loc2) {
        return getDistance(loc1, loc2, true, true);
    }

    /**
     * Calculate the distance between two locations.
     *
     * @param loc1    Location one.
     * @param loc2    Location two.
     * @param ignoreY If the Y position should be ignored.
     * @param useSqrt If Math.sqrt should be called to get the actual distance, if you're doing a comparison not using sqrt saves time.
     * @return the distance.
     */
    public static double getDistance(Location loc1, Location loc2, boolean ignoreY, boolean useSqrt) {
        double dx = getDifference(loc1.getX(), loc2.getX());
        double dz = getDifference(loc1.getZ(), loc2.getZ());
        double dy = getDifference(loc1.getY(), loc2.getY());

        double distance = dx * dx + (!ignoreY ? dy * dy : 0) + dz * dz;

        return useSqrt ? Math.sqrt(distance) : distance;
    }

    /**
     * Calculate the difference of the two variables.
     * @param x variable one
     * @param x1 variable two
     * @return the difference.
     */
    public static double getDifference(double x, double x1) {
        return Math.max(x, x1) - Math.min(x, x1);
    }

    /**
     * Check if a location is within two other locations.
     * @param pos1 The first position.
     * @param pos2 The second position.
     * @param locationToCheck The location to check.
     * @return true, if its within Pos1 and Pos2.
     */
    public static boolean isWithinRegion(Location pos1, Location pos2, Location locationToCheck) {
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());

        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        double toCheckX = locationToCheck.getX();
        double toCheckY = locationToCheck.getY();
        double toCheckZ = locationToCheck.getZ();

        return minY <= toCheckY && minX <= toCheckX && minZ <= toCheckZ && toCheckX <= maxX && toCheckZ <= maxZ && maxY <= toCheckY;
    }

    /**
     * Gets the metadata of a player.
     * @param instance the owning Plugin.
     * @param player the Player.
     * @param key they Metadata Key.
     * @return the Metadata or null.
     */
    public static MetadataValue getMetaData(JavaPlugin instance, Player player, String key) {
        return player.getMetadata(key).stream().filter(x -> x.getOwningPlugin() == instance).findFirst().orElse(null);
    }

    /**
     * Add coins to a player.
     *
     * @param player The player to add coins to.
     * @param amount The amount of coins to add.
     */
    public static void addCoins(Player player, long amount) {
        if (amount < 0) {
            log.warn("Amount must be positive. {} is not positive.", amount);
        }

        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
            return;
        }
        sqlWorker.giveCoins(player.getUniqueId().toString(), amount, 1000);
    }

    /**
     * Add stats to a player.
     *
     * @param player     The player to add stats to.
     * @param identifier The identifier of the stats.
     * @param amount     The amount of stats to add.
     */
    public static void addStats(Player player, String identifier, long amount) {
        if (amount < 0) {
            log.warn("Amount must be positive. {} is not positive.", amount);
        }

        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
            return;
        }

        sqlWorker.addStat(player.getUniqueId().toString(), identifier, amount);
    }

    /**
     * Get the stats of a player.
     *
     * @param player The player to get the stats from.
     * @return The list of stats of the player.
     */
    public static List<Stats> getStats(Player player) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        return sqlWorker.getStats(player.getUniqueId().toString());
    }

    /**
     * Get the stats of a player with a specific identifier.
     *
     * @param player     The player to get the stats from.
     * @param startsWith The identifier to filter the stats.
     * @return The list of stats of the player.
     */
    public static List<Stats> getStats(String player, String startsWith) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        return sqlWorker.getStats(player, startsWith);
    }

    /**
     * Get the stats of a player with a specific identifier.
     *
     * @param player     The player to get the stats from.
     * @param startsWith The identifier to filter the stats.
     * @return The list of stats of the player.
     */
    public static List<Stats> getStats(Player player, String startsWith) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        return sqlWorker.getStats(player.getUniqueId().toString(), startsWith);
    }

    /**
     * Get a specific stat of a player.
     *
     * @param player     The player to get the stat from.
     * @param identifier The identifier of the stat.
     * @return The stat of the player.
     */
    public static Stats getStat(Player player, String identifier) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        return sqlWorker.getStat(player.getUniqueId().toString(), identifier);
    }

    /**
     * Add an achievement to a player.
     *
     * @param player     The player to add the achievement to.
     * @param identifier The identifier of the achievement.
     * @param announce   Whether to announce the achievement.
     */
    public static void addAchievement(Player player, String identifier, boolean announce) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
            return;
        }

        Achievement achievement = sqlWorker.addAchievement(player.getUniqueId().toString(), identifier);

        if (achievement == null) {
            return;
        }

        if (announce) {
            TagResolver resolver = TagResolver.builder()
                    .resolver(TagResolver.standard())
                    .tag("achievement", Tag.inserting(Component.text(achievement.getDisplayName(), NamedTextColor.RED)))
                    .tag("achievement_description", Tag.inserting(Component.text(achievement.getDescription(), NamedTextColor.LIGHT_PURPLE)))
                    .build();

            MiniMessage serializer = MiniMessage.builder()
                    .tags(resolver).build();

            Component messageToSend = serializer.deserialize("<dark_purple><obf>A</obf></dark_purple><gradient:light_purple:dark_purple>→ <bold>Achievement unlocked: <hover:show_text:'<achievement_description>'><achievement></hover></bold> ←</gradient><light_purple><obf>A</obf></light_purple>");
            player.sendMessage(messageToSend);
            XSound.BLOCK_AMETHYST_CLUSTER_BREAK.play(player, 0.5f, 1f);
        }
    }

    /**
     * Get all achievements.
     *
     * @param like The string to filter the achievements.
     * @return The list of achievements.
     */
    public static List<Achievement> getAchievements(String like) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        return sqlWorker.getAchievements(like);
    }

    /**
     * Check if a player has an achievement.
     *
     * @param player      The player to check.
     * @param achievement The achievement to check.
     * @return True if the player has the achievement, false otherwise.
     */
    public static boolean hasAchievement(Player player, String achievement) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        return sqlWorker.hasAchievement(player.getUniqueId().toString(), achievement);
    }

    public static void addTitle(Player player, String titleIdentifier, boolean announce) {
        if (sqlWorker == null) {
            log.error("SQLWorker not initialized.");
        }

        Title title = sqlWorker.addTitle(player.getUniqueId().toString(), titleIdentifier);

        if (title == null) {
            return;
        }

        if (announce) {
            TagResolver resolver = TagResolver.builder()
                    .resolver(TagResolver.standard())
                    .tag("title", Tag.inserting(Component.text(title.getDisplayName(), NamedTextColor.RED)))
                    .build();

            MiniMessage serializer = MiniMessage.builder()
                    .tags(resolver).build();

            Component messageToSend = serializer.deserialize("<dark_purple><obf>A</obf></dark_purple><gradient:light_purple:dark_purple>→ <bold>Title unlocked: <title></bold> ←</gradient><light_purple><obf>A</obf></light_purple>");
            player.sendMessage(messageToSend);
            XSound.BLOCK_AMETHYST_CLUSTER_BREAK.play(player, 0.5f, 1f);
        }
    }
}
