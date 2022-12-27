package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Callback;
import com.github.sqyyy.jcougar.Panel;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class CloseEventPanel implements Panel {
    private final Callback.Close closeCallback;

    public CloseEventPanel(@NotNull Callback.Close closeCallback) {
        this.closeCallback = closeCallback;
    }

    @Override
    public boolean collidesWith(int slot) {
        return false;
    }

    @Override
    public boolean canClick(int slot) {
        return false;
    }

    @Override
    public boolean canPlace(int slot) {
        return false;
    }

    @Override
    public boolean canTake(int slot) {
        return false;
    }

    @Override
    public void close(@NotNull Player player, @NotNull InventoryView view, InventoryCloseEvent.@NotNull Reason reason) {
        this.closeCallback.close(player, view, reason);
    }
}
