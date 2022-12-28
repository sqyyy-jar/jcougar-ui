package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Callback;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class SlotClickEventPanel extends AbstractSlotPanel {
    private final Callback.Click clickCallback;

    public SlotClickEventPanel(int slot, @NotNull Callback.Click clickCallback) {
        super(slot);
        this.clickCallback = clickCallback;
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
}
