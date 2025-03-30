package io.github.alathra.simplelockpicking.crafting;

import io.github.alathra.simplelockpicking.Reloadable;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class CraftingHandler implements Reloadable {

    @Override
    public void onLoad(SimpleLockpicking plugin) {
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        if (Settings.isDefaultLockpickCraftingRecipeEnabled()) {
            loadLockpickRecipe();
        }
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
    }

    public void loadLockpickRecipe() {
        NamespacedKey key = new NamespacedKey(SimpleLockpicking.getInstance(), "lockpick");
        ShapedRecipe lockpickRecipe = new ShapedRecipe(key, SimpleLockpickingAPI.getLockpickItem());
        lockpickRecipe.shape("@@ ", " % ", "%  ");
        lockpickRecipe.setIngredient('@', Material.IRON_INGOT);
        lockpickRecipe.setIngredient('%', Material.STICK);
        SimpleLockpicking.getInstance().getServer().addRecipe(lockpickRecipe);
    }

}
