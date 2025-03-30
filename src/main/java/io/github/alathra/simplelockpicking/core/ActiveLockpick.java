package io.github.alathra.simplelockpicking.core;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class ActiveLockpick {

    private final Object base;
    private final Player player;
    private boolean isToggleableByPlayers;

    public ActiveLockpick(Object base, Player player) {
        this.base = base;
        this.player = player;
        isToggleableByPlayers = true;
    }

    public abstract void toggle();
    public abstract boolean isNotContainer();

    public void startLockpicking() {
        player.sendMessage(ColorParser.of("<yellow>Attempting to lockpick...").build());
        // "Use" the lockpick they have in their hand
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
        if (Settings.isLockpickingSoundEnabled())
            player.playSound(Settings.getLockpickingSound());
        boolean hitChance;
        Block block;
        Entity entity;
        if (this instanceof ActiveBlockLockpick activeBlockLockpick) {
            entity = null;
            hitChance = Settings.getLockpickChancesForBlocks().get(activeBlockLockpick.getBlock().getType()) > Math.random();
            block = activeBlockLockpick.getBlock();
        } else if (this instanceof ActiveEntityLockpick activeEntityLockpick) {
            block = null;
            hitChance = Settings.getLockpickChancesForEntities().get(activeEntityLockpick.getEntity().getType()) > Math.random();
            entity = activeEntityLockpick.getEntity();
        } else {
            LockpickingManager.deRegisterActiveLockpick(this);
            return;
        }
        Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> {
            if (hitChance) {
                player.sendMessage(ColorParser.of("<green>Lockpicking was successful").build());
                // return lockpick to inventory
                player.getInventory().addItem(SimpleLockpickingAPI.getLockpickItem());
                toggle();
                if (Settings.getSecondsUntilToggleable() > 0) {
                    if (isNotContainer()) {
                        isToggleableByPlayers = false;
                        Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> isToggleableByPlayers = true, Settings.getSecondsUntilToggleable()*20L);
                    }
                }
                if (Settings.getSecondsUntilClosesAgain() > 0 && isNotContainer()) {
                    Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> {
                        toggle();
                        LockpickingManager.deRegisterActiveLockpick(this);
                    }, Settings.getSecondsUntilClosesAgain()*20L);
                } else {
                    LockpickingManager.deRegisterActiveLockpick(this);
                }
            } else {
                player.sendMessage(ColorParser.of("<red>Your lockpick broke").build());
                if (Settings.isLockpickBreakEffectEnabled()) {
                    if (block != null) {
                        player.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);
                    }
                    if (entity != null) {
                        player.getLocation().getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);
                    }
                }
                LockpickingManager.deRegisterActiveLockpick(this);
            }
        }, Settings.getLockpickSeconds()*20L);
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

}
