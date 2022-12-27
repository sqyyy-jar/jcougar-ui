package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Callback;
import com.github.sqyyy.jcougar.Panel;
import com.github.sqyyy.jcougar.Slot;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public class ClickPanel implements Panel {
    private final int rowWidth;
    private final Callback.Click clickCallback;
    private final int startRow;
    private final int startColumn;
    private final int endRow;
    private final int endColumn;

    public ClickPanel(int startSlot, int endSlot, int rowWidth, @NotNull Callback.Click clickCallback) {
        this.rowWidth = rowWidth;
        this.clickCallback = clickCallback;
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
