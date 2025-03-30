package io.github.alathra.simplelockpicking.hook.oraxen;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;


public class OraxenHook extends AbstractHook {

    public OraxenHook(SimpleLockpicking plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.Oraxen.getPluginName());
    }

    public @Nullable ItemStack getLockpickItem() {
        if (OraxenItems.exists(Settings.getCustomLockpickItemID())) {
            return (OraxenItems.getItemById(Settings.getCustomLockpickItemID()).build());
        }
        return null;
    }

}
