package com.github.sqyyy.jcougar.impl;

import com.github.sqyyy.jcougar.Panel;
import com.github.sqyyy.jcougar.Ui;
import com.github.sqyyy.jcougar.internal.UiHolder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PaperUi implements Ui {
    private final InventoryType type;
    private final int rows;
    private final int slots;
    private final Component title;
    private final List<Panel>[] panels;
    private final boolean[] clickMap;
    private final boolean[] placeMap;
    private final boolean[] takeMap;

    public PaperUi(@NotNull Component title, @Range(from = 1, to = 6) int rows) {
        this(title, InventoryType.CHEST, rows, rows * 9, List.of());
    }

    public PaperUi(@NotNull Component title, @Range(from = 1, to = 6) int rows, @NotNull List<List<Panel>> panels) {
        this(title, InventoryType.CHEST, rows, rows * 9, panels);
    }

    public PaperUi(@NotNull Component title, @NotNull InventoryType type) {
        this(title, type, List.of());
    }

    public PaperUi(@NotNull Component title, @NotNull InventoryType type, @NotNull List<List<Panel>> panels) {
        this(title, type, switch (type) {
            case CHEST, DISPENSER, DROPPER -> 3;
            case HOPPER -> 1;
            default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
        }, switch (type) {
            case CHEST -> 3 * 9;
            case DISPENSER, DROPPER -> 9;
            case HOPPER -> 5;
            default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
        }, panels);
    }

    @SuppressWarnings("unchecked")
    private PaperUi(@NotNull Component title, @NotNull InventoryType type, int rows, int slots,
        @NotNull List<List<Panel>> panels) {
        this.title = Objects.requireNonNull(title);
        this.type = Objects.requireNonNull(type);
        this.rows = rows;
        this.slots = slots;
        this.panels = new List[16];
        for (int i = 0; i < Objects.requireNonNull(panels)
            .size(); i++) {
            if (this.panels.length <= i) {
                break;
            }
            if (this.panels[i] == null) {
                this.panels[i] = new ArrayList<>();
            }
            this.panels[i].addAll(panels.get(i));
        }
        this.clickMap = new boolean[this.slots];
        this.placeMap = new boolean[this.slots];
        this.takeMap = new boolean[this.slots];
        for (int slot = 0; slot < this.slots; slot++) {
            for (final var panelList : this.panels) {
                if (panelList == null) {
                    continue;
                }
                for (final var panel : panelList) {
                    if (panel.collidesWith(slot)) {
                        this.clickMap[slot] = this.clickMap[slot] || panel.canClick(slot);
                        this.placeMap[slot] = panel.canPlace(slot);
                        this.takeMap[slot] = panel.canTake(slot);
                    }
                }
            }
        }
    }

    @Override
    public @NotNull InventoryType getType() {
        return this.type;
    }

    @Override
    public int getRows() {
        return this.rows;
    }

    @Override
    public int getSlots() {
        return this.slots;
    }

    @Override
    public void click(@NotNull Player player, @NotNull InventoryView view, int slot) {
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                if (panel.collidesWith(slot) && panel.canClick(slot)) {
                    panel.click(player, view, slot);
                }
            }
        }
    }

    @Override
    public boolean place(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item) {
        var res = false;
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                if (panel.collidesWith(slot) && panel.canPlace(slot)) {
                    res = panel.place(player, view, slot, item);
                }
            }
        }
        return res;
    }

    @Override
    public boolean placeMany(@NotNull Player player, @NotNull InventoryView view, @NotNull Map<Integer, ItemStack> items) {
        var res = false;
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                for (final var item : items.entrySet()) {
                    if (panel.collidesWith(item.getKey()) && panel.canPlace(item.getKey())) {
                        res = panel.placeMany(player, view, items);
                        break;
                    }
                }
            }
        }
        return res;
    }

    @Override
    public boolean take(@NotNull Player player, @NotNull InventoryView view, int slot) {
        var res = false;
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                if (panel.collidesWith(slot) && panel.canTake(slot)) {
                    res = panel.take(player, view, slot);
                }
            }
        }
        return res;
    }

    @Override
    public boolean replace(@NotNull Player player, @NotNull InventoryView view, int slot, @NotNull ItemStack item) {
        var res = false;
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                if (panel.collidesWith(slot) && panel.canPlace(slot) && panel.canTake(slot)) {
                    res = panel.replace(player, view, slot, item);
                }
            }
        }
        return res;
    }

    @Override
    public boolean canClick(int slot) {
        return this.clickMap[slot];
    }

    @Override
    public boolean canPlace(int slot) {
        return this.placeMap[slot];
    }

    @Override
    public boolean canTake(int slot) {
        return this.takeMap[slot];
    }

    @Override
    public void open(@NotNull Player player) {
        final Inventory inventory;
        final var holder = new UiHolder(this);
        inventory = this.type == InventoryType.CHEST ? Bukkit.createInventory(holder, this.slots, this.title) :
            Bukkit.createInventory(holder, this.type, this.title);
        holder.setInventory(inventory);
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                panel.open(player, inventory);
            }
        }
        player.openInventory(inventory);
    }

    @Override
    public void close(@NotNull Player player, @NotNull InventoryView view, @NotNull InventoryCloseEvent.Reason reason) {
        for (final var panelList : this.panels) {
            if (panelList == null) {
                continue;
            }
            for (final var panel : panelList) {
                panel.close(player, view, reason);
            }
        }
    }
}
