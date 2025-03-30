package io.github.alathra.simplelockpicking.core;

import com.destroystokyo.paper.MaterialTags;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.api.SimpleLockpickingAPI;
import io.github.alathra.simplelockpicking.config.Settings;
import io.github.alathra.simplelockpicking.data.MaterialGroups;
import io.github.milkdrinkers.colorparser.ColorParser;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Door;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            Door door = (Door) block.getState();
            final boolean wasOpen = door.isOpen();
            door.setOpen(!door.isOpen());
            Sound sound;
            if (block.getType().equals(Material.IRON_DOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_IRON_DOOR_CLOSE;
                sound = Sound.BLOCK_IRON_DOOR_OPEN;
            } else if (MaterialGroups.getCopperDoors().contains(block.getType())) {
                if (wasOpen)
                    sound = Sound.BLOCK_COPPER_DOOR_CLOSE;
                sound = Sound.BLOCK_COPPER_DOOR_OPEN;
            } else if (block.getType().equals(Material.BAMBOO_DOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_BAMBOO_WOOD_DOOR_CLOSE;
                sound = Sound.BLOCK_BAMBOO_WOOD_DOOR_OPEN;
            } else if (block.getType().equals(Material.CHERRY_DOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_CHERRY_WOOD_DOOR_CLOSE;
                sound = Sound.BLOCK_CHERRY_WOOD_DOOR_OPEN;
            } else if (block.getType().equals(Material.CRIMSON_DOOR) || block.getType().equals(Material.WARPED_DOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_NETHER_WOOD_DOOR_CLOSE;
                sound = Sound.BLOCK_NETHER_WOOD_DOOR_OPEN;
            } else {
                if (wasOpen)
                    sound = Sound.BLOCK_WOODEN_DOOR_CLOSE;
                sound = Sound.BLOCK_WOODEN_DOOR_OPEN;
            }
            if (sound != null) {
                world.playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else if (MaterialTags.TRAPDOORS.isTagged(block.getType())) {
            TrapDoor trapdoor = (TrapDoor) block.getState();
            final boolean wasOpen = trapdoor.isOpen();
            trapdoor.setOpen(!trapdoor.isOpen());
            Sound sound;
            if (block.getType().equals(Material.IRON_TRAPDOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_IRON_TRAPDOOR_CLOSE;
                sound = Sound.BLOCK_IRON_TRAPDOOR_OPEN;
            } else if (MaterialGroups.getCopperTrapdoors().contains(block.getType())) {
                if (wasOpen)
                    sound = Sound.BLOCK_COPPER_TRAPDOOR_CLOSE;
                sound = Sound.BLOCK_COPPER_TRAPDOOR_OPEN;
            } else if (block.getType().equals(Material.BAMBOO_TRAPDOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_BAMBOO_WOOD_TRAPDOOR_CLOSE;
                sound = Sound.BLOCK_BAMBOO_WOOD_TRAPDOOR_OPEN;
            } else if (block.getType().equals(Material.CHERRY_TRAPDOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_CHERRY_WOOD_TRAPDOOR_CLOSE;
                sound = Sound.BLOCK_CHERRY_WOOD_TRAPDOOR_OPEN;
            } else if (block.getType().equals(Material.CRIMSON_TRAPDOOR) || block.getType().equals(Material.WARPED_TRAPDOOR)) {
                if (wasOpen)
                    sound = Sound.BLOCK_NETHER_WOOD_TRAPDOOR_CLOSE;
                sound = Sound.BLOCK_NETHER_WOOD_TRAPDOOR_OPEN;
            } else {
                if (wasOpen)
                    sound = Sound.BLOCK_WOODEN_TRAPDOOR_CLOSE;
                sound = Sound.BLOCK_WOODEN_TRAPDOOR_OPEN;
            }
            if (sound != null) {
                world.playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else if (MaterialTags.FENCE_GATES.isTagged(block.getType())) {
            Gate gate = (Gate) block.getState();
            final boolean wasOpen = gate.isOpen();
            gate.setOpen(!gate.isOpen());
            Sound sound;
            if (block.getType().equals(Material.BAMBOO_FENCE_GATE)) {
                if (wasOpen)
                    sound = Sound.BLOCK_BAMBOO_WOOD_FENCE_GATE_CLOSE;
                sound = Sound.BLOCK_BAMBOO_WOOD_FENCE_GATE_OPEN;
            } else if (block.getType().equals(Material.CHERRY_FENCE_GATE)) {
                if (wasOpen)
                    sound = Sound.BLOCK_CHERRY_WOOD_FENCE_GATE_CLOSE;
                sound = Sound.BLOCK_CHERRY_WOOD_FENCE_GATE_OPEN;
            } else if (block.getType().equals(Material.CRIMSON_FENCE_GATE) || block.getType().equals(Material.WARPED_FENCE_GATE)) {
                if (wasOpen)
                    sound = Sound.BLOCK_NETHER_WOOD_FENCE_GATE_CLOSE;
                sound = Sound.BLOCK_NETHER_WOOD_FENCE_GATE_OPEN;
            } else {
                if (wasOpen)
                    sound = Sound.BLOCK_FENCE_GATE_CLOSE;
                sound = Sound.BLOCK_FENCE_GATE_OPEN;
            }
            if (sound != null) {
                world.playSound(block.getLocation(), sound, SoundCategory.BLOCKS, 1.0f, 1.0f);
            }
        } else if (block.getType().equals(Material.BARREL)) {
            org.bukkit.block.Barrel barrelBlock = (org.bukkit.block.Barrel) block;
            super.getPlayer().openInventory(barrelBlock.getInventory());
            world.playSound(block.getLocation(), Sound.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        } else if (block.getType().equals(Material.CHEST) || block.getType().equals(Material.TRAPPED_CHEST)) {
            org.bukkit.block.Chest chestBlock = (org.bukkit.block.Chest) block;
            super.getPlayer().openInventory(chestBlock.getInventory());
            world.playSound(block.getLocation(), Sound.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        } else if (MaterialTags.SHULKER_BOXES.isTagged(block.getType())) {
            org.bukkit.block.ShulkerBox shulkerBoxBlock = (org.bukkit.block.ShulkerBox) block.getState();
            super.getPlayer().openInventory(shulkerBoxBlock.getInventory());
            world.playSound(block.getLocation(), Sound.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        }
    }

    public boolean shouldToggleTwice() {
        return List.of(Material.BARREL, Material.CHEST, Material.TRAPPED_CHEST).contains(block.getType()) ||
            MaterialTags.SHULKER_BOXES.isTagged(block.getType());
    }

    @Override
    public void startLockpicking(Location location) {
        Player player = super.getPlayer();
        player.sendMessage(ColorParser.of("<yellow>Attempting to lockpick...").build());
        // "Use" the lockpick they have in their hand
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount()-1);
        if (Settings.isLockpickingSoundEnabled())
            player.playSound(Settings.getLockpickingSound());
        Bukkit.getScheduler().runTaskLater(SimpleLockpicking.getInstance(), () -> {
            if (Settings.getLockpickChancesForBlocks().get(block.getType()) > Math.random()) {
                player.sendMessage("<green>Lockpicking was successful");
                // return lockpick to inventory
                player.getInventory().addItem(SimpleLockpickingAPI.getLockpickItem());
                toggle();
            } else {
                player.sendMessage("<red>Your lockpick broke");
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 0.1F);
                player.getLocation().getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, SimpleLockpickingAPI.getLockpickItem().getType());
            }
        }, Settings.getLockpickSeconds()*20L);
    }

    public Block getBlock() {
        return block;
    }
}
