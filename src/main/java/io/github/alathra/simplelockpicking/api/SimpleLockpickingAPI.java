package io.github.alathra.simplelockpicking.api;

import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.core.*;
import io.github.alathra.simplelockpicking.hook.Hook;
import io.github.alathra.simplelockpicking.hook.craftbook.WrappedMechanic;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
        Objects.requireNonNull(itemStack).setAmount(amount);
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
    public static boolean isLockItem(ItemStack itemStack) {
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
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveBlockLockpick activeBlockLockpick) {
                if (activeBlockLockpick.getBlock().equals(block)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static @Nullable ActiveBlockLockpick getActiveLockpick(Block block) {
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveBlockLockpick activeBlockLockpick) {
                if (activeBlockLockpick.getBlock().equals(block)) {
                    return activeBlockLockpick;
                }
            }
        }
        return null;
    }

    public static boolean isGettingLockpicked(Entity entity) {
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveEntityLockpick activeEntityLockpick) {
                if (activeEntityLockpick.getEntity().getEntityId() == entity.getEntityId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static @Nullable ActiveEntityLockpick getActiveLockpick(Entity entity) {
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
        for (ActiveLockpick activeLockpick : LockpickingManager.getActiveLockpicks()) {
            if (activeLockpick instanceof ActiveMechanicLockpick activeMechanicLockpick) {
                if (activeMechanicLockpick.getWrappedMechanic().getMechanic().getMechanicType().id().contentEquals(wrappedMechanic.getMechanic().getMechanicType().id())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static @Nullable ActiveMechanicLockpick getActiveLockpick(WrappedMechanic wrappedMechanic) {
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
