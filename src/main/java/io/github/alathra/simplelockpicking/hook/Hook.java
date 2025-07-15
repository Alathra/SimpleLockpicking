package io.github.alathra.simplelockpicking.hook;

import io.github.alathra.simplelockpicking.hook.bolt.BoltHook;
import io.github.alathra.simplelockpicking.hook.craftbook.CraftbookHook;
import io.github.alathra.simplelockpicking.hook.itemsadder.ItemsAdderHook;
import io.github.alathra.simplelockpicking.hook.mmoitems.MMOItemsHook;
import io.github.alathra.simplelockpicking.hook.nexo.NexoHook;
import io.github.alathra.simplelockpicking.hook.oraxen.OraxenHook;
import io.github.alathra.simplelockpicking.hook.towny.TownyHook;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Enum of all hooks used by the plugin.
 */
public enum Hook {
    Bolt(BoltHook.class, "Bolt", true),
    CraftBook(CraftbookHook.class, "Craftbook5", true),
    Towny(TownyHook.class, "Towny", true),
    ItemsAdder(ItemsAdderHook.class, "ItemsAdder", true),
    Nexo(NexoHook.class, "Nexo", true),
    Oraxen(OraxenHook.class, "Oraxen", true),
    MMOItems(MMOItemsHook.class, "MMOItems", true);

    private final @NotNull Class<? extends AbstractHook> hookClass; // The hook class used by this hook
    private final @Nullable String pluginName; // The plugin name used by this hook or null if not applicable
    private final boolean optional; // Whether this hook is optional or required for the plugin to enable
    private AbstractHook loadedHook; // A pointer to the hook object instantiated by {@link HookManager}

    Hook(@NotNull Class<? extends AbstractHook> hookClass, @Nullable String pluginName, boolean optional) {
        this.hookClass = hookClass;
        this.pluginName = pluginName;
        this.optional = optional;
    }

    /**
     * Get the hook class.
     * @return the hook class
     */
    @NotNull Class<? extends AbstractHook> getHookClass() {
        return hookClass;
    }

    /**
     * Get the plugin name used by this hook. Can be null for hooks that do not use a plugin to provide functionality.
     * @return the plugin name
     */
    public @Nullable String getPluginName() {
        return pluginName;
    }

    /**
     * Check if this hook is required for the plugin to enable.
     * @return whether this hook is required
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Get the hook object.
     * @return the hook object
     * @implNote Cast this {@link AbstractHook} into the correct hook class.
     * @implSpec You should check {@link #isLoaded()} before using this method.
     * @throws IllegalStateException if the hook has not been loaded yet
     */
    public AbstractHook get() {
        if (loadedHook == null)
            throw new IllegalStateException("Hook has not been loaded yet.");

        return loadedHook;
    }

    /**
     * Check if the hook is loaded.
     * @return whether the hook is loaded
     * @implNote This check is a guarantee that the hook and its dependencies have loaded. It also checks {@link AbstractHook#isHookLoaded()}.
     */
    public boolean isLoaded() {
        if (loadedHook != null)
            return loadedHook.isHookLoaded();

        return false;
    }

    /**
     * Sets a weak reference to a hook
     * @param hook the hook object
     */
    @ApiStatus.Internal
    void setHook(@Nullable AbstractHook hook) {
        this.loadedHook = hook;
    }

    /**
     * Clear the weak reference to this hook
     */
    @ApiStatus.Internal
    void clearHook() {
        this.loadedHook = null;
    }

    /**
     * Clear the weak references for hooks
     */
    @ApiStatus.Internal
    static void clearHooks() {
        for (Hook hooks : values())
            hooks.clearHook();
    }

    @NotNull
    public static TownyHook getTownyHook() {
        return (TownyHook) Hook.Towny.get();
    }

    @NotNull
    public static ItemsAdderHook getItemsAdderHook() {
        return (ItemsAdderHook) Hook.ItemsAdder.get();
    }

    @NotNull
    public static MMOItemsHook getMMOItemsHook() {
        return (MMOItemsHook) Hook.MMOItems.get();
    }

    @NotNull
    public static NexoHook getNexoHook() {
        return (NexoHook) Hook.Nexo.get();
    }

    @NotNull
    public static OraxenHook getOraxenHook() {
        return (OraxenHook) Hook.Oraxen.get();
    }

    @NotNull
    public static CraftbookHook getCraftBookHook() {
        return (CraftbookHook) Hook.CraftBook.get();
    }

    @NotNull
    public static BoltHook getBoltHook() {
        return (BoltHook) Hook.Bolt.get();
    }


}
