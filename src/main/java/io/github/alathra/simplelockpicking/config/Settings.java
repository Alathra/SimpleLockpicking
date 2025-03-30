package io.github.alathra.simplelockpicking.config;

import com.destroystokyo.paper.MaterialTags;
import io.github.alathra.simplelockpicking.data.EntityGroups;
import io.github.alathra.simplelockpicking.data.ItemPlugin;
import io.github.alathra.simplelockpicking.data.MaterialGroups;
import io.github.alathra.simplelockpicking.utility.Cfg;
import io.github.alathra.simplelockpicking.utility.Logger;
import io.github.milkdrinkers.colorparser.ColorParser;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.intellij.lang.annotations.Subst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Settings {

    public static List<World> getEnabledWorlds() {
        return Cfg.get().getStringList("GeneralSettings.enabledWorlds")
            .stream()
            .map(Bukkit::getWorld)
            .collect(Collectors.toList());
    }

    public static Map<Material, Double> getLockpickChancesForBlocks() {
        Map<Material, Double> lockpickChancesMap = new HashMap<>();
        lockpickChancesMap.put(Material.BARREL, Cfg.get().getDouble("GeneralSettings.LockpickChances.barrels"));
        lockpickChancesMap.put(Material.CHEST, Cfg.get().getDouble("GeneralSettings.LockpickChances.chests"));
        MaterialGroups.getCopperDoors().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.LockpickChances.copperDoors")));
        MaterialGroups.getCopperTrapdoors().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.LockpickChances.copperTrapdoors")));
        MaterialTags.FENCE_GATES.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.LockpickChances.fenceGates")));
        lockpickChancesMap.put(Material.IRON_DOOR, Cfg.get().getDouble("GeneralSettings.LockpickChances.ironDoors"));
        lockpickChancesMap.put(Material.IRON_TRAPDOOR, Cfg.get().getDouble("GeneralSettings.LockpickChances.ironTrapdoors"));
        MaterialTags.SHULKER_BOXES.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.LockpickChances.shulkers")));
        lockpickChancesMap.put(Material.TRAPPED_CHEST, Cfg.get().getDouble("GeneralSettings.LockpickChances.trappedChests"));
        MaterialTags.WOODEN_DOORS.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.LockpickChances.woodenDoors")));
        lockpickChancesMap.put(Material.BAMBOO_DOOR, Cfg.get().getDouble("GeneralSettings.LockpickChances.woodenDoors"));
        MaterialTags.WOODEN_TRAPDOORS.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.LockpickChances.woodenTrapdoors")));
        lockpickChancesMap.put(Material.BAMBOO_TRAPDOOR, Cfg.get().getDouble("GeneralSettings.LockpickChances.woodenTrapdoors"));
        return lockpickChancesMap;
    }

    public static Map<EntityType, Double> getLockpickChancesForEntities() {
        Map<EntityType, Double> lockpickChancesMap = new HashMap<>();
        EntityGroups.getChestBoats().forEach(entityType -> lockpickChancesMap.put(entityType, Cfg.get().getDouble("GeneralSettings.LockpickChances.chestBoats")));
        lockpickChancesMap.put(EntityType.CHEST_MINECART, Cfg.get().getDouble("GeneralSettings.LockpickChances.chestMinecarts"));
        return lockpickChancesMap;
    }

    public static int getLockpickSeconds() {
        return Cfg.get().getOrDefault("GeneralSettings.lockpickSeconds", 5);
    }

    public static int getSecondsUntilClosesAgain() {
        return Cfg.get().getOrDefault("GeneralSettings.secondsUntilClosesAgain", 5);
    }

    public static boolean isDefaultLockpickCraftingRecipeEnabled() {
        return Cfg.get().getOrDefault("GeneralSettings.enableDefaultLockpickCraftingRecipe", false);
    }

    public static boolean isLockpickingSoundEnabled() {
        return Cfg.get().getOrDefault("GeneralSettings.LockpickSound.enabled", false);
    }

    public static Sound getLockpickingSound() {
        @Subst("minecraft:item.flintandsteel.use") String soundID = Cfg.get().getOrDefault("GeneralSettings.LockpickSound.effect", "minecraft:item.flintandsteel.use");
        float volume = Cfg.get().getOrDefault("GeneralSettings.LockpickSound.volume", 1.0).floatValue();
        float pitch = Cfg.get().getOrDefault("GeneralSettings.LockpickSound.pitch", 1.0).floatValue();

        return Sound.sound()
            .type(Key.key(soundID))
            .source(Sound.Source.BLOCK)
            .volume(volume)
            .pitch(pitch)
            .build();
    }

    public static List<Material> getDisabledLockpickingMaterials() {
        List<Material> disabledLockpickingMaterials = new ArrayList<>();
        List<String> raw = Cfg.get().getStringList("GeneralSettings.disableLockpicking");
        raw.forEach(string -> {
            try {
                disabledLockpickingMaterials.add(Material.valueOf(string));
            } catch (IllegalArgumentException ignored) {
            }
        });
        return disabledLockpickingMaterials;
    }

    public static ItemPlugin getItemPlugin() {
        // Default to empty String, no plugin
        String itemPluginString = Cfg.get().getOrDefault("LockpickItem.itemPlugin", "");
        if (itemPluginString.isEmpty() || itemPluginString.equalsIgnoreCase("None")) {
            return ItemPlugin.NONE;
        } else if (itemPluginString.equalsIgnoreCase("ItemsAdder")) {
            return ItemPlugin.ITEMSADDER;
        } else if (itemPluginString.equalsIgnoreCase("Nexo")) {
            return ItemPlugin.NEXO;
        } else if (itemPluginString.equalsIgnoreCase("Oraxen")) {
            return ItemPlugin.ORAXEN;
        } else {
            Logger.get().warn("Invalid 'ItemPlugin' defined in config.yml. Defaulting to none...");
            return ItemPlugin.NONE;
        }
    }

    public static String getCustomLockpickItemID() {
        return Cfg.get().getOrDefault("LockpickItem.customLockItemID", "");
    }

    public static Material getDefaultLockpickItemMaterial() {
        String materialString = Cfg.get().getOrDefault("LockpickItem.DefaultLockpickItem.material", "STICK");
        try {
            return Material.valueOf(materialString);
        } catch (IllegalArgumentException e) {
            return Material.STICK;
        }
    }

    public static int getDefaultLockpickItemCustomModelData() {
        return Cfg.get().getOrDefault("LockpickItem.DefaultLockpickItem.customModelData", 8793);
    }

    public static Component getDefaultLockpickItemDisplayName() {
        return ColorParser.of(
            Cfg.get().getOrDefault("LockpickItem.DefaultLockpickItem.displayName", "<gray><b>Lockpick")
        ).build().decoration(TextDecoration.ITALIC, false);
    }

    public static List<Component> getDefaultLockItemLore() {
        List<String> loreStrings = Cfg.get().getStringList("LockpickItem.DefaultLockpickItem.lore");
        List<Component> loreComponents = new ArrayList<>();
        if (loreStrings == null || loreStrings.isEmpty()) {
            return List.of(Component.empty());
        } else {
            for (String line : loreStrings) {
                loreComponents.add(ColorParser.of(line).build().decoration(TextDecoration.ITALIC, false));
            }
        }
        return loreComponents;
    }

    public static boolean areCraftBookGatesLockpickable() {
        return Cfg.get().getOrDefault("CraftBookCompatibility.Gate.enabled", false);
    }

    public static double getCraftBookGateChance() {
        return Cfg.get().getOrDefault("CraftBookCompatibility.Gate.chance", 0.2);
    }

    public static boolean areCraftBookDoorsLockpickable() {
        return Cfg.get().getOrDefault("CraftBookCompatibility.Door.enabled", false);
    }

    public static double getCraftBookDoorChance() {
        return Cfg.get().getOrDefault("CraftBookCompatibility.Door.chance", 0.2);
    }

    public static boolean areCraftBookBridgesLockpickable() {
        return Cfg.get().getOrDefault("CraftBookCompatibility.Bridge.enabled", false);
    }

    public static double getCraftBookBridgeChance() {
        return Cfg.get().getOrDefault("CraftBookCompatibility.Bridge.chance", 0.2);
    }


}
