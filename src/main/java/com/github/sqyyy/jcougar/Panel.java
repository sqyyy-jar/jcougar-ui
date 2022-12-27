package com.github.sqyyy.jcougar;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Panel {
    boolean collidesWith(int slot);

    default void click(@NotNull Player player, @NotNull InventoryView view, int slot) {
    }

    default boolean place(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item) {
        return true;
    }

    default boolean placeMany(@NotNull Player player, @NotNull InventoryView view, @NotNull Map<Integer, ItemStack> items) {
        return true;
    }

    default boolean take(@NotNull Player player, @NotNull InventoryView view, int slot) {
        return true;
    }

    default boolean replace(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item) {
        return true;
    }

    boolean canClick(int slot);

    boolean canPlace(int slot);

    boolean canTake(int slot);

    default void open(@NotNull Player player, @NotNull Inventory inventory) {
    }

    default void close(@NotNull Player player, @NotNull InventoryView view, @NotNull InventoryCloseEvent.Reason reason) {
    }
}
