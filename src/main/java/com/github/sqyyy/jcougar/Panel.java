package com.github.sqyyy.jcougar;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface Panel {
    boolean collidesWith(int slot);

    default void click(Player player, InventoryView view, int slot) {
    }

    default boolean place(Player player, InventoryView view, int slot, ItemStack item) {
        return true;
    }

    default boolean placeMany(Player player, InventoryView view, Map<Integer, ItemStack> items) {
        return true;
    }

    default boolean take(Player player, InventoryView view, int slot) {
        return true;
    }

    default boolean replace(Player player, InventoryView view, int slot, ItemStack item) {
        return true;
    }

    boolean canClick(int slot);

    boolean canPlace(int slot);

    boolean canTake(int slot);

    default void open(Player player, Inventory inventory) {
    }

    default void close(Player player, InventoryView view, InventoryCloseEvent.Reason reason) {
    }
}
