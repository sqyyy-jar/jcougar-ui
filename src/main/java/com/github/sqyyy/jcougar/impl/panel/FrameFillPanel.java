package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Panel;
import com.github.sqyyy.jcougar.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FrameFillPanel implements Panel {
    private final int rowWidth;
    private final ItemStack fillItem;
    private final int startRow;
    private final int startColumn;
    private final int endRow;
    private final int endColumn;

    public FrameFillPanel(int startSlot, int endSlot, int rowWidth, ItemStack fillItem) {
        this.rowWidth = rowWidth;
        this.fillItem = fillItem;
        this.startRow = Slot.getRow(this.rowWidth, startSlot);
        this.startColumn = Slot.getColumn(this.rowWidth, startSlot);
        this.endRow = Slot.getRow(this.rowWidth, endSlot);
        this.endColumn = Slot.getColumn(this.rowWidth, endSlot);
    }

    @Override
    public boolean collidesWith(int slot) {
        final var row = Slot.getRow(this.rowWidth, slot);
        final var column = Slot.getColumn(this.rowWidth, slot);
        return (row == this.startRow || row == this.endRow) && (column == this.startColumn || column == this.endColumn);
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
        for (var column = this.startColumn; column <= this.endColumn; column++) {
            inventory.setItem(this.startRow * this.rowWidth + column, this.fillItem);
        }
        for (var row = this.startRow + 1; row < this.endRow + 1; row++) {
            inventory.setItem(row * this.rowWidth + this.startColumn, this.fillItem);
            inventory.setItem(row * this.rowWidth + this.endColumn, this.fillItem);
        }
        for (var column = this.startColumn; column <= this.endColumn; column++) {
            inventory.setItem(this.endRow * this.rowWidth + column, this.fillItem);
        }
    }
}
