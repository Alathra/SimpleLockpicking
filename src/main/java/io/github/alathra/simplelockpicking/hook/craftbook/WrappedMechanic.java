package io.github.alathra.simplelockpicking.hook.craftbook;

import org.bukkit.block.Sign;
import org.bukkit.block.sign.Side;
import org.enginehub.craftbook.ChangedSign;
import org.enginehub.craftbook.mechanic.CraftBookMechanic;
import org.enginehub.craftbook.mechanic.exception.InvalidMechanismException;
import org.enginehub.craftbook.mechanics.area.Bridge;
import org.enginehub.craftbook.mechanics.area.Door;
import org.enginehub.craftbook.mechanics.area.Gate;

public class WrappedMechanic {

    private final CraftBookMechanic mechanic;
    private final Sign mechanicSign;

    public WrappedMechanic(CraftBookMechanic mechanic, Sign mechanicSign) {
        this.mechanic = mechanic;
        this.mechanicSign = mechanicSign;
    }

    public CraftBookMechanic getMechanic() {
        return mechanic;
    }

    public Sign getMechanicSign() {
        return mechanicSign;
    }

    public void toggle() {
        switch (mechanic.getMechanicType().id()) {
            case "bridge":
                Bridge bridge = (Bridge) mechanic;
                try {
                    bridge.flipState(mechanicSign.getBlock());
                } catch (InvalidMechanismException ignored) {}
                break;
            case "door":
                Door door = (Door) mechanic;
                try {
                    door.flipState(mechanicSign.getBlock(), ChangedSign.create(mechanicSign, Side.FRONT));
                } catch (InvalidMechanismException e) {
                    try {
                        door.flipState(mechanicSign.getBlock(), ChangedSign.create(mechanicSign, Side.BACK));
                    } catch (InvalidMechanismException ignored) {}
                }
                break;
            case "gate":
                Gate gate = (Gate) mechanic;
                // Close: null = toggle, true = close, false = open
                try {
                    gate.toggleGates(mechanicSign.getBlock(), ChangedSign.create(mechanicSign, Side.FRONT), null);
                } catch (InvalidMechanismException e) {
                    try {
                        gate.toggleGates(mechanicSign.getBlock(), ChangedSign.create(mechanicSign, Side.BACK), null);
                    } catch (InvalidMechanismException ignored) {}
                }
                break;
        }
    }
}

