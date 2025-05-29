package io.github.alathra.simplelockpicking.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.Reloadable;

public class CommandHandler implements Reloadable {
    private final SimpleLockpicking plugin;

    public CommandHandler(SimpleLockpicking plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(SimpleLockpicking plugin) {
        CommandAPI.onLoad(
            new CommandAPIBukkitConfig(plugin)
                .shouldHookPaperReload(true)
                .silentLogs(true)
                .beLenientForMinorVersions(true)
        );
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        CommandAPI.onEnable();

        // Register commands here
        new SimpleLockpickingCommand();
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
        CommandAPI.getRegisteredCommands().forEach(registeredCommand -> CommandAPI.unregister(registeredCommand.namespace() + ':' + registeredCommand.commandName(), true));
        CommandAPI.onDisable();
    }
}