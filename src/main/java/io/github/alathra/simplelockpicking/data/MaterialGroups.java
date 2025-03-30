package io.github.alathra.simplelockpicking.data;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;

import java.util.ArrayList;
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

    public static List<Material> lockpickableBlocks() {
        List<Material> lockpickableBlocks = new ArrayList<>();
        lockpickableBlocks.add(Material.BARREL);
        lockpickableBlocks.addAll(MaterialTags.DOORS.getValues());
        lockpickableBlocks.addAll(List.of(Material.CHEST, Material.TRAPPED_CHEST));
        lockpickableBlocks.addAll(MaterialTags.FENCE_GATES.getValues());
        lockpickableBlocks.addAll(MaterialTags.SHULKER_BOXES.getValues());
        lockpickableBlocks.addAll(MaterialTags.TRAPDOORS.getValues());
        return lockpickableBlocks;
    }
}
