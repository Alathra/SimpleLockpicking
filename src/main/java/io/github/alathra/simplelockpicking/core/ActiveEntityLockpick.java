package io.github.alathra.simplelockpicking.core;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.data.EntityGroups;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;


public class ActiveEntityLockpick extends ActiveLockpick {

    private final Entity entity;

    public ActiveEntityLockpick(Entity base, Player player) {
        super(base, player);
        entity = base;
    }

    @Override
    public void toggle() {
        World world = entity.getWorld();
        if (EntityGroups.getChestBoats().contains(entity.getType())) {
            ChestBoat chestBoat = (ChestBoat) entity;
            super.getPlayer().openInventory(chestBoat.getInventory());
        } else if (entity.getType().equals(EntityType.CHEST_MINECART)) {
            StorageMinecart chestMinecart = (StorageMinecart) entity;
            super.getPlayer().openInventory(chestMinecart.getInventory());
        }
    }

    @Override
    public boolean shouldToggleTwice() {
        return false;
    }

    @Override
    public void startLockpicking(Location location) {
    }

    public Entity getEntity() {
        return entity;
    }
}
