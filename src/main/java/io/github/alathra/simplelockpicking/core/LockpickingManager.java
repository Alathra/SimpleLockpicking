package io.github.alathra.simplelockpicking.core;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LockpickingManager {

    private static final Set<ActiveLockpick> activeLockpicks = new HashSet<>();
    private static final Map<Block, Inventory> forcedAccessBlockInventoriies = new HashMap<>();
    private static final Map<Entity, Inventory> forcedAccessEntityInventoriies = new HashMap<>();

    public static Set<ActiveLockpick> getActiveLockpicks() {
        return activeLockpicks;
    }

    public static Map<Block, Inventory> getForcedAccessBlockInventoriies() {
        return forcedAccessBlockInventoriies;
    }

    public static Map<Entity, Inventory> getForcedAccessEntityInventories() {
        return forcedAccessEntityInventoriies;
    }

    public static boolean registerActiveLockpick(ActiveLockpick newActiveLockpick) {
        if (newActiveLockpick instanceof ActiveBlockLockpick newActiveBlockLockpick) {
            for (ActiveLockpick activeLockpick : activeLockpicks) {
                if (activeLockpick instanceof ActiveBlockLockpick activeBlockLockpick) {
                    // If block is already being lockpicked
                    if (newActiveBlockLockpick.getBlock().equals(activeBlockLockpick.getBlock())) {
                        return false;
                    }
                }
            }
        } else if (newActiveLockpick instanceof ActiveEntityLockpick newActiveEntityLockpick) {
            for (ActiveLockpick activeLockpick : activeLockpicks) {
                if (activeLockpick instanceof ActiveEntityLockpick activeEntityLockpick) {
                    // If entity is already being lockpicked
                    if (newActiveEntityLockpick.getEntity().getEntityId() == activeEntityLockpick.getEntity().getEntityId())
                        return false;
                }
            }
        } else if (newActiveLockpick instanceof ActiveMechanicLockpick newActiveMechanicLock) {
            for (ActiveLockpick activeLockpick : activeLockpicks) {
                if (activeLockpick instanceof ActiveMechanicLockpick activeMechanicLockpick) {
                    // If entity is already being lockpicked
                    if (newActiveMechanicLock.getWrappedMechanic().getMechanic().getMechanicType().id().contentEquals(activeMechanicLockpick.getWrappedMechanic().getMechanic().getMechanicType().id()))
                        return false;
                }
            }
        }
        activeLockpicks.add(newActiveLockpick);
        return true;
    }

    public static void deRegisterActiveLockpick(ActiveLockpick activeLockpick) {
        activeLockpicks.remove(activeLockpick);
    }
}
