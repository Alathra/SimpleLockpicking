package io.github.alathra.simplelockpicking.listener;

import io.github.alathra.simplelockpicking.crafting.CraftingHandler;
import io.github.alathra.simplelockpicking.data.Permissions;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftingListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void CraftListener(CraftItemEvent event) {
        if (event.getCurrentItem() == null) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.hasPermission(Permissions.getAdminPermission())) {
            return;
        }
        if (event.getRecipe().equals(CraftingHandler.getLockpickRecipe())) {
            player = (Player) event.getWhoClicked();
            if (!player.hasPermission(Permissions.getCraftingPermission())) {
                event.setCancelled(true);
                player.sendMessage(ColorParser.of("<red>You do not have permission to craft lockpicks").build());
            }
        }
    }
}
