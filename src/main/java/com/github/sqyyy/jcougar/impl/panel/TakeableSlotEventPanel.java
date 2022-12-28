package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Callback;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TakeableSlotEventPanel extends AbstractSlotPanel {
    private final Callback.Take takeCallback;

    public TakeableSlotEventPanel(int slot, @NotNull Callback.Take takeCallback) {
        super(slot);
        this.takeCallback = Objects.requireNonNull(takeCallback);
    }

    @Override
    public boolean take(@NotNull Player player, @NotNull InventoryView view, int slot) {
        return this.takeCallback.take(player, view, slot);
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
}
