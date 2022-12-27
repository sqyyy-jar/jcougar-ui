package com.github.sqyyy.jcougar.impl;

import com.github.sqyyy.jcougar.Panel;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UiBuilder {
    public static class PaperUiBuilder {
        private Component title;
        private InventoryType type;
        private int rows;
        private List<List<Panel>> panels;

        public PaperUiBuilder() {
            this.title = Component.empty();
            this.type = InventoryType.CHEST;
            this.rows = 3;
            this.panels = new ArrayList<>(16);
            for (int i = 0; i < 16; i++) {
                this.panels.add(new ArrayList<>(0));
            }
        }

        public PaperUiBuilder title(@NotNull Component title) {
            this.title = Objects.requireNonNull(title);
            return this;
        }

        public PaperUiBuilder type(@NotNull InventoryType type) {
            this.type = Objects.requireNonNull(type);
            this.rows = switch (type) {
                case CHEST -> Math.min(6, Math.max(1, this.rows));
                case DISPENSER, DROPPER -> 3;
                case HOPPER -> 1;
                default -> throw new IllegalArgumentException("Unsupported InventoryType was provided");
            };
            return this;
        }

        public PaperUiBuilder rows(int rows) {
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

        public PaperUi build() {
            if (this.type == InventoryType.CHEST) {
                return new PaperUi(this.title, this.rows, this.panels);
            }
            return new PaperUi(this.title, this.type, this.panels);
        }
    }
}
