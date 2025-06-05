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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper-class for getting UUIDs of players.
 */
public final class PlayerInfoFetcher {

    private static final String UUID_URL = "https://api.mojang.com/users"
            + "/profiles/minecraft/";

    private static final Pattern UUID_PATTERN = Pattern.compile("\"id\"\\s*:\\s*\"(.*?)\"");

    private static final String NAME_URL = "https://sessionserver.mojang.com"
            + "/session/minecraft/profile/";

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
        String output = callURL(UUID_URL + name);
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
        String output = callURL(NAME_URL + uuid);
        Matcher m = NAME_PATTERN.matcher(output);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    private static String callURL(String urlStr) {
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
                in = new InputStreamReader(conn.getInputStream(), "UTF-8");
                br = new BufferedReader(in);
                String line = br.readLine();
                while (line != null) {
                    sb.append(line).append("\n");
                    line = br.readLine();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
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