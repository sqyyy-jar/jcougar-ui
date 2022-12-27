package com.github.sqyyy.jcougar;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

public interface Callback {
    interface Click extends Callback {
        void click(@NotNull Player player, @NotNull InventoryView view, int slot);
    }

    interface Update extends Callback {
        void update(@NotNull Player player, @NotNull InventoryView view);
    }

    interface Open extends Callback {
        void open(@NotNull Player player, @NotNull Inventory inventory);
    }

    interface Close extends Callback {
        void close(@NotNull Player player, @NotNull InventoryView view, InventoryCloseEvent.@NotNull Reason reason);
    }
}
