package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Callback;
import com.github.sqyyy.jcougar.JCougar;
import com.github.sqyyy.jcougar.Panel;
import com.github.sqyyy.jcougar.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StoragePanel implements Panel {
    private final int rowWidth;
    private final Callback.Update updateCallback;
    private final int startRow;
    private final int startColumn;
    private final int endRow;
    private final int endColumn;

    public StoragePanel(int startSlot, int endSlot, int rowWidth, @NotNull Callback.Update updateCallback) {
        this.rowWidth = rowWidth;
        this.updateCallback = updateCallback;
        this.startRow = Slot.getRow(this.rowWidth, startSlot);
        this.startColumn = Slot.getColumn(this.rowWidth, startSlot);
        this.endRow = Slot.getRow(this.rowWidth, endSlot);
        this.endColumn = Slot.getColumn(this.rowWidth, endSlot);
    }

    @Override
    public boolean collidesWith(int slot) {
        final var column = Slot.getColumn(this.rowWidth, slot);
        final var row = Slot.getRow(this.rowWidth, slot);
        return row >= this.startRow && row <= this.endRow && column >= this.startColumn && column <= this.endColumn;
    }

    @Override
    public boolean place(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item) {
        JCougar.scheduleTask(() -> this.updateCallback.update(player, view));
        return false;
    }

    @Override
    public boolean placeMany(@NotNull Player player, @NotNull InventoryView view, @NotNull Map<Integer, ItemStack> items) {
        JCougar.scheduleTask(() -> this.updateCallback.update(player, view));
        return false;
    }

    @Override
    public boolean take(@NotNull Player player, @NotNull InventoryView view, int slot) {
        JCougar.scheduleTask(() -> this.updateCallback.update(player, view));
        return false;
    }

    @Override
    public boolean replace(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item) {
        JCougar.scheduleTask(() -> this.updateCallback.update(player, view));
        return false;
    }

    @Override
    public void open(@NotNull Player player, @NotNull Inventory inventory) {
        for (var row = this.startRow; row <= this.endRow; row++) {
            for (var column = this.startColumn; column <= this.endColumn; column++) {
                inventory.setItem(row * this.rowWidth + column, null);
            }
        }
    }

    @Override
    public boolean canClick(int slot) {
        return false;
    }

    @Override
    public boolean canPlace(int slot) {
        return true;
    }

    @Override
    public boolean canTake(int slot) {
        return true;
    }
}
