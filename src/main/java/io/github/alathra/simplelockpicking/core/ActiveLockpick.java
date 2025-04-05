package io.github.alathra.simplelockpicking.core;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.Hook;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public abstract class ActiveLockpick {

    private final Object base;
    private final Player player;
    private boolean isToggleableByPlayers;
    private boolean wasAccessForced;

    public ActiveLockpick(Object base, Player player) {
        this.base = base;
        this.player = player;
        isToggleableByPlayers = true;
    }

    public abstract void toggleBase();

    public abstract boolean isContainer();

    public abstract @Nullable Inventory getInventory();

    public abstract boolean isMultiBlock();

    public abstract boolean isSuccessful();

    public abstract void createLockpickBreakEffect();

    public void startLockpicking() {
        player.sendMessage(ColorParser.of("<yellow>Attempting to lockpick...").build());
        // "Use" the lockpick they have in their hand
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
        if (Settings.isLockpickingSoundEnabled())
            player.playSound(Settings.getLockpickingSound());
        Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> {
            if (isSuccessful()) {
                player.sendMessage(ColorParser.of("<green>Lockpicking was successful").build());
                // return lockpick to inventory
                player.getInventory().addItem(SimpleLockpickingAPI.getLockpickItem());
                wasAccessForced = false;
                if (Hook.Bolt.isLoaded()) {
                    wasAccessForced = Hook.getBoltHook().forceAccessIfNeeded(this);
                    if (wasAccessForced) {
                        if (getBase() instanceof Block block) {
                            LockpickingManager.getForcedAccessBlockInventoriies().put(block, getInventory());
                        } else if (getBase() instanceof Entity entity) {
                            LockpickingManager.getForcedAccessEntityInventories().put(entity, getInventory());
                        }
                    }
                }
                toggleBase();
                if (isContainer()) {
                    LockpickingManager.deRegisterActiveLockpick(this);
                    return;
                }
                if (Settings.getSecondsUntilToggleable() > 0) {
                    isToggleableByPlayers = false;
                    Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> {
                        isToggleableByPlayers = true;
                        if (Settings.getSecondsUntilToggleable() >= Settings.getSecondsUntilClosesAgain()) {
                            LockpickingManager.deRegisterActiveLockpick(this);
                        }
                    }, Settings.getSecondsUntilToggleable() * 20L);
                }
                if (Settings.getSecondsUntilClosesAgain() > 0 && !isMultiBlock()) {
                    Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> {
                        toggleBase();
                        if (Settings.getSecondsUntilClosesAgain() > Settings.getSecondsUntilToggleable()) {
                            LockpickingManager.deRegisterActiveLockpick(this);
                        }
                    }, Settings.getSecondsUntilClosesAgain() * 20L);
                }
                if (Settings.getSecondsUntilToggleable() <= 0 && Settings.getSecondsUntilClosesAgain() <= 0) {
                    LockpickingManager.deRegisterActiveLockpick(this);
                }
            } else {
                player.sendMessage(ColorParser.of("<red>Your lockpick broke").build());
                if (Settings.isLockpickBreakEffectEnabled()) {
                    createLockpickBreakEffect();
                }
                LockpickingManager.deRegisterActiveLockpick(this);
            }
        }, Settings.getLockpickSeconds() * 20L);
    }

    public Object getBase() {
        return base;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isToggleableByPlayers() {
        return isToggleableByPlayers;
    }

    public boolean wasAccessForced() {
        return wasAccessForced;
    }

}
