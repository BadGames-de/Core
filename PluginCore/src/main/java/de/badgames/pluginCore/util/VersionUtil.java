package de.badgames.pluginCore.util;

import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionUtil {

    /**
     * The current version of the server in the form of a major version.
     * If the static initialization for this fails, you know something's wrong with the server software.
     *
     * @since 1.0.0
     */
    private static final int VERSION;

    static { // This needs to be right below VERSION because of initialization order.
        String version = Bukkit.getVersion();
        Matcher matcher = Pattern.compile("MC: \\d\\.(\\d+)").matcher(version);

        if (matcher.find()) VERSION = Integer.parseInt(matcher.group(1));
        else throw new IllegalArgumentException("Failed to parse server version from: " + version);
    }

    private static final boolean ISFLAT = supports(13);

    private static final boolean ISVILLAGE = supports(14);

    private static final boolean ISBEE = supports(15);

    private static final boolean ISNETHER = supports(16);

    private static final boolean ISCAVES_AND_CLIFFS = supports(17);

    private static final boolean ISCAVES_AND_CLIFFS_2 = supports(18);

    private static final boolean ISWILD = supports(19);

    private static final boolean ISTRAILS_AND_TALES = supports(20);

    private static final boolean ISTRICKY = supports(21);

    public static boolean supports(int version) {
        return VERSION >= version;
    }
}
