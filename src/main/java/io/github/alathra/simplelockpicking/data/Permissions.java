package io.github.alathra.simplelockpicking.data;

public class Permissions {
    private static final String ADMIN_PERMISSION = "simplelockpicking.admin";
    private static final String CRAFTING_PERMISSION = "simplelockpicking.craft";
    private static final String LOCKPICKING_PERMISSION = "simplelockpicking.lockpicking";

    public static String getAdminPermission() {
        return ADMIN_PERMISSION;
    }

    public static String getCraftingPermission() {
        return CRAFTING_PERMISSION;
    }

    public static String getLockpickPermission() {
        return LOCKPICKING_PERMISSION;
    }
}
