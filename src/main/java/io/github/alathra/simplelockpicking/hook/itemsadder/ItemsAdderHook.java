package io.github.alathra.simplelockpicking.hook.itemsadder;

import dev.lone.itemsadder.api.CustomStack;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;


public class ItemsAdderHook extends AbstractHook implements Listener {

    public ItemsAdderHook(SimpleLockpicking plugin) {
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
        return isPluginEnabled(Hook.ItemsAdder.getPluginName());
    }

    public @Nullable ItemStack getLockpickItem() {
        CustomStack customStack = CustomStack.getInstance(Settings.getCustomLockpickItemID());
        if (customStack == null) {
            return null;
        }
        return customStack.getItemStack();
    }

}
