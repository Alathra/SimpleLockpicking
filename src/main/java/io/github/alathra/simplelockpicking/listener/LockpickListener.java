package io.github.alathra.simplelockpicking.listener;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.core.ActiveBlockLockpick;
import io.github.alathra.simplelockpicking.core.ActiveEntityLockpick;
import io.github.alathra.simplelockpicking.core.ActiveLockpick;
import io.github.alathra.simplelockpicking.core.LockpickingManager;
import io.github.alathra.simplelockpicking.data.EntityGroups;
import io.github.alathra.simplelockpicking.data.MaterialGroups;
import io.github.alathra.simplelockpicking.data.Permissions;
import io.github.alathra.simplelockpicking.hook.Hook;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class LockpickListener implements Listener {

    private final SimpleLockpicking plugin;

    public LockpickListener(SimpleLockpicking plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLockpickBlock(PlayerInteractEvent event) {
        // Standard checks
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (event.getHand() == null) {
            return;
        }
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        Player player = event.getPlayer();

        // Check for toggling a lockpicked non-container
        ActiveBlockLockpick activeBlockLockpick = LockpickingManager.getActiveLockpick(block);
        if (activeBlockLockpick != null) {
            if (!activeBlockLockpick.isToggleableByPlayers()) {
                player.sendMessage(ColorParser.of("<red>This has recently been lockpicked").build());
                event.setCancelled(true);
                return;
            }
        }

        // Checking for lockpick
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (!player.hasPermission(Permissions.getLockpickPermission()) && !player.hasPermission(Permissions.getAdminPermission())) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().isSimilar(SimpleLockpickingAPI.getLockpickItem())) {
            return;
        }
        if (!Settings.getEnabledWorlds().contains(player.getWorld())) {
            return;
        }
        if (Settings.getDisabledLockpickingBlocks().contains(block.getType())) {
            return;
        }
        if (!MaterialGroups.lockpickableBlocks().contains(block.getType())) {
            return;
        }

        // ATTEMPT TO ACTIVATE LOCKPICK
        if (LockpickingManager.isGettingLockpicked(block)) {
            player.sendMessage(ColorParser.of("<red>You are already lockpicking this").build());
            return;
        }
        ActiveLockpick activeLockpick = new ActiveBlockLockpick(block, player);
        // If fails to register, abort
        if (!LockpickingManager.registerActiveLockpick(activeLockpick)) {
            return;
        }
        if (!activeLockpick.isNotContainer()) {
            if (Hook.Towny.isLoaded()) {
                if (Hook.getTownyHook().isLocationInOwnTown(block.getLocation(), player)) {
                    player.sendMessage(ColorParser.of("<red>You cannot lockpick containers in your own town").build());
                    LockpickingManager.deRegisterActiveLockpick(activeLockpick);
                    return;
                } else if (Hook.getTownyHook().isLocationInAnyTown(block.getLocation())) {
                    player.sendMessage(ColorParser.of("<red>You cannot lockpick containers within town claims").build());
                    LockpickingManager.deRegisterActiveLockpick(activeLockpick);
                    return;
                }
            }
        }
        // Initiate lockpicking
        activeLockpick.startLockpicking();
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLockpickEntity(PlayerInteractEntityEvent event) {
        if (event.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();

        // Checking for lockpick
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        if (!player.hasPermission(Permissions.getLockpickPermission()) && !player.hasPermission(Permissions.getAdminPermission())) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().isSimilar(SimpleLockpickingAPI.getLockpickItem())) {
            return;
        }
        if (!Settings.getEnabledWorlds().contains(player.getWorld())) {
            return;
        }
        if (Settings.getDisabledLockpickingEntities().contains(entity.getType())) {
            return;
        }
        if (!EntityGroups.getLockpickableEntities().contains(entity.getType())) {
            return;
        }

        // ATTEMPT TO ACTIVATE LOCKPICK
        if (LockpickingManager.isGettingLockpicked(entity)) {
            player.sendMessage(ColorParser.of("<red>You are already lockpicking this").build());
            return;
        }
        ActiveLockpick activeLockpick = new ActiveEntityLockpick(entity, player);
        // If fails to register, abort
        if (!LockpickingManager.registerActiveLockpick(activeLockpick)) {
            return;
        }
        if (!activeLockpick.isNotContainer()) {
            if (Hook.Towny.isLoaded()) {
                if (Hook.getTownyHook().isLocationInOwnTown(entity.getLocation(), player)) {
                    player.sendMessage(ColorParser.of("<red>You cannot lockpick containers in your own town").build());
                    LockpickingManager.deRegisterActiveLockpick(activeLockpick);
                    return;
                } else if (Hook.getTownyHook().isLocationInAnyTown(entity.getLocation())) {
                    player.sendMessage(ColorParser.of("<red>You cannot lockpick containers within town claims").build());
                    LockpickingManager.deRegisterActiveLockpick(activeLockpick);
                    return;
                }
            }
        }
        // Initiate lockpicking
        activeLockpick.startLockpicking();
        event.setCancelled(true);
    }

}
