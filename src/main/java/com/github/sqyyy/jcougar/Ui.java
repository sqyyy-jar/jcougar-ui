package com.github.sqyyy.jcougar;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public interface Ui {
    InventoryType getType();

    int getRows();

    int getSlots();

    void click(Player player, InventoryView view, int slot);

    boolean place(Player player, InventoryView view, int slot, ItemStack item);

    boolean placeMany(Player player, InventoryView view, Map<Integer, ItemStack> items);

    boolean take(Player player, InventoryView view, int slot);

    boolean replace(Player player, InventoryView view, int slot, ItemStack item);

    boolean canClick(int slot);

    boolean canPlace(int slot);

    boolean canTake(int slot);

    void open(Player player);

    void close(Player player, InventoryView view, InventoryCloseEvent.Reason reason);
}
