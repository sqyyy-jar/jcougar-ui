package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Callback;
import com.github.sqyyy.jcougar.Panel;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class OpenEventPanel implements Panel {
    private final Callback.Open openCallback;

    public OpenEventPanel(@NotNull Callback.Open openCallback) {
        this.openCallback = openCallback;
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
    public void open(@NotNull Player player, @NotNull Inventory inventory) {
        this.openCallback.open(player, inventory);
    }
}
