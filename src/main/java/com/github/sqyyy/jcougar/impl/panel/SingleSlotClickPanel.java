package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Panel;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class SingleSlotClickPanel implements Panel {
    private final int slot;
    private final Callback clickCallback;

    public SingleSlotClickPanel(int slot, @NotNull Callback clickCallback) {
        this.slot = slot;
        this.clickCallback = clickCallback;
    }

    @Override
    public boolean collidesWith(int slot) {
        return slot == this.slot;
    }

    @Override
    public void click(@NotNull Player player, @NotNull InventoryView view, int slot) {
        this.clickCallback.click(player, view, slot);
    }

    @Override
    public boolean canClick(int slot) {
        return true;
    }

    @Override
    public boolean canPlace(int slot) {
        return false;
    }

    @Override
    public boolean canTake(int slot) {
        return false;
    }

    public interface Callback {
        void click(@NotNull Player player, @NotNull InventoryView view, int slot);
    }
}
