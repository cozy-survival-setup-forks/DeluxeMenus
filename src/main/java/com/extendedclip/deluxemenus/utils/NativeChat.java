package com.extendedclip.deluxemenus.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

/**
 * Sends MiniMessage text (click and hover events included) through the Adventure that Paper already has, by
 * reflection since the plugin's own copy of Adventure is relocated. Not there on Spigot, then {@link #send} says so
 * and the caller falls back to its own copy.
 */
public final class NativeChat {

    // Built in pieces: the build relocates our own Adventure, and would rewrite the name of the one we want too
    private static final String ADVENTURE = "net#kyori#adventure#".replace('#', '.');
    private static final Object MINI;
    private static final Method DESERIALIZE;
    private static final Method SEND;

    static {
        Object mini = null;
        Method deserialize = null;
        Method send = null;
        try {
            final Class<?> miniClass = Class.forName(ADVENTURE + "text.minimessage.MiniMessage");
            final Class<?> componentClass = Class.forName(ADVENTURE + "text.Component");
            final Class<?> audienceClass = Class.forName(ADVENTURE + "audience.Audience");
            mini = miniClass.getMethod("miniMessage").invoke(null);
            deserialize = miniClass.getMethod("deserialize", Object.class);
            send = audienceClass.getMethod("sendMessage", componentClass);
        } catch (ReflectiveOperationException | LinkageError e) {
            Bukkit.getLogger().warning("[DeluxeMenus] No native Adventure: " + e);
            // Spigot
        }
        MINI = mini;
        DESERIALIZE = deserialize;
        SEND = send;
    }

    private NativeChat() {
    }

    /** @return true if the server's own Adventure was there and sent it */
    public static boolean send(final @NotNull CommandSender to, final @NotNull String miniMessage) {
        if (SEND == null || !SEND.getDeclaringClass().isInstance(to)) return false;
        try {
            SEND.invoke(to, DESERIALIZE.invoke(MINI, miniMessage));
            return true;
        } catch (ReflectiveOperationException | RuntimeException e) {
            Bukkit.getLogger().warning("[DeluxeMenus] Could not send with the native Adventure: " + e);
            return false;
        }
    }

    /** @return true if the server's own Adventure was there and sent it to everyone online */
    public static boolean broadcast(final @NotNull String miniMessage) {
        if (SEND == null) return false;
        boolean sent = false;
        for (final org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) sent |= send(player, miniMessage);
        return sent;
    }
}
