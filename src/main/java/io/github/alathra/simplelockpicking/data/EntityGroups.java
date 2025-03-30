package io.github.alathra.simplelockpicking.data;

import org.bukkit.entity.EntityType;

import java.util.List;

public class EntityGroups {

    public static List<EntityType> getChestBoats() {
        return List.of(
            EntityType.ACACIA_BOAT,
            EntityType.BAMBOO_CHEST_RAFT,
            EntityType.BIRCH_CHEST_BOAT,
            EntityType.CHERRY_CHEST_BOAT,
            EntityType.DARK_OAK_CHEST_BOAT,
            EntityType.JUNGLE_CHEST_BOAT,
            EntityType.MANGROVE_CHEST_BOAT,
            EntityType.OAK_CHEST_BOAT,
            EntityType.PALE_OAK_CHEST_BOAT,
            EntityType.SPRUCE_CHEST_BOAT
        );
    }
}
