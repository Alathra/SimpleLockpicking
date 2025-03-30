package io.github.alathra.simplelockpicking.hook.nexo;

import com.nexomc.nexo.api.NexoItems;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


public class NexoHook extends AbstractHook {

    public NexoHook(SimpleLockpicking plugin) {
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
        return isPluginEnabled(Hook.Nexo.getPluginName());
    }

    public @Nullable ItemStack getLockpickItem() {
        try {
            return Objects.requireNonNull(NexoItems.itemFromId(Settings.getCustomLockpickItemID())).build();
        } catch (NullPointerException e) {
            return null;
        }
    }

}
