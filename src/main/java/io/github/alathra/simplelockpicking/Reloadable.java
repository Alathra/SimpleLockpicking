package io.github.alathra.simplelockpicking;

/**
 * Implemented in classes that should support being reloaded IE executing the methods during runtime after startup.
 */
public interface Reloadable {
    /**
     * On plugin load.
     */
    void onLoad(SimpleLockpicking plugin);

    /**
     * On plugin enable.
     */
    void onEnable(SimpleLockpicking plugin);

    /**
     * On plugin disable.
     */
    void onDisable(SimpleLockpicking plugin);
}
