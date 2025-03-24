package io.github.alathra.simplelockpicking.utility;


import io.github.alathra.simplelockpicking.SimpleLockpicking;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

/**
 * A class that provides shorthand access to {@link SimpleLockpicking#getComponentLogger}.
 */
public class Logger {
    /**
     * Get component logger. Shorthand for:
     *
     * @return the component logger {@link SimpleLockpicking#getComponentLogger}.
     */
    @NotNull
    public static ComponentLogger get() {
        return SimpleLockpicking.getInstance().getComponentLogger();
    }
}
