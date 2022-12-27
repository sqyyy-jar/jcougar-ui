package com.github.sqyyy.jcougar.internal;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class SystemListener implements Listener {
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        final var holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof UiHolder uiHolder) {
            uiHolder.onClose(event);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        final var holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof UiHolder uiHolder) {
            uiHolder.onDrag(event);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        final var holder = event.getView().getTopInventory().getHolder();
        if (holder instanceof UiHolder uiHolder) {
            uiHolder.onClick(event, event instanceof InventoryCreativeEvent);
        }
    }
}
