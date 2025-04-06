package io.github.alathra.simplelockpicking.hook.bolt;

import com.destroystokyo.paper.MaterialTags;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.core.ActiveBlockLockpick;
import io.github.alathra.simplelockpicking.core.ActiveEntityLockpick;
import io.github.alathra.simplelockpicking.core.ActiveLockpick;
import io.github.alathra.simplelockpicking.core.LockpickingManager;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import io.github.alathra.simplelockpicking.utility.BlockUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;
import org.popcraft.bolt.BoltPlugin;
import org.popcraft.bolt.protection.BlockProtection;
import org.popcraft.bolt.protection.EntityProtection;
import org.popcraft.bolt.protection.Protection;
import org.popcraft.bolt.util.Permission;

import java.util.Map;


public class BoltHook extends AbstractHook {

    private BoltPlugin boltPlugin;

    public BoltHook(SimpleLockpicking plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
        boltPlugin = (BoltPlugin) Bukkit.getServer().getPluginManager().getPlugin("Bolt");
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.Bolt.getPluginName());
    }

    private @Nullable BlockProtection getBlockProtectionOrNull(Block block) {
        if (!boltPlugin.isProtected(block)) {
            return null;
        }
        BlockProtection protection = boltPlugin.loadProtection(block);
        if (protection == null) {
            if (MaterialTags.DOORS.isTagged(block.getType())) {
                return boltPlugin.loadProtection(block.getRelative(BlockFace.DOWN));
            } else if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
                return boltPlugin.loadProtection(BlockUtil.getConnectedDoubleChest(block));
            } else {
                return null;
            }
        } else {
            return protection;
        }
    }

    private @Nullable EntityProtection getEntityProtectionOrNull(Entity entity) {
        if (!boltPlugin.isProtected(entity)) {
            return null;
        }
        return boltPlugin.loadProtection(entity);
    }

    private boolean hasAccess(Protection protection, Player player) {
        boolean isOwner = protection.getOwner().equals(player.getUniqueId());
        if (isOwner)
            return true;
        return boltPlugin.canAccess(protection, player, Permission.INTERACT, Permission.OPEN, Permission.DEPOSIT, Permission.WITHDRAW);
    }

    private void grantAccess(Protection protection, Player player) {
        protection.getAccess().put("player:" + player.getUniqueId(), "normal");
        boltPlugin.saveProtection(protection);
    }

    private void revokeAccess(Protection protection, Player player) {
        protection.getAccess().remove("player:" + player.getUniqueId());
        boltPlugin.saveProtection(protection);
    }

    public boolean forceAccessIfNeeded(ActiveLockpick activeLockpick) {
        if (!activeLockpick.isContainer()) {
            return false;
        }
        if (activeLockpick instanceof ActiveBlockLockpick activeBlockLockpick) {
            Block block = activeBlockLockpick.getBlock();
            BlockProtection blockProtection = getBlockProtectionOrNull(block);
            Player player = activeBlockLockpick.getPlayer();
            if (blockProtection != null) {
                if (!hasAccess(blockProtection, player)) {
                    grantAccess(blockProtection, player);
                    return true;
                }
            }
        } else if (activeLockpick instanceof ActiveEntityLockpick activeEntityLockpick) {
            Entity entity = activeEntityLockpick.getEntity();
            EntityProtection entityProtection = getEntityProtectionOrNull(entity);
            Player player = activeEntityLockpick.getPlayer();
            if (entityProtection != null) {
                if (!hasAccess(entityProtection, player)) {
                    grantAccess(entityProtection, player);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean revokeAccessIfNeeded(Inventory inventory) {
        for (Map.Entry<Block, Inventory> entry : LockpickingManager.getForcedAccessBlockInventoriies().entrySet()) {
            BlockProtection blockProtection = getBlockProtectionOrNull(entry.getKey());
            if (inventory.getViewers().getFirst() instanceof Player player) {
                if (blockProtection != null) {
                    revokeAccess(blockProtection, player);
                    return true;
                }
            }
        }
        for (Map.Entry<Entity, Inventory> entry : LockpickingManager.getForcedAccessEntityInventories().entrySet()) {
            EntityProtection entityProtection = getEntityProtectionOrNull(entry.getKey());
            if (inventory.getViewers().getFirst() instanceof Player player) {
                if (entityProtection != null) {
                    revokeAccess(entityProtection, player);
                    return true;
                }
            }
        }
        return false;
    }

}
