package de.badgames.gameCore.util;

import java.util.ArrayList;

/**
 * Utility class to preload classes for YAML.
 */
public final class YAMLFixerUtil {

    /**
     * Private constructor to prevent instantiation.
     */
    private YAMLFixerUtil() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * List of classes to preload.
     */
    private static final ArrayList<String> CLASSES_TO_PRELOAD = new ArrayList<>();

    /**
     * Preload classes.
     */
    static {
        CLASSES_TO_PRELOAD.add("de.badgames.gameCore.team.Team");
        CLASSES_TO_PRELOAD.add("de.badgames.gameCore.map.GenericMap");
    }

    /**
     * Add a class to the list of classes to preload.
     * @param clazz Class to preload.
     */
    public static void add(final String clazz) {
        CLASSES_TO_PRELOAD.add(clazz);
    }

    /**
     * Load all classes in the list of classes to preload.
     */
    public static void load() {
        if (CLASSES_TO_PRELOAD.isEmpty())
            return;

        for (final String clazz : CLASSES_TO_PRELOAD)
            try {
                Class.forName(clazz);
            } catch (ClassNotFoundException ignore) {
            }
    }

}