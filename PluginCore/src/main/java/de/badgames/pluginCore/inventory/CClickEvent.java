package de.badgames.pluginCore.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public record CClickEvent(Player player, ItemStack stack, int slot, boolean isShift, Inventory inventory) {

}
