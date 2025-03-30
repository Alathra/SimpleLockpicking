package io.github.alathra.simplelockpicking.core;

import io.github.alathra.simplelockpicking.SimpleLockpicking;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.HashSet;
import java.util.Set;

public class LockpickingManager {

    private final SimpleLockpicking plugin;
    private final Set<ActiveLockpick> activeLockpicks = new HashSet<>();

    public LockpickingManager(SimpleLockpicking plugin) {
        this.plugin = plugin;
    }

    public Set<ActiveLockpick> getActiveLockpicks() {
        return activeLockpicks;
    }

    public boolean registerActiveLockpick(ActiveLockpick newActiveLockpick) {
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

    public void deRegisterActiveLockpick(ActiveLockpick activeLockpick) {
        activeLockpicks.remove(activeLockpick);
    }

    public boolean isGettingLockpicked(Block block) {
        for (ActiveLockpick activeLockpick : activeLockpicks) {
            if (activeLockpick instanceof ActiveBlockLockpick activeBlockLockpick) {
                if (activeBlockLockpick.getBlock().equals(block)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGettingLockpicked(Entity entity) {
        for (ActiveLockpick activeLockpick : activeLockpicks) {
            if (activeLockpick instanceof ActiveEntityLockpick activeEntityLockpick) {
                if (activeEntityLockpick.getEntity().getEntityId() == entity.getEntityId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
