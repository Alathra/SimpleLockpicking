package io.github.alathra.simplelockpicking.core;

import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

public class LockpickingManager {

    private static final Set<ActiveLockpick> activeLockpicks = new HashSet<>();

    public static Set<ActiveLockpick> getActiveLockpicks() {
        return activeLockpicks;
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
        }
        activeLockpicks.add(newActiveLockpick);
        return true;
    }

    public static void deRegisterActiveLockpick(ActiveLockpick activeLockpick) {
        activeLockpicks.remove(activeLockpick);
    }
}
