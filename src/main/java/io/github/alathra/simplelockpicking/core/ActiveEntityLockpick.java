package io.github.alathra.simplelockpicking.core;

import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.data.EntityGroups;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;


public class ActiveEntityLockpick extends ActiveLockpick {

    private final Entity entity;

    public ActiveEntityLockpick(Entity base, Player player) {
        super(base, player);
        entity = base;
    }

    @Override
    public void toggleBase() {
        if (EntityGroups.getChestBoats().contains(entity.getType())) {
            ChestBoat chestBoat = (ChestBoat) entity;
            super.getPlayer().openInventory(chestBoat.getInventory());
        } else if (entity.getType().equals(EntityType.CHEST_MINECART)) {
            StorageMinecart chestMinecart = (StorageMinecart) entity;
            super.getPlayer().openInventory(chestMinecart.getInventory());
        }
    }

    @Override
    public boolean isContainer() {
        // All relevant entities are containers (chest boats, chest minecarts)
        return true;
    }

    @Override
    public @Nullable Inventory getInventory() {
        if (EntityGroups.getChestBoats().contains(entity.getType())) {
            ChestBoat chestBoat = (ChestBoat) entity;
            return chestBoat.getInventory();
        } else if (entity.getType().equals(EntityType.CHEST_MINECART)) {
            StorageMinecart chestMinecart = (StorageMinecart) entity;
            return chestMinecart.getInventory();
        }
        return null;
    }

    @Override
    public boolean isMultiBlock() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return Settings.getLockpickChancesForEntities().get(entity.getType()) > Math.random();
    }

    @Override
    public void createLockpickBreakEffect() {
        entity.getWorld().playEffect(entity.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);
    }

    public Entity getEntity() {
        return entity;
    }
}
