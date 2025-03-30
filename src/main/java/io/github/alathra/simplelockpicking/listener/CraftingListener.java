package io.github.alathra.simplelockpicking.listener;

import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.data.Permissions;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class CraftingListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void CraftListener(CraftItemEvent e) {
        if (e.getCurrentItem() == null) {
            return;
        }
        if (!(e.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.hasPermission(Permissions.getAdminPermission())) {
            return;
        }
        if (e.getCurrentItem().isSimilar(SimpleLockpickingAPI.getLockpickItem())) {
            Player p = (Player) e.getWhoClicked();
            if (!p.hasPermission(Permissions.getCraftingPermission())) {
                e.setCancelled(true);
                p.sendMessage(ColorParser.of("<red>You do not have permission to craft lockpicks").build());
            }
        }
    }
}
