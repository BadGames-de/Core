package de.badgames.pluginCore.listener;

import de.badgames.pluginCore.PluginCore;
import de.badgames.pluginCore.inventory.CClickEvent;
import de.badgames.pluginCore.inventory.CItem;
import de.badgames.pluginCore.inventory.CScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        CScreen screen = PluginCore.getInstance().getScreens().getScreenFromInventory(player, event.getView().title(), event.getInventory());

        if (screen != null) {
            screen.open(player, event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        CScreen screen = PluginCore.getInstance().getScreens().getScreenFromInventory((Player) event.getPlayer(), event.getView().title(), event.getInventory());

        if (screen != null) {
            screen.close((Player) event.getPlayer(), event);
        }

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;

        CScreen screen = PluginCore.getInstance().getScreens().getScreenFromInventory(player, event.getView().title(), event.getClickedInventory());

        if (screen != null) {
            CItem item = screen.item(player, event.getSlot());

            if (item != null) {
                event.setCancelled(item.click(new CClickEvent(player, event.getCurrentItem(), event.getSlot(), event.isShiftClick(), event.getClickedInventory())));
            }
        }
    }

}
