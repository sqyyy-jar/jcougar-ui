package com.github.sqyyy.jcougar;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface Callback {
    interface Click extends Callback {
        Click EMPTY = (player, view, slot) -> {
        };

        void click(@NotNull Player player, @NotNull InventoryView view, int slot);
    }

    interface Place extends Callback {
        Place FALSE = (player, view, slot, item) -> false;
        Place TRUE = (player, view, slot, item) -> true;

        boolean take(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item);
    }

    interface Take extends Callback {
        Take FALSE = (player, view, slot) -> false;
        Take TRUE = (player, view, slot) -> true;

        boolean take(@NotNull Player player, @NotNull InventoryView view, int slot);
    }

    interface Update extends Callback {
        Update EMPTY = (player, view) -> {
        };

        void update(@NotNull Player player, @NotNull InventoryView view);
    }

    interface Open extends Callback {
        Open EMPTY = (player, inventory) -> {
        };

        void open(@NotNull Player player, @NotNull Inventory inventory);
    }

    interface Close extends Callback {
        Close EMPTY = (player, view, reason) -> {
        };

        void close(@NotNull Player player, @NotNull InventoryView view, InventoryCloseEvent.@NotNull Reason reason);
    }
}
