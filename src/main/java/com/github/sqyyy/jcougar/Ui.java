package com.github.sqyyy.jcougar;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Ui {
    @NotNull InventoryType getType();

    int getRows();

    int getSlots();

    void click(@NotNull Player player, @NotNull InventoryView view, int slot);

    boolean place(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item);

    boolean placeMany(@NotNull Player player, @NotNull InventoryView view, @NotNull Map<Integer, ItemStack> items);

    boolean take(@NotNull Player player, @NotNull InventoryView view, int slot);

    boolean replace(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item);

    boolean canClick(int slot);

    boolean canPlace(int slot);

    boolean canTake(int slot);

    void open(@NotNull Player player);

    void close(@NotNull Player player, @NotNull InventoryView view, InventoryCloseEvent.@NotNull Reason reason);
}
