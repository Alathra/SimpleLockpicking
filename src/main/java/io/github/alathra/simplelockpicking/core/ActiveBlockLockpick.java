package io.github.alathra.simplelockpicking.core;

import com.destroystokyo.paper.MaterialTags;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.data.MaterialGroups;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;

import java.util.List;

public class ActiveBlockLockpick extends ActiveLockpick {

    private final Block block;

    public ActiveBlockLockpick(Block base, Player player) {
        super(base, player);
        block = base;
    }

    @Override
    public void toggle() {
        World world = block.getWorld();
        if (MaterialTags.DOORS.isTagged(block.getType())) {
            BlockState blockState = block.getState();
            Door door = (Door) block.getState().getBlockData();
            final boolean wasOpen = door.isOpen();
            door.setOpen(!door.isOpen());
            blockState.setBlockData(door);
            blockState.update();
            Sound sound;
            if (block.getType().equals(Material.IRON_DOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_IRON_DOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_IRON_DOOR_OPEN;
                }
            } else if (MaterialGroups.getCopperDoors().contains(block.getType())) {
                if (wasOpen) {
                    sound = Sound.BLOCK_COPPER_DOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_COPPER_DOOR_OPEN;
                }
            } else if (block.getType().equals(Material.BAMBOO_DOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_BAMBOO_WOOD_DOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_BAMBOO_WOOD_DOOR_OPEN;
                }
            } else if (block.getType().equals(Material.CHERRY_DOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_CHERRY_WOOD_DOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_CHERRY_WOOD_DOOR_OPEN;
                }
            } else if (block.getType().equals(Material.CRIMSON_DOOR) || block.getType().equals(Material.WARPED_DOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_NETHER_WOOD_DOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_NETHER_WOOD_DOOR_OPEN;
                }
            } else {
                if (wasOpen) {
                    sound = Sound.BLOCK_WOODEN_DOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_WOODEN_DOOR_OPEN;
                }
            }
            if (sound != null) {
                world.playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else if (MaterialTags.TRAPDOORS.isTagged(block.getType())) {
            BlockState blockState = block.getState();
            TrapDoor trapDoor = (TrapDoor) block.getState().getBlockData();
            final boolean wasOpen = trapDoor.isOpen();
            trapDoor.setOpen(!trapDoor.isOpen());
            blockState.setBlockData(trapDoor);
            blockState.update();
            Sound sound;
            if (block.getType().equals(Material.IRON_TRAPDOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_IRON_TRAPDOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_IRON_TRAPDOOR_OPEN;
                }
            } else if (MaterialGroups.getCopperTrapdoors().contains(block.getType())) {
                if (wasOpen) {
                    sound = Sound.BLOCK_COPPER_TRAPDOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_COPPER_TRAPDOOR_OPEN;
                }
            } else if (block.getType().equals(Material.BAMBOO_TRAPDOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN;
                }
            } else if (block.getType().equals(Material.CHERRY_TRAPDOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_CHERRY_WOOD_TRAPDOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_CHERRY_WOOD_TRAPDOOR_OPEN;
                }
            } else if (block.getType().equals(Material.CRIMSON_TRAPDOOR) || block.getType().equals(Material.WARPED_TRAPDOOR)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_NETHER_WOOD_TRAPDOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_NETHER_WOOD_TRAPDOOR_OPEN;
                }
            } else {
                if (wasOpen) {
                    sound = Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE;
                } else {
                    sound = Sound.BLOCK_WOODEN_TRAPDOOR_OPEN;
                }
            }
            if (sound != null) {
                world.playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else if (MaterialTags.FENCE_GATES.isTagged(block.getType())) {
            BlockState blockState = block.getState();
            Gate fenceGate = (Gate) block.getState().getBlockData();
            final boolean wasOpen = fenceGate.isOpen();
            fenceGate.setOpen(!fenceGate.isOpen());
            blockState.setBlockData(fenceGate);
            blockState.update();
            Sound sound;
            if (block.getType().equals(Material.BAMBOO_FENCE_GATE)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE;
                } else {
                    sound = Sound.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN;
                }
            } else if (block.getType().equals(Material.CHERRY_FENCE_GATE)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_CHERRY_WOOD_FENCE_GATE_CLOSE;
                } else {
                    sound = Sound.BLOCK_CHERRY_WOOD_FENCE_GATE_OPEN;
                }
            } else if (block.getType().equals(Material.CRIMSON_FENCE_GATE) || block.getType().equals(Material.WARPED_FENCE_GATE)) {
                if (wasOpen) {
                    sound = Sound.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE;
                } else {
                    sound = Sound.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN;
                }
            } else {
                if (wasOpen) {
                    sound = Sound.BLOCK_FENCE_GATE_CLOSE;
                } else {
                    sound = Sound.BLOCK_FENCE_GATE_OPEN;
                }
            }
            if (sound != null) {
                world.playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else if (block.getType().equals(Material.BARREL)) {
            Barrel barrelBlock = (Barrel) block.getState();
            super.getPlayer().openInventory(barrelBlock.getInventory());
        } else if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
            Chest chestBlock = (Chest) block.getState();
            super.getPlayer().openInventory(chestBlock.getInventory());
        } else if (MaterialTags.SHULKER_BOXES.isTagged(block.getType())) {
            ShulkerBox shulkerBoxBlock = (ShulkerBox) block.getState();
            super.getPlayer().openInventory(shulkerBoxBlock.getInventory());
        }
    }

    @Override
    public boolean isContainer() {
        return List.of(Material.BARREL, Material.CHEST, Material.TRAPPED_CHEST).contains(block.getType()) ||
            MaterialTags.SHULKER_BOXES.isTagged(block.getType());
    }

    @Override
    public boolean isMultiBlock() {
        return false;
    }

    @Override
    public boolean isSuccessful() {
        return Settings.getLockpickChancesForBlocks().get(block.getType()) > Math.random();
    }

    @Override
    public void lockpickBreakEffect() {
        block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, Material.IRON_BLOCK);
    }

    public Block getBlock() {
        return block;
    }
}
