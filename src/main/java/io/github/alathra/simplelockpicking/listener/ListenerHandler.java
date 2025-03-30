package io.github.alathra.simplelockpicking.listener;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.Reloadable;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ListenerHandler implements Reloadable {
    private final SimpleLockpicking plugin;
    private final List<Listener> listeners = new ArrayList<>();

    public ListenerHandler(SimpleLockpicking plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(SimpleLockpicking plugin) {
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        listeners.clear(); // Clear the list to avoid duplicate listeners when reloading the plugin
        listeners.add(new CraftingListener());
        listeners.add(new LockpickListener(plugin));

        // Register listeners here
        for (Listener listener : listeners) {
            plugin.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
    }
}
