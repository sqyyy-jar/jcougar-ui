package com.github.sqyyy.jcougar.internal;

import com.github.sqyyy.jcougar.Ui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UiHolder implements InventoryHolder {
    private final Ui ui;
    private Inventory inventory;

    public UiHolder(@NotNull Ui ui) {
        this.ui = Objects.requireNonNull(ui);
        this.inventory = null;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return Objects.requireNonNull(this.inventory);
    }

    public void setInventory(@NotNull Inventory inventory) {
        if (this.inventory != null) {
            throw new IllegalStateException("Inventory already set");
        }
        this.inventory = Objects.requireNonNull(inventory);
    }

    public void onClose(@NotNull InventoryCloseEvent event) {
        this.ui.close((Player) event.getPlayer(), event.getView(), event.getReason());
    }

    public void onDrag(@NotNull InventoryDragEvent event) {
        if (event.getRawSlots().stream().anyMatch(it -> it < this.ui.getSlots() && !this.ui.canPlace(it))) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(this.ui.placeMany((Player) event.getWhoClicked(), event.getView(),
            event.getNewItems().entrySet().stream().filter(it -> it.getKey() < this.ui.getSlots())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
    }

    public void onClick(@NotNull InventoryClickEvent event, boolean creative) {
        if (this.inventory == null) {
            throw new IllegalStateException("No inventory set");
        }
        final var slot = event.getRawSlot();
        final var uiClick = slot < this.ui.getSlots();
        switch (event.getAction()) {
            case PICKUP_ALL, PICKUP_SOME, PICKUP_HALF, PICKUP_ONE, DROP_ALL_SLOT, DROP_ONE_SLOT -> {
                if (!uiClick) {
                    return;
                }
                if (!this.ui.canTake(slot)) {
                    event.setCancelled(true);
                    if (this.ui.canClick(slot)) {
                        this.ui.click((Player) event.getWhoClicked(), event.getView(), slot);
                    }
                    return;
                }
                event.setCancelled(this.ui.take((Player) event.getWhoClicked(), event.getView(), slot));
            }
            case PLACE_ALL, PLACE_SOME, PLACE_ONE -> {
                if (event.getCursor() == null) {
                    return;
                }
                if (!uiClick) {
                    return;
                }
                if (!this.ui.canPlace(slot)) {
                    event.setCancelled(true);
                    if (this.ui.canClick(slot)) {
                        this.ui.click((Player) event.getWhoClicked(), event.getView(), slot);
                    }
                    return;
                }
                event.setCancelled(this.ui.place((Player) event.getWhoClicked(), event.getView(), slot, event.getCursor()));
            }
            case SWAP_WITH_CURSOR, HOTBAR_MOVE_AND_READD -> {
                if (!uiClick) {
                    return;
                }
                if (!this.ui.canTake(slot) || !this.ui.canPlace(slot)) {
                    event.setCancelled(true);
                    if (this.ui.canClick(slot)) {
                        this.ui.click((Player) event.getWhoClicked(), event.getView(), slot);
                    }
                    return;
                }
                // TODO - nullability
                event.setCancelled(this.ui.replace((Player) event.getWhoClicked(), event.getView(), slot,
                    Objects.requireNonNull(event.getCurrentItem())));
            }
            case HOTBAR_SWAP -> {
                if (!uiClick) {
                    return;
                }
                final var place = event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR;
                if (place) {
                    if (!this.ui.canPlace(slot)) {
                        event.setCancelled(true);
                        if (this.ui.canClick(slot)) {
                            this.ui.click((Player) event.getWhoClicked(), event.getView(), slot);
                        }
                        return;
                    }
                    if (event.getCurrentItem() == null) {
                        // TODO - nullability
                        event.setCancelled(this.ui.place((Player) event.getWhoClicked(), event.getView(), slot,
                            Objects.requireNonNull(event.getWhoClicked().getInventory().getItem(event.getHotbarButton()))));
                        return;
                    }
                    event.setCancelled(this.ui.take((Player) event.getWhoClicked(), event.getView(), slot));
                    return;
                }
                if (!this.ui.canTake(slot)) {
                    event.setCancelled(true);
                    if (this.ui.canClick(slot)) {
                        this.ui.click((Player) event.getWhoClicked(), event.getView(), slot);
                    }
                    return;
                }
                event.setCancelled(this.ui.take((Player) event.getWhoClicked(), event.getView(), slot));
            }
            case MOVE_TO_OTHER_INVENTORY -> {
                if (event.getCurrentItem() == null) {
                    return;
                }
                final var currentItem = event.getCurrentItem();
                if (currentItem.getType() == Material.AIR) {
                    return;
                }
                if (uiClick) {
                    if (!this.ui.canTake(slot)) {
                        event.setCancelled(true);
                        if (this.ui.canClick(slot)) {
                            this.ui.click((Player) event.getWhoClicked(), event.getView(), slot);
                        }
                        return;
                    }
                    event.setCancelled(this.ui.take((Player) event.getWhoClicked(), event.getView(), slot));
                    return;
                }
                final var inventory = this.inventory;
                var amount = currentItem.getAmount();
                event.setCancelled(true);
                for (int i = 0; i < this.ui.getSlots(); i++) {
                    if (!this.ui.canPlace(i)) {
                        continue;
                    }
                    final var item = inventory.getItem(i);
                    if (item == null || item.getType() == Material.AIR) {
                        event.getView().getBottomInventory().setItem(event.getSlot(), null);
                        inventory.setItem(i, currentItem);
                        break;
                    }
                    if (item.getAmount() >= 64) {
                        continue;
                    }
                    if (currentItem.isSimilar(item)) {
                        final var max = 64 - item.getAmount();
                        if (max >= amount) {
                            event.getView().getBottomInventory().setItem(event.getSlot(), null);
                            inventory.setItem(i, currentItem);
                            break;
                        }
                        amount -= max;
                        item.setAmount(64);
                        currentItem.setAmount(amount);
                        break;
                    }
                }
            }
            case COLLECT_TO_CURSOR, UNKNOWN -> event.setCancelled(true);
        }
    }
}
