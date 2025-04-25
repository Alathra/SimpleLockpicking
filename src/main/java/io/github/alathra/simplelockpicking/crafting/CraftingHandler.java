package io.github.alathra.simplelockpicking.crafting;

import io.github.alathra.simplelockpicking.Reloadable;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.data.ItemPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

public class CraftingHandler implements Reloadable {

    private SimpleLockpicking plugin;

    public CraftingHandler(SimpleLockpicking plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad(SimpleLockpicking plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        if (Settings.isDefaultLockpickCraftingRecipeEnabled() && Settings.getItemPlugin().equals(ItemPlugin.NONE)) {
            loadLockpickRecipe();
        }
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
    }

    public static Recipe getLockpickRecipe() {
        NamespacedKey key = new NamespacedKey(SimpleLockpicking.getInstance(), "lockpick");
        ShapedRecipe lockpickRecipe = new ShapedRecipe(key, SimpleLockpickingAPI.getLockpickItem());
        lockpickRecipe.shape("@@ ", " % ", "%  ");
        lockpickRecipe.setIngredient('@', Material.IRON_INGOT);
        lockpickRecipe.setIngredient('%', Material.STICK);
        return lockpickRecipe;
    }

    public void loadLockpickRecipe() {
        plugin.getServer().addRecipe(getLockpickRecipe());
    }

}
