package io.github.alathra.simplelockpicking.data;

import org.bukkit.Material;

import java.util.List;

public class MaterialGroups {
    public static List<Material> getChestBoats() {
        return List.of(
            Material.ACACIA_BOAT,
            Material.BAMBOO_CHEST_RAFT,
            Material.BIRCH_CHEST_BOAT,
            Material.CHERRY_CHEST_BOAT,
            Material.DARK_OAK_CHEST_BOAT,
            Material.JUNGLE_CHEST_BOAT,
            Material.MANGROVE_CHEST_BOAT,
            Material.OAK_CHEST_BOAT,
            Material.PALE_OAK_CHEST_BOAT,
            Material.SPRUCE_CHEST_BOAT
        );
    }

    public static List<Material> getCopperDoors() {
        return List.of(
            Material.COPPER_DOOR,
            Material.EXPOSED_COPPER_DOOR,
            Material.WEATHERED_COPPER_DOOR,
            Material.WAXED_COPPER_DOOR,
            Material.WAXED_COPPER_DOOR,
            Material.WAXED_WEATHERED_COPPER_DOOR
        );
    }

    public static List<Material> getCopperTrapdoors() {
        return List.of(
            Material.COPPER_TRAPDOOR,
            Material.EXPOSED_COPPER_TRAPDOOR,
            Material.WEATHERED_COPPER_TRAPDOOR,
            Material.WAXED_COPPER_TRAPDOOR,
            Material.WAXED_EXPOSED_COPPER_TRAPDOOR,
            Material.WAXED_WEATHERED_COPPER_TRAPDOOR
        );
    }
}
