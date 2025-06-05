package de.badgames.pluginCore.util;

import org.bukkit.entity.Player;

public class RankUtil {

    public static String getIconFromPermission(Player player) {
        if (player.hasPermission("group.admin")) {
            return getIconFromRank("admin");
        } else if (player.hasPermission("group.dev")) {
            return getIconFromRank("dev");
        } else if (player.hasPermission("group.mod")) {
            return getIconFromRank("mod");
        } else if (player.hasPermission("group.sup")) {
            return getIconFromRank("sup");
        } else if (player.hasPermission("group.friend")) {
            return getIconFromRank("friend");
        } else if (player.hasPermission("group.mvp")) {
            return getIconFromRank("mvp");
        } else if (player.hasPermission("group.vipplus")) {
            return getIconFromRank("vip+");
        } else if (player.hasPermission("group.vip")) {
            return getIconFromRank("vip");
        }

        return getIconFromRank("default");
    }

    public static String getIconFromRank(String rank) {
        return switch (rank) {
            case "admin" -> "§d贝";
            case "dev" -> "<glyph:dev>";
            case "mod" -> "<glyph:mod>";
            case "sup" -> "<glyph:sup>";
            case "friend" -> "<glyph:fiend>";
            case "mvp" -> "§b☰";
            case "vip+" -> "§6维";
            case "vip" -> "§6☱";
            default -> "§7德";
        };
    }

    public static String getRankFromPermission(String permission) {
        return switch (permission) {
            case "group.admin" -> "admin";
            case "group.dev" -> "dev";
            case "group.mod" -> "mod";
            case "group.sup" -> "sup";
            case "group.friend" -> "friend";
            case "group.mvp" -> "mvp";
            case "group.vipplus" -> "vipplus";
            case "group.vip" -> "vip";
            default -> "default";
        };
    }

}
