package io.github.alathra.simplelockpicking.data;

import org.bukkit.Material;

import java.util.List;

public class MaterialGroups {

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
