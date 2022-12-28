package com.github.sqyyy.jcougar.internal;

import com.github.sqyyy.jcougar.JCougar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.jetbrains.annotations.NotNull;

public class SystemListener implements Listener {
    @EventHandler
    public void onClose(@NotNull InventoryCloseEvent event) {
        final var holder = event.getView()
            .getTopInventory()
            .getHolder();
        if (holder instanceof UiHolder uiHolder) {
            JCougar.UnsafeValues.inventoryCloseEvent = event;
            uiHolder.onClose(event);
            JCougar.UnsafeValues.inventoryCloseEvent = null;
        }
    }

    @EventHandler
    public void onDrag(@NotNull InventoryDragEvent event) {
        final var holder = event.getView()
            .getTopInventory()
            .getHolder();
        if (holder instanceof UiHolder uiHolder) {
            JCougar.UnsafeValues.inventoryDragEvent = event;
            uiHolder.onDrag(event);
            JCougar.UnsafeValues.inventoryDragEvent = null;
        }
    }

    @EventHandler
    public void onClick(@NotNull InventoryClickEvent event) {
        final var holder = event.getView()
            .getTopInventory()
            .getHolder();
        if (holder instanceof UiHolder uiHolder) {
            JCougar.UnsafeValues.inventoryClickEvent = event;
            uiHolder.onClick(event, event instanceof InventoryCreativeEvent);
            JCougar.UnsafeValues.inventoryClickEvent = null;
        }
    }
}
