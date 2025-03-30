package io.github.alathra.simplelockpicking.listener;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.core.ActiveBlockLockpick;
import io.github.alathra.simplelockpicking.core.ActiveLockpick;
import io.github.alathra.simplelockpicking.core.LockpickingManager;
import io.github.alathra.simplelockpicking.data.Permissions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LockpickListener implements Listener {

    private final SimpleLockpicking plugin;
    private final LockpickingManager lockpickingManager;

    public LockpickListener(SimpleLockpicking plugin) {
        this.plugin = plugin;
        lockpickingManager = plugin.getLockpickingManager();
    }

    public void onPlayerLockpickBlock(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }
        if (!event.getPlayer().isSneaking()) {
            return;
        }
        Player player = event.getPlayer();
        if (!player.hasPermission(Permissions.getLockpickPermission()) && !player.hasPermission(Permissions.getAdminPermission())) {
            return;
        }
        if (!player.getInventory().getItemInMainHand().isSimilar(SimpleLockpickingAPI.getLockpickItem())) {
            return;
        }
        if (!Settings.getEnabledWorlds().contains(player.getWorld())) {
            return;
        }
        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }
        if (Settings.getDisabledLockpickingMaterials().contains(block.getType())) {
            return;
        }
        // block is already getting lockpicked
        if (lockpickingManager.isGettingLockpicked(block)) {
            return;
        }

        ActiveLockpick activeLockpick = new ActiveBlockLockpick(block, player);
        // If fails to register, abort
        if (!lockpickingManager.registerActiveLockpick(activeLockpick)) {
            return;
        }

    }

}
