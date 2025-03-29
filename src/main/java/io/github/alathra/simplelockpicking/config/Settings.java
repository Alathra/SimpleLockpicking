package io.github.alathra.simplelockpicking.config;

import com.destroystokyo.paper.MaterialTags;
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

    public static Map<Material, Double> getLockpickChances() {
        Map<Material, Double> lockpickChancesMap = new HashMap<>();
        lockpickChancesMap.put(Material.BARREL, Cfg.get().getDouble("GeneralSettings.lockpickChances.barrels"));
        MaterialGroups.getChestBoats().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.chestBoats")));
        lockpickChancesMap.put(Material.CHEST, Cfg.get().getDouble("GeneralSettings.lockpickChances.chests"));
        MaterialGroups.getCopperDoors().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.copperDoors")));
        MaterialGroups.getCopperTrapdoors().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.copperTrapdoors")));
        MaterialTags.FENCE_GATES.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.fenceGates")));
        lockpickChancesMap.put(Material.IRON_DOOR, Cfg.get().getDouble("GeneralSettings.lockpickChances.ironDoors"));
        lockpickChancesMap.put(Material.IRON_TRAPDOOR, Cfg.get().getDouble("GeneralSettings.lockpickChances.ironTrapdoors"));
        MaterialTags.SHULKER_BOXES.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.shulkers")));
        lockpickChancesMap.put(Material.TRAPPED_CHEST, Cfg.get().getDouble("GeneralSettings.lockpickChances.trappedChests"));
        MaterialTags.WOODEN_DOORS.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.woodenDoors")));
        lockpickChancesMap.put(Material.BAMBOO_DOOR, Cfg.get().getDouble("GeneralSettings.lockpickChances.woodenDoors"));
        MaterialTags.WOODEN_TRAPDOORS.getValues().forEach(material -> lockpickChancesMap.put(material, Cfg.get().getDouble("GeneralSettings.lockpickChances.woodenTrapdoors")));
        lockpickChancesMap.put(Material.BAMBOO_TRAPDOOR, Cfg.get().getDouble("GeneralSettings.lockpickChances.woodenTrapdoors"));
        return lockpickChancesMap;
    }

    public static int getSecondsUntilClosesAgain() {
        return Cfg.get().getInt("GeneralSettings.secondsUntilClosesAgain");
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
        String materialString = Cfg.get().getOrDefault("LockpickItem.DefaultLockItem.material", "STICK");
        try {
            return Material.valueOf(materialString);
        } catch (IllegalArgumentException e) {
            return Material.STICK;
        }
    }

    public static int getDefaultLockpickItemCustomModelData() {
        return Cfg.get().getOrDefault("LockpickItem.DefaultLockItem.customModelData", 8793);
    }

    public static Component getDefaultLockpickItemDisplayName() {
        return ColorParser.of(
            Cfg.get().getOrDefault("LockpickItem.DefaultLockItem.displayName", "<gray><b>Lockpick")
        ).build().decoration(TextDecoration.ITALIC, false);
    }

    public static List<Component> getDefaultLockItemLore() {
        List<String> loreStrings = Cfg.get().getStringList("LockpickItem.DefaultLockItem.lore");
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

    public static boolean isLockpickingSoundEnabled() {
        return Cfg.get().getOrDefault("LockpickItem.sound.enabled", false);
    }

    public static Sound getLockpickingSound() {
        @Subst("minecraft:item.flintandsteel.use") String soundID = Cfg.get().getOrDefault("LockpickItem.sound.effect", "minecraft:item.flintandsteel.use");
        float volume = Cfg.get().getOrDefault("LockpickItem.sound.volume", 1.0).floatValue();
        float pitch = Cfg.get().getOrDefault("LockpickItem.sound.pitch", 1.0).floatValue();

        return Sound.sound()
            .type(Key.key(soundID))
            .source(Sound.Source.BLOCK)
            .volume(volume)
            .pitch(pitch)
            .build();
    }
}
