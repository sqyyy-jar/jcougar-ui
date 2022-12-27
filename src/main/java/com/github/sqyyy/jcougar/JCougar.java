package com.github.sqyyy.jcougar;

import com.github.sqyyy.jcougar.internal.SystemListener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class JCougar {
    private static boolean initialized = false;
    private static Plugin plugin = null;

    public static void initializeSystem(@NotNull Plugin plugin) {
        if (JCougar.initialized) {
            throw new IllegalStateException("JCougar is already initialized");
        }
        JCougar.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new SystemListener(), JCougar.plugin);
        JCougar.initialized = true;
    }

    public static void scheduleTask(@NotNull Runnable task) {
        if (JCougar.plugin == null) {
            throw new IllegalStateException("JCougar is not yet initialized");
        }
        JCougar.plugin.getServer().getScheduler().runTask(JCougar.plugin, task);
    }
}
