package io.github.alathra.simplelockpicking.hook.towny;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.WorldCoord;
import io.github.alathra.simplelockpicking.SimpleLockpicking;
import io.github.alathra.simplelockpicking.hook.AbstractHook;
import io.github.alathra.simplelockpicking.hook.Hook;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TownyHook extends AbstractHook {

    public TownyHook(SimpleLockpicking plugin) {
        super(plugin);
    }

    @Override
    public void onEnable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
    }

    @Override
    public void onDisable(SimpleLockpicking plugin) {
        if (!isHookLoaded()) return;
    }

    @Override
    public boolean isHookLoaded() {
        return isPluginEnabled(Hook.Towny.getPluginName());
    }

    public boolean isLocationInAnyTown(Location location) {
        Town townAtLocation = WorldCoord.parseWorldCoord(location).getTownOrNull();
        return townAtLocation != null;
    }

    public boolean isLocationInTown(Location location, Town town) {
        Town townAtLocation = WorldCoord.parseWorldCoord(location).getTownOrNull();
        if (townAtLocation == null) {
            return false;
        }
        return townAtLocation.equals(town);
    }

    public boolean isLocationInOwnTown(Location location, Player player) {
        Resident resident = TownyAPI.getInstance().getResident(player.getUniqueId());
        if (resident == null) {
            return false;
        }
        Town town = resident.getTownOrNull();
        if (town == null) {
            return false;
        }
        return isLocationInTown(location, town);
    }

    public boolean isRuins(Location location) {
        Town townAtLocation = WorldCoord.parseWorldCoord(location).getTownOrNull();
        if (townAtLocation == null) return false;
        return townAtLocation.isRuined();
    }

}
