package io.github.alathra.simplelockpicking.hook.nexo;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import org.bukkit.event.Listener;


public class NexoHook extends AbstractHook implements Listener {

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

}
