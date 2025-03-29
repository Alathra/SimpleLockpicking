package io.github.alathra.simplelockpicking.command;

import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.IntegerArgument;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Class containing the code for the example command.
 */
class SimpleLockpickingCommand {

    protected SimpleLockpickingCommand() {
        new CommandAPICommand("simplelockpicking")
            .withFullDescription("Simplelockpicking commands.")
            .withShortDescription("Simplelockpicking commands.")
            .withPermission(SimpleLockpicking.ADMIN_PERMISSION)
            .withSubcommands(
                GetLockCommand()
            )
            .executes(this::helpMenu)
            .register();
    }

    private void helpMenu(CommandSender sender, CommandArguments args) {
        sender.sendMessage(ColorParser.of("<yellow>SimpleLockpicking Commands:").build());
        sender.sendMessage(ColorParser.of("<yellow>/simplelockpicking getlockpick <green>Spawn a lockpick item").build());
    }

    private CommandAPICommand GetLockCommand() {
        return new CommandAPICommand("getlockpick")
            .withFullDescription("Gives yourself the lockpick item.")
            .withShortDescription("Gives lockpick item.")
            .withPermission(SimpleLockpicking.ADMIN_PERMISSION)
            .withOptionalArguments(
                new IntegerArgument("amount")
                    .replaceSuggestions(ArgumentSuggestions.strings("64", "32", "16"))
            )
            .executesPlayer((Player sender, CommandArguments args) -> {
                Integer amount = (Integer) args.get("amount");
                if (amount == null) {
                    sender.getInventory().addItem(SimpleLockpickingAPI.getLockpickItem(1));
                    return;
                }
                sender.getInventory().addItem(SimpleLockpickingAPI.getLockpickItem(amount));
            });
    }
}