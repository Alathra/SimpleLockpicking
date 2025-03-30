package io.github.alathra.simplelockpicking.core;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.time.Instant;

public abstract class ActiveLockpick {

    private Instant lockpickTime;
    private final Object base;
    private final Player player;

    public ActiveLockpick(Object base, Player player) {
        this.base = base;
        this.player = player;
    }

    public abstract void toggle();
    public abstract boolean shouldToggleTwice();
    public abstract void startLockpicking(Location location);

    public Object getBase() {
        return base;
    }

    public Player getPlayer() {
        return player;
    }

}
