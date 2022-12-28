package com.github.sqyyy.jcougar.impl.panel;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SlotFillPanel extends AbstractSlotPanel {
    private final ItemStack fillItem;

    public SlotFillPanel(int slot, @Nullable ItemStack fillItem) {
        super(slot);
        this.fillItem = fillItem;
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
