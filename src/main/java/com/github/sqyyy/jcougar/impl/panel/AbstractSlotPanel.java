package com.github.sqyyy.jcougar.impl.panel;

import com.github.sqyyy.jcougar.Panel;

public abstract class AbstractSlotPanel implements Panel {
    private final int slot;

    protected AbstractSlotPanel(int slot) {
        this.slot = slot;
    }

    @Override
    public boolean collidesWith(int slot) {
        return slot == this.slot;
    }
}
