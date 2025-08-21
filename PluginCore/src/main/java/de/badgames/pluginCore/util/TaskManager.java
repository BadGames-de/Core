package de.badgames.pluginCore.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TaskManager {

    private final ArrayList<Runnable> toRun = new ArrayList<>();
    private final ArrayList<Runnable> runOnce = new ArrayList<>();
    private final ArrayList<Runnable> toRemove = new ArrayList<>();

    public void initTask(JavaPlugin javaPlugin) {

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Runnable rem : toRemove) {
                    toRun.remove(rem);
                    runOnce.remove(rem);
                }

                for (Runnable rem : runOnce) {
                    rem.run();
                    toRemove.add(rem);
                }

                for (Runnable runnable : toRun) {
                    runnable.run();
                }
            }
        }.runTaskTimer(javaPlugin, 0, 1);

    }

    public void inject(Runnable runnable) {
        toRun.add(runnable);
    }

    public void runOnce(Runnable runnable) {
        runOnce.add(runnable);
    }

    public void uninject(Runnable runnable) {
        if (runnable == null) return;
        toRemove.add(runnable);
    }

}
