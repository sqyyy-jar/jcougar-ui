package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Panel;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingleSlotFillPanel implements Panel {
    private final int slot;
    private final ItemStack fillItem;

    public SingleSlotFillPanel(int slot, @Nullable ItemStack fillItem) {
        this.slot = slot;
        this.fillItem = fillItem;
    }

    @Override
    public boolean collidesWith(int slot) {
        return slot == this.slot;
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
        inventory.setItem(this.slot, this.fillItem);
    }
}
