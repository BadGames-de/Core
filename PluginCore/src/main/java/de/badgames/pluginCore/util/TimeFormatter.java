package de.badgames.pluginCore.util;

public class TimeFormatter {

    public static String getRemainingTime(long timeInFutureMilliseconds, TIME_FORMAT format) {
        long current = System.currentTimeMillis();
        long difference = timeInFutureMilliseconds - current;

        int seconds = 0;
        int minutes = 0;
        int hours = 0;
        int days = 0;
        while (difference >= 1000) {
            difference -= 1000;
            ++seconds;
        }
        while (seconds >= 60) {
            seconds -= 60;
            ++minutes;
        }
        while (minutes >= 60) {
            minutes -= 60;
            ++hours;
        }
        while (hours >= 24) {
            hours -= 24;
            ++days;
        }

        if (format == TIME_FORMAT.FULL_FORMAT) {
            return getLongFormat(days, hours, minutes, seconds);
        } else {
            return getShortFormat(days, hours, minutes, seconds);
        }
    }

    private static String getLongFormat(int days, int hours, int minutes, int seconds) {
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days).append(" Day(s)");
            if (hours > 0 || minutes > 0 || seconds > 0) {
                sb.append(", ");
            }
        }

        if (hours > 0) {
            sb.append(hours).append(" Hour(s)");
            if (minutes > 0 || seconds > 0) {
                sb.append(", ");
            }
        }

        if (minutes > 0) {
            sb.append(minutes).append(" Minute(s)");
            if (seconds > 0) {
                sb.append(", ");
            }
        }

        sb.append(seconds).append(" Second(s)");

        return sb.toString().trim();
    }

    private static String getShortFormat(int days, int hours, int minutes, int seconds) {
        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days);
            if (hours > 0 || minutes > 0 || seconds > 0) {
                sb.append(":");
            }
        }

        if (hours > 0) {
            sb.append(hours);
            if (minutes > 0 || seconds > 0) {
                sb.append(":");
            }
        }

        if (minutes > 0) {
            sb.append(minutes);
            if (seconds > 0) {
                sb.append(":");
            }
        }

        if (seconds > 0 || sb.isEmpty()) {
            sb.append(seconds);
        }

        return sb.toString().trim();
    }

    public enum TIME_FORMAT {
        FULL_FORMAT,
        SHORT_FORMAT,
    }

}