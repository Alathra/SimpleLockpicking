package io.github.alathra.simplelockpicking.hook.oraxen;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import org.bukkit.event.Listener;


public class OraxenHook extends AbstractHook implements Listener {

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

}
