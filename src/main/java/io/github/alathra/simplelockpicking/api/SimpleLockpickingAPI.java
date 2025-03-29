package io.github.alathra.simplelockpicking.api;

import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.Hook;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
}
