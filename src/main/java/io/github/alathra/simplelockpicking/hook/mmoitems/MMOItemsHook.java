package io.github.alathra.simplelockpicking.hook.mmoitems;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;


public class MMOItemsHook extends AbstractHook {

    public MMOItemsHook(SimpleLockpicking plugin) {
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
        if (!Settings.getCustomLockpickItemID().contains(".")) {
            return null;
        }
        String[] inputs = Settings.getCustomLockpickItemID().split("\\.", 2); // split result into TYPE.ID
        return MMOItems.plugin.getItem(inputs[0], inputs[1]);
    }

}
