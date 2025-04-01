package io.github.alathra.simplelockpicking.hook.craftbook;

import com.destroystokyo.paper.MaterialTags;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.enginehub.craftbook.CraftBook;
import org.enginehub.craftbook.CraftBookPlayer;
import org.enginehub.craftbook.bukkit.CraftBookPlugin;
import org.enginehub.craftbook.mechanic.CraftBookMechanic;
import org.enginehub.craftbook.mechanics.area.Bridge;
import org.enginehub.craftbook.mechanics.area.Door;
import org.enginehub.craftbook.mechanics.area.Gate;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CraftbookHook extends AbstractHook {

    private CraftBookPlugin craftBookPlugin;
    private Bridge bridge;
    private Door door;
    private Gate gate;

    private List<Material> possibleBridgeMaterials;
    private List<Material> possibleDoorMaterials;
    private List<Material> possibleGateMaterials;

    public CraftbookHook(SimpleLockpicking plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) {
            return;
        }
        ;
        bridge = null;
        door = null;
        gate = null;
        possibleBridgeMaterials = null;
        possibleDoorMaterials = null;
        possibleGateMaterials = null;
        initializeMechanics();
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.CraftBook.getPluginName());
    }

    public void initializeMechanics() {
        for (CraftBookMechanic mechanic : CraftBook.getInstance().getPlatform().getMechanicManager().getLoadedMechanics()) {
            if (mechanic instanceof Bridge) {
                bridge = (Bridge) mechanic;
                possibleBridgeMaterials = findPossibleBridgeMaterials();
            } else if (mechanic instanceof Door) {
                door = (Door) mechanic;
                possibleDoorMaterials = findPossibleDoorMaterials();
            } else if (mechanic instanceof Gate) {
                gate = (Gate) mechanic;
                possibleGateMaterials = findPossibleGateMaterials();
            }
        }
    }

    private List<Material> findPossibleBridgeMaterials() {
        File configFile = CraftBook.getInstance().getPlatform().getWorkingDirectory().resolve("mechanics").resolve("Bridge.yml").toFile();
        YamlConfiguration doorsConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
        List<Material> doorBlocks = new ArrayList<>();
        for (String blockStr : doorsConfig.getStringList("blocks")) {
            //remove "minecraft:" in string
            blockStr = blockStr.substring(10);
            blockStr = blockStr.toUpperCase();
            doorBlocks.add(Material.getMaterial(blockStr));
        }
        return doorBlocks;
    }

    private List<Material> findPossibleDoorMaterials() {
        File configFile = CraftBook.getInstance().getPlatform().getWorkingDirectory().resolve("mechanics").resolve("Door.yml").toFile();
        YamlConfiguration doorsConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
        List<Material> doorBlocks = new ArrayList<>();
        for (String blockStr : doorsConfig.getStringList("blocks")) {
            //remove "minecraft:" in string
            blockStr = blockStr.substring(10);
            blockStr = blockStr.toUpperCase();
            doorBlocks.add(Material.getMaterial(blockStr));
        }
        return doorBlocks;
    }

    private List<Material> findPossibleGateMaterials() {
        File configFile = CraftBook.getInstance().getPlatform().getWorkingDirectory().resolve("mechanics").resolve("Gate.yml").toFile();
        YamlConfiguration doorsConfig = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(configFile);
        List<Material> doorBlocks = new ArrayList<>();
        for (String blockStr : doorsConfig.getStringList("blocks")) {
            //remove "minecraft:" in string
            blockStr = blockStr.substring(10);
            blockStr = blockStr.toUpperCase();
            doorBlocks.add(Material.getMaterial(blockStr));
        }
        return doorBlocks;
    }

    public @Nullable Bridge getBridgeMechanic() {
        return bridge;
    }

    public @Nullable Door getDoorMechanic() {
        return door;
    }

    public @Nullable Gate getGateMechanic() {
        return gate;
    }

    public @Nullable List<Material> getPossibleBridgeMaterials() {
        return possibleBridgeMaterials;
    }

    public @Nullable List<Material> getPossibleDoorMaterials() {
        return possibleDoorMaterials;
    }

    public @Nullable List<Material> getPossibleGateMaterials() {
        return possibleGateMaterials;
    }

    public @Nullable WrappedMechanic checkForMechanic(Block block, CraftBookMechanic mechanic) {
        // Search radius for mechanic sign
        int radius = 10;
        for (int x = -(radius); x <= radius; x++) {
            for (int y = -(radius); y <= radius; y++) {
                for (int z = -(radius); z <= radius; z++) {
                    Block relativeBlock = block.getRelative(x, y, z);
                    if (MaterialTags.SIGNS.isTagged(relativeBlock.getType())) {
                        BlockState blockState = relativeBlock.getState();
                        if (blockState instanceof Sign sign) { // Check if the block state is a Sign
                            switch (mechanic.getMechanicType().id()) {
                                case "bridge":
                                    if (isBridgeSign(sign))
                                        return new WrappedMechanic(bridge, sign);
                                    break;
                                case "door":
                                    if (isDoorSign(sign))
                                        return new WrappedMechanic(door, sign);
                                    break;
                                case "gate":
                                    if (isGateSign(sign))
                                        return new WrappedMechanic(gate, sign);
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public boolean isBridgeSign(Sign sign) {
        if (((TextComponent) sign.getSide(Side.FRONT).line(1)).content().contentEquals("[Bridge]")) {
            return true;
        } else return ((TextComponent) sign.getSide(Side.BACK).line(1)).content().contentEquals("[Bridge]");
    }

    public boolean isDoorSign(Sign sign) {
        String rawLineText = ((TextComponent) sign.getSide(Side.FRONT).line(1)).content();
        if (rawLineText.contentEquals("[Door Up]") || rawLineText.contentEquals("[Door Down]")) {
            return true;
        } else return ((TextComponent) sign.getSide(Side.BACK).line(1)).content().contentEquals("[Door]");
    }

    public boolean isGateSign(Sign sign) {
        if (((TextComponent) sign.getSide(Side.FRONT).line(1)).content().contentEquals("[Gate]")) {
            return true;
        } else return ((TextComponent) sign.getSide(Side.BACK).line(1)).content().contentEquals("[Gate]");
    }

    public CraftBookPlayer getCraftBookPlayer(Player player) {
        return craftBookPlugin.wrapPlayer(player);
    }

}
