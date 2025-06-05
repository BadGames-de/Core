package de.badgames.gameCore.util;

/**
 * Utility class for formatting time.
 */
public class TimeFormatter {

    /**
     * Format ticks to a string.
     * @param ticks ticks to format.
     * @return formatted string.
     */
    public static String formatTicks(long ticks) {
        long seconds = ticks / 20;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("§a%02d§7:§a%02d", minutes, seconds);
    }

}
