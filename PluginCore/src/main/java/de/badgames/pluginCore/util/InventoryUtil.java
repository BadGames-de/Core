package de.badgames.pluginCore.util;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static boolean hasEnoughItems(Player player, XMaterial material, int amount) {
        int count = 0;
        for (ItemStack item : player.getInventory()) {
            if (item != null && material.isSimilar(item)) {
                count += item.getAmount();
            }
        }
        return count >= amount;
    }

    public static void removeAmountFromInventory(Player player, XMaterial material, int amount) {
        int count = amount;
        for (ItemStack item : player.getInventory()) {
            if (item != null && material.isSimilar(item)) {
                int sub = Math.min(item.getAmount(), count);
                int newAmount = item.getAmount() - sub;
                item.setAmount(newAmount);
                count -= sub;
                if (count <= 0) {
                    break;
                }
            }
        }
    }
}
