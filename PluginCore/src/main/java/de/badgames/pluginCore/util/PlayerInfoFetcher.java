package de.badgames.pluginCore.util;

import org.bukkit.Bukkit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper-class for getting UUIDs of players.
 */
public final class PlayerInfoFetcher {

    private static final String MOJANG_UUID_URL = "https://api.mojang.com/users"
            + "/profiles/minecraft/";

    private static final String MOJANG_NAME_URL = "https://sessionserver.mojang.com"
            + "/session/minecraft/profile/";

    private static final String MINETOOLS_UUID_URL = "https://api.minetools.eu/uuid/";
    private static final String MINETOOLS_NAME_URL = "https://api.minetools.eu/uuid/";

    private static final Pattern UUID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"(.*?)\"");
    private static final Pattern NAME_PATTERN = Pattern.compile(",\\s*\"name\"\\s*:\\s*\"(.*?)\"");

    private PlayerInfoFetcher() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the UUID of the searched player.
     *
     * @param name The name of the player.
     * @return The UUID of the given player.
     */
    public static UUID getUUID(String name) {
        String output;

        try {
            output = callURL(MOJANG_UUID_URL + name);
        } catch (Exception e) {
            // If Mojang API fails, try Minetools
            try {
                output = callURL(MINETOOLS_UUID_URL + name);
            } catch (Exception ex) {
                Logger.getGlobal().warning("Failed to fetch player UUID for name: " + name);
                return null;
            }
        }

        if (output.isBlank()) {
            return null;
        }
        Matcher m = UUID_PATTERN.matcher(output);
        if (m.find()) {
            return UUID.fromString(insertDashes(m.group(1)));
        }
        return null;
    }

    public static BufferedImage parseHead(UUID uuid) {
        String request = String.format("https://crafatar.com/avatars/%s?size=64", uuid.toString());
        try {
            return ImageIO.read(new URL(request));
        }
        catch (MalformedURLException ex) {
            Bukkit.getLogger().severe("Malformed URL: " + request);
        }
        catch (IOException ex) {
            Bukkit.getLogger().severe("Could not read image from URL: " + request);
        }
        return null;
    }

    /**
     * Helper method for inserting dashes into
     * unformatted UUID.
     *
     * @return Formatted UUID with dashes.
     */
    public static String insertDashes(String uuid) {
        StringBuilder sb = new StringBuilder(uuid);
        sb.insert(8, '-');
        sb.insert(13, '-');
        sb.insert(18, '-');
        sb.insert(23, '-');
        return sb.toString();
    }

    /**
     * Returns the name of the searched player.
     *
     * @param uuid The UUID of a player.
     * @return The name of the given player.
     */
    public static String getName(UUID uuid) {
        return getName(uuid.toString());
    }

    /**
     * Returns the name of the searched player.
     *
     * @param uuid The UUID of a player (can be trimmed or the normal version).
     * @return The name of the given player.
     */
    public static String getName(String uuid) {
        uuid = uuid.replace("-", "");
        String output;

        try {
            output = callURL(MOJANG_NAME_URL + uuid);
        } catch (Exception e) {
            // If Mojang API fails, try Minetools
            try {
                output = callURL(MINETOOLS_NAME_URL + uuid);
            } catch (Exception ex) {
                Logger.getGlobal().warning("Failed to fetch player name for uuid: " + uuid);
                return null;
            }
        }

        if (output.isBlank()) {
            return null;
        }

        Matcher m = NAME_PATTERN.matcher(output);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    private static String callURL(String urlStr) throws Exception {
        StringBuilder sb = new StringBuilder();
        URLConnection conn;
        BufferedReader br = null;
        InputStreamReader in = null;
        try {
            conn = new URL(urlStr).openConnection();
            if (conn != null) {
                conn.setReadTimeout(60 * 1000);
            }
            if (conn != null && conn.getInputStream() != null) {
                in = new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8);
                br = new BufferedReader(in);
                String line = br.readLine();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }
            }
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Throwable ignored) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable ignored) {
                }
            }
        }
        return sb.toString();
    }

}