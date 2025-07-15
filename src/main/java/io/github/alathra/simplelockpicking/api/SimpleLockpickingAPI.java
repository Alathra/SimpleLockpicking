package io.github.alathra.simplelockpicking.api;

import com.destroystokyo.paper.MaterialTags;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.core.*;
import io.github.alathra.simplelockpicking.hook.Hook;
import io.github.alathra.simplelockpicking.hook.craftbook.WrappedMechanic;
import io.github.alathra.simplelockpicking.utility.Logger;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

public class SimpleLockpickingAPI {

    /**
     * Get the lockpick item
     * @param amount The amount of lockpick items in the resulting itemstack
     * @return The lockpick item from the specified item plugin or the default (native) lockpick item
     */
    public static ItemStack getLockpickItem(int amount) {
        ItemStack itemStack;
        switch (Settings.getItemPlugin()) {
            case ITEMSADDER -> {
                if (Hook.ItemsAdder.isLoaded()) {
                    itemStack = Hook.getItemsAdderHook().getLockpickItem();
                } else {
                    itemStack = getDefaultLockItem();
                }
            }
            case MMOITEMS -> {
                if (Hook.MMOItems.isLoaded()) {
                    itemStack = Hook.getMMOItemsHook().getLockpickItem();
                } else {
                    itemStack = getDefaultLockItem();
                }
            }
            case NEXO -> {
                if (Hook.Nexo.isLoaded()) {
                    itemStack = Hook.getNexoHook().getLockpickItem();
                } else {
                    itemStack = getDefaultLockItem();
                }
            }
            case ORAXEN -> {
                if (Hook.Oraxen.isLoaded()) {
                    itemStack = Hook.getOraxenHook().getLockpickItem();
                } else {
                    itemStack = getDefaultLockItem();
                }
            }
            default -> {itemStack = getDefaultLockItem();}
        }
        if (itemStack == null) {
            Logger.get().error("Lockpick item failed to load! If you are using an item plugin make sure the ID is correct");
            Logger.get().warn("Loading default lockpick item...");
            return getLockpickItem();
        }
        itemStack.setAmount(amount);
        return itemStack;
    }

    /**
     * Get the lockpick item
     * @return The lockpick item from the specified item plugin or the default (native) lock item
     */
    public static ItemStack getLockpickItem() {
        return getLockpickItem(1);
    }

    /**
     * Get if the provided ItemStack is a lockpick
     * @return If the ItemStack consists of lockpick items
     */
    public static boolean isLockpickItem(ItemStack itemStack) {
        return itemStack.isSimilar(getLockpickItem());
    }

    /**
     * Get the default lockpick item
     * @return The default (native) lockpick item if no item plugin is specified
     */
    private static ItemStack getDefaultLockItem() {
        ItemStack lockItem = new ItemStack(Settings.getDefaultLockpickItemMaterial());
        ItemMeta lockMeta = lockItem.getItemMeta();
        lockMeta.displayName(Settings.getDefaultLockpickItemDisplayName());
        lockMeta.lore(Settings.getDefaultLockItemLore());
        lockMeta.setCustomModelData(Settings.getDefaultLockpickItemCustomModelData());
        lockItem.setItemMeta(lockMeta);
        return lockItem;
    }

    public static boolean isGettingLockpicked(Block block) {
        return getActiveLockpickOrNull(block) != null;
    }

    public static @Nullable ActiveBlockLockpick getActiveLockpickOrNull(Block block) {
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveBlockLockpick activeBlockLockpick) {
                if (activeBlockLockpick.getBlock().equals(block)) {
                    return activeBlockLockpick;
                }
                if (MaterialTags.DOORS.isTagged(block.getType())) {
                    Door door = (Door) block.getBlockData();
                    Block otherHalf;
                    if(door.getHalf().equals(Bisected.Half.TOP)) {
                        otherHalf = block.getRelative(BlockFace.DOWN);
                    } else {
                        otherHalf = block.getRelative(BlockFace.UP);
                    }
                    if (activeBlockLockpick.getBlock().equals(otherHalf)) {
                        return activeBlockLockpick;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isGettingLockpicked(Entity entity) {
        return getActiveLockpickOrNull(entity) != null;
    }

    public static @Nullable ActiveEntityLockpick getActiveLockpickOrNull(Entity entity) {
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveEntityLockpick activeEntityLockpick) {
                if (activeEntityLockpick.getEntity().equals(entity)) {
                    if (activeEntityLockpick.getEntity().getEntityId() == entity.getEntityId()) {
                        return activeEntityLockpick;
                    }
                }
            }
        }
        return null;
    }

    public static boolean isGettingLockpicked(WrappedMechanic wrappedMechanic) {
        return getActiveLockpickOrNull(wrappedMechanic) != null;
    }

    public static @Nullable ActiveMechanicLockpick getActiveLockpickOrNull(WrappedMechanic wrappedMechanic) {
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveMechanicLockpick activeMechanicLockpick) {
                if (activeMechanicLockpick.getWrappedMechanic().getMechanic().getMechanicType().id().contentEquals(wrappedMechanic.getMechanic().getMechanicType().id())) {
                    return activeMechanicLockpick;
                }
            }
        }
        return null;
    }
}
