package de.badgames.pluginCore.inventory;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class Screens {

    private final ArrayList<CScreen> registered = new ArrayList<>();

    public void register(CScreen... screens) {
        registered.addAll(Arrays.asList(screens));
    }

    public void unregister(CScreen... screens) {
        registered.removeAll(Arrays.asList(screens));
    }

    public void unload() {
        for (CScreen screen : registered) {
            if (screen.getCached() != null) {
                screen.getCached().close();
                screen.getCached().clear();
            }
        }
        registered.clear();
    }

    public CScreen getScreenFromInventory(Player player, Component title, Inventory inventory) {
        for (CScreen screen : registered) {
            if (screen.isCached() && screen.getCached() != null) {
                if (inventory.equals(screen.getCached())) return screen;
            } else if (title.equals(screen.title(player))) return screen;
        }

        return null;
    }

    public CScreen screen(int id) {
        for (CScreen screen : registered) {
            if (screen.getId() == id) {
                return screen;
            }
        }

        return null;
    }

    public void open(Player player, int id) {
        openAndReturnInventory(player, id);
    }

    public Inventory openAndReturnInventory(Player player, int id) {
        for (CScreen screen : registered) {
            if (screen.getId() == id) {
                Inventory inventory = screen.buildInventory(player);

                player.openInventory(inventory);
                return inventory;
            }
        }

        return null;
    }
}
