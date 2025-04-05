package io.github.alathra.simplelockpicking.core;

import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.hook.craftbook.WrappedMechanic;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

public class ActiveMechanicLockpick extends ActiveLockpick {

    private final WrappedMechanic wrappedMechanic;
    private final Block clickedBlock;

    public ActiveMechanicLockpick(WrappedMechanic base, Player player, Block clickedBlock) {
        super(base, player);
        wrappedMechanic = base;
        this.clickedBlock = clickedBlock;
    }

    @Override
    public void toggleBase() {
        wrappedMechanic.toggle();
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public @Nullable Inventory getInventory() {
        return null;
    }

    @Override
    public boolean isMultiBlock() {
        return true;
    }

    @Override
    public boolean isSuccessful() {
        return switch (wrappedMechanic.getMechanic().getMechanicType().id()) {
            case "bridge" -> Settings.getCraftBookBridgeChance() > Math.random();
            case "door" -> Settings.getCraftBookDoorChance() > Math.random();
            case "gate" -> Settings.getCraftBookGateChance() > Math.random();
            default -> false;
        };
    }

    @Override
    public void createLockpickBreakEffect() {
        clickedBlock.getWorld().playEffect(clickedBlock.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);
    }

    public WrappedMechanic getWrappedMechanic() {
        return wrappedMechanic;
    }

    public Block getClickedBlock() {
        return clickedBlock;
    }
}
