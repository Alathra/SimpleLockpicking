<h1 style="text-align:center;">SimpleLockpicking</h1>
<h2>A simple plugin for your lockpicking needs</h2>
<p style="text-align:center;">
    <img alt="GitHub License" src="https://img.shields.io/github/license/Alathra/SimpleLockpicking?style=for-the-badge&color=blue&labelColor=141417">
    <img alt="GitHub Downloads (all assets, all releases)" src="https://img.shields.io/github/downloads/Alathra/SimpleLockpicking/total?style=for-the-badge&labelColor=141417">
    <img alt="GitHub Release" src="https://img.shields.io/github/v/release/Alathra/SimpleLockpicking?include_prereleases&sort=semver&style=for-the-badge&label=LATEST%20VERSION&labelColor=141417">
    <img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/Alathra/SimpleLockpicking/ci.yml?style=for-the-badge&labelColor=141417">
    <img alt="GitHub Issues or Pull Requests" src="https://img.shields.io/github/issues/Alathra/SimpleLockpicking?style=for-the-badge&labelColor=141417">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/Alathra/SimpleLockpicking?style=for-the-badge&labelColor=141417">
</p>

---

## Description

SimpleLockpicking adds lockpick items which can be used to open doors, fence gates, trapdoors, chests and more. It is designed to be used in conjunciton with a block protection or "private sign" plugin like [BlockLocker](https://www.spigotmc.org/resources/blocklocker.3268/), [LWC Extended](https://www.spigotmc.org/resources/lwc-extended.69551/), or [Bolt](https://modrinth.com/plugin/bolt). Lockpicks are able to bypass the protections of these plugin, facilitating a simple and dynamic lockpicking mechanic. 

---

## Usage
Lockpicks are a configurable custom item that used by holding them in your main hand and **shift right clicking** a block or entity. An [example resourcepack](https://github.com/Alathra/SimpleLockpicking/blob/main/SimpleLockpicking-Resourcepack-1.0.zip) is included that gives the lockpick a custom texture and model. Note that this requires the player to have the permission ``simplelockpicking.lockpick`` and that the world is in the ``enabledWorlds`` section of the [config](https://github.com/Alathra/SimpleLockpicking/blob/main/src/main/resources/config.yml). Below is a list of lockpickable things.

| **Name**             |   **Type**   |   **Lockpick Action**   | **Notes**                        |
| ---------------- | ------ | --------------- | -------------------------------------------- |
| barrel           | block  | opens inventory |                                              |
| chests           | block  | opens inventory | Does not include trapped chests              |
| chest boats      | entity | opens inventory | Includes all chest boat variants             |
| chest minecarts  | entity | opens inventory |                                              |
| copper doors     | block  | toggles state   | Includes all copper variants                 |
| copper trapdoors | block  | toggles state   | Includes all copper variants                 |
| fence gates      | block  | toggles state   | Includes all fence gate variants             |
| iron doors       | block  | toggles state   |                                              |
| iron trapdoors   | block  | toggles state   |                                              |
| shulker boxes    | block  | opens inventory | Includes all shulker box variants            |
| trapped chests   | block  | opens inventory |                                              |
| wooden doors     | block  | toggles state   | Includes bamboo, crimson and warped variants |
| wooden trapdoors | block  | toggles state   | Includes bamboo, crimson and warped variants |

Based on the chances defined in the ``LockpickChances`` section of the [config](https://github.com/Alathra/SimpleLockpicking/blob/main/src/main/resources/config.yml), a lockpick will either be successful and trigger the "LockpickAction" defined above, or break and the player will lose the item.

#### Lockpick Success

![Lockpick Success](https://github.com/Alathra/SimpleLockpicking/blob/main/docs/assets/lockpick_success.gif?raw=true "Lockpick Success")

#### Lockpick Fail (It breaks)

![Lockpick Fail](https://github.com/Alathra/SimpleLockpicking/blob/main/docs/assets/lockpick_fail.gif?raw=true "Lockpick Fail")

#### Crafting Lockpicks
SimpleLockpicking includes a default crafting recipe for lockpicks. Please note that for players to craft lockpicks you must have ``enableDefaultLockpickCraftingRecipe`` set to ``true`` in the [config](https://github.com/Alathra/SimpleLockpicking/blob/main/src/main/resources/config.yml) and grant the permissionm ``simplellockpicking.lockpick``.

![Lockpick Recipe](https://github.com/Alathra/SimpleLockpicking/blob/main/docs/assets/lockpick_recipe.PNG?raw=true "Lockpick Crafting Recipe")

---

## Hooks & Compatibility
### Tested Protection Plugins
* #### [BlockLocker](https://www.spigotmc.org/resources/blocklocker.3268/)
    *Works natively*
* #### [Bolt](https://modrinth.com/plugin/bolt)
    *Ssupport provided by SimpleLoickpicking*
* #### [LWC Extended](https://www.spigotmc.org/resources/lwc-extended.69551/)
    *Works natively*
### Optional Hooks
* #### [Craftbook5](https://modrinth.com/plugin/craftbook/)
    Adds the ability to lockpick CraftBook bridges, doors and gates. This feature can be configured in the ``CraftBookCompatibility`` section of the [config](https://github.com/Alathra/SimpleLockpicking/blob/main/src/main/resources/config.yml).
* #### [Towny](https://github.com/TownyAdvanced/Towny)
    If Towny is enabled, the lockpicking of containers (barrels, chests, shulker boxes, etc.) will automatically be disabled within Towny claims.
* #### [ItemsAdder](https://itemsadder.devs.beer/), [Nexo](https://docs.nexomc.com/), [Oraxen](https://oraxen.com/)
    The above plugins are used to create and manage custom items. SimpleLockpicking supports the use of either of them for the lockpick item. If you wish to create a custom lock item using one of these item frameworks, you can define item id for simplelockpicking to link and use in the ``Lockpick Item`` section of the [config.](https://github.com/Alathra/SimpleLockpicking/blob/main/src/main/resources/config.yml).

---

## Permissions
SimpleLockpicking contains the following permission nodes:
* ``simplelockpicking.admin``
Grants the user all other permissions and also the **/simplelockpicking getlockpick** command.
* ``simplelockpicking.lock``
Grants the user the ability to use lockpicks.
* ``simplelockpicking.craft``
Grants the user the ability to craft lockpicks using the default crafting recipe.
---

## Commands
SimpleLockpicking adds the following commands:
* ``/simplelockpicking getlockpick <amount>``
Spawns the specified amount of lockpick items in the player's inventory, defaults to 1. This command requires the permission node **simplelockpicking.admin**.

---

## Configuration

Simplelockpicking can be configured by editing values in the [config.yml](https://github.com/Alathra/BoltUX/blob/main/src/main/resources/config.yml). **SimpleLockpicking does not have a reload command because it automatically checks for updates in the config file**. When you make edits to the file the changes will be applied immediately. The only the exeption is the field ``enableDefaultLockpickCraftingRecipe``, which is applied when the plugin is enabled.

---
