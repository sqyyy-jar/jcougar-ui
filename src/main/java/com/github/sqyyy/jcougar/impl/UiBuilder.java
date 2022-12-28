package com.github.sqyyy.jcougar.impl;

import com.github.sqyyy.jcougar.Callback;
import com.github.sqyyy.jcougar.Panel;
import com.github.sqyyy.jcougar.Slot;
import com.github.sqyyy.jcougar.impl.panel.CloseEventPanel;
import com.github.sqyyy.jcougar.impl.panel.FillPanel;
import com.github.sqyyy.jcougar.impl.panel.FrameFillPanel;
import com.github.sqyyy.jcougar.impl.panel.OpenEventPanel;
import com.github.sqyyy.jcougar.impl.panel.SlotFillPanel;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UiBuilder {
    public static class PaperUiBuilder {
        private final List<List<Panel>> panels;
        private Component title;
        private InventoryType type;
        private int rows;

        public PaperUiBuilder() {
            this.title = Component.empty();
            this.type = InventoryType.CHEST;
            this.rows = 3;
            this.panels = new ArrayList<>(16);
            for (int i = 0; i < 16; i++) {
                this.panels.add(new ArrayList<>(0));
            }
        }

        public @NotNull PaperUiBuilder title(@NotNull Component title) {
            this.title = Objects.requireNonNull(title);
            return this;
        }

        public @NotNull PaperUiBuilder type(@NotNull InventoryType type) {
            this.type = Objects.requireNonNull(type);
            this.rows = switch (type) {
                case CHEST -> Math.min(6, Math.max(1, this.rows));
                case DISPENSER, DROPPER -> 3;
                case HOPPER -> 1;
                default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
            };
            return this;
        }

        public @NotNull PaperUiBuilder rows(int rows) {
            switch (this.type) {
                case CHEST -> {
                    if (rows > 6 || rows < 1) {
                        throw new IllegalArgumentException("Invalid amount of rows");
                    }
                }
                case DISPENSER, DROPPER -> {
                    if (rows != 3) {
                        throw new IllegalArgumentException("Invalid amount of rows");
                    }
                }
                case HOPPER -> {
                    if (rows != 1) {
                        throw new IllegalArgumentException("Invalid amount of rows");
                    }
                }
                default -> throw new IllegalStateException();
            }
            this.rows = rows;
            return this;
        }

        public @NotNull PaperUiBuilder fill(@Range(from = 0, to = 15) int priority, @NotNull Slot from, @NotNull Slot to,
            @Nullable ItemStack fillItem) {
            Objects.checkIndex(priority, 16);
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);
            this.panels.get(priority)
                .add(switch (this.type) {
                    case CHEST -> new FillPanel(from.chestSlot, to.chestSlot, 9, fillItem);
                    case DISPENSER, DROPPER -> new FillPanel(from.dispenserSlot, to.dispenserSlot, 3, fillItem);
                    case HOPPER -> new FillPanel(from.hopperSlot, to.hopperSlot, 5, fillItem);
                    default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
                });
            return this;
        }

        public @NotNull PaperUiBuilder frame(@Range(from = 0, to = 15) int priority, @NotNull Slot from, @NotNull Slot to,
            @Nullable ItemStack fillItem) {
            Objects.checkIndex(priority, 16);
            Objects.requireNonNull(from);
            Objects.requireNonNull(to);
            this.panels.get(priority)
                .add(switch (this.type) {
                    case CHEST -> new FrameFillPanel(from.chestSlot, to.chestSlot, 9, fillItem);
                    case DISPENSER, DROPPER -> new FrameFillPanel(from.dispenserSlot, to.dispenserSlot, 3, fillItem);
                    case HOPPER -> new FrameFillPanel(from.hopperSlot, to.hopperSlot, 5, fillItem);
                    default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
                });
            return this;
        }

        public @NotNull PaperUiBuilder put(@Range(from = 0, to = 15) int priority, @NotNull Slot slot,
            @Nullable ItemStack fillItem) {
            Objects.checkIndex(priority, 16);
            Objects.requireNonNull(slot);
            this.panels.get(priority)
                .add(switch (this.type) {
                    case CHEST -> new SlotFillPanel(slot.chestSlot, fillItem);
                    case DISPENSER, DROPPER -> new SlotFillPanel(slot.dispenserSlot, fillItem);
                    case HOPPER -> new SlotFillPanel(slot.hopperSlot, fillItem);
                    default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
                });
            return this;
        }

        public @NotNull PaperUiBuilder onOpen(@NotNull Callback.Open openCallback) {
            Objects.requireNonNull(openCallback);
            return this.onOpen(0, openCallback);
        }

        public @NotNull PaperUiBuilder onOpen(@Range(from = 0, to = 15) int priority, @NotNull Callback.Open openCallback) {

            Objects.checkIndex(priority, 16);
            Objects.requireNonNull(openCallback);
            this.panels.get(priority)
                .add(new OpenEventPanel(openCallback));
            return this;
        }

        public @NotNull PaperUiBuilder onClose(@NotNull Callback.Close closeCallback) {
            Objects.requireNonNull(closeCallback);
            return this.onClose(0, closeCallback);
        }

        public @NotNull PaperUiBuilder onClose(@Range(from = 0, to = 15) int priority, @NotNull Callback.Close closeCallback) {
            Objects.checkIndex(priority, 16);
            Objects.requireNonNull(closeCallback);
            this.panels.get(priority)
                .add(new CloseEventPanel(closeCallback));
            return this;
        }

        public @NotNull PaperUiBuilder addPanels(@NotNull List<List<Panel>> panels) {
            Objects.requireNonNull(panels);
            for (int i = 0; i < panels.size(); i++) {
                if (this.panels.size() <= i) {
                    break;
                }
                this.panels.get(i)
                    .addAll(Objects.requireNonNull(panels.get(i)));
            }
            return this;
        }

        public @NotNull PaperUiBuilder addPanel(@Range(from = 0, to = 15) int priority, @NotNull Panel panel) {
            Objects.checkIndex(priority, 16);
            Objects.requireNonNull(panel);
            this.panels.get(priority)
                .add(panel);
            return this;
        }

        public @NotNull PaperUi build() {
            if (this.type == InventoryType.CHEST) {
                return new PaperUi(this.title, this.rows, this.panels);
            }
            return new PaperUi(this.title, this.type, this.panels);
        }
    }
}
