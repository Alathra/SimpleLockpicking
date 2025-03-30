package io.github.alathra.simplelockpicking;

import io.github.alathra.simplelockpicking.hook.HookManager;
import io.github.alathra.simplelockpicking.command.CommandHandler;
import io.github.alathra.simplelockpicking.config.ConfigHandler;
import io.github.alathra.simplelockpicking.listener.ListenerHandler;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class SimpleLockpicking extends JavaPlugin {
    private static SimpleLockpicking instance;

    // Handlers/Managers
    private ConfigHandler configHandler;
    private HookManager hookManager;
    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;

    // Handlers list (defines order of load/enable/disable)
    private List<? extends Reloadable> handlers;

    public static SimpleLockpicking getInstance() {
        return instance;
    };

    @Override
    public void onLoad() {
        instance = this;

        configHandler = new ConfigHandler(this);
        hookManager = new HookManager(this);
        commandHandler = new CommandHandler(this);
        listenerHandler = new ListenerHandler(this);

        handlers = List.of(
            configHandler,
            hookManager,
            commandHandler,
            listenerHandler
        );

        for (Reloadable handler : handlers)
            handler.onLoad(instance);
    }

    @Override
    public void onEnable() {
        for (Reloadable handler : handlers)
            handler.onEnable(instance);
    }

    @Override
    public void onDisable() {
        for (Reloadable handler : handlers.reversed()) // If reverse doesn't work implement a new List with your desired disable order
            handler.onDisable(instance);
    }

    /**
     * Use to reload the entire plugin.
     */
    public void onReload() {
        onDisable();
        onLoad();
        onEnable();
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    @NotNull
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * Gets hook manager.
     * @return the hook manager
     */
    @NotNull
    public HookManager getHookManager() {
        return hookManager;
    }
}
