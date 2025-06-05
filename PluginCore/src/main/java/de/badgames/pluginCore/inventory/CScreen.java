package de.badgames.pluginCore.inventory;

import com.cryptomorin.xseries.XMaterial;
import de.badgames.pluginCore.util.ItemStackBuilder;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class CScreen {

    /**
     * The unique ID.
     */
    @Getter
    private final int id;

    /**
     * The title of the inventory.
     */
    @Getter
    private final Component title;

    /**
     * The rows of the inventory.
     */
    @Getter
    private final int rows;

    /**
     * If a global cache inventory should be used.
     */
    private final boolean cache;

    /**
     * The global cache inventory.
     */
    @Getter
    private Inventory cached;

    /**
     * The Inventory type.
     */
    @Getter
    private final InventoryType inventoryType;

    /**
     * The background material that is being used on {@link CScreen#background()}.
     */
    protected final XMaterial backgroundMaterial;

    /**
     * Create a new screen.
     * @param id the screen ID.
     * @param title the title ID.
     * @param rows the rows of the inventory
     * @param cache if the cache should be used.
     */
    public CScreen(int id, Component title, int rows, boolean cache) {
        this(id, title, rows, cache, InventoryType.CHEST, XMaterial.BLACK_STAINED_GLASS_PANE);
    }

    /**
     * Create a new screen.
     * @param id the screen ID.
     * @param title the title ID.
     * @param rows the rows of the inventory
     * @param cache if the cache should be used.
     * @param inventoryType the inventory type.
     */
    public CScreen(int id, Component title, int rows, boolean cache, InventoryType inventoryType) {
        this(id, title, rows, cache, inventoryType, XMaterial.BLACK_STAINED_GLASS_PANE);
    }

    /**
     * Create a new screen.
     * @param id the screen ID.
     * @param title the title ID.
     * @param rows the rows of the inventory
     * @param cache if the cache should be used.
     * @param inventoryType the inventory type.
     * @param backgroundMaterial the background Material.
     */
    public CScreen(int id, Component title, int rows, boolean cache, InventoryType inventoryType, XMaterial backgroundMaterial) {
        this.id = id;
        this.title = title;
        this.rows = rows;
        this.cache = cache;
        this.inventoryType = inventoryType;
        this.backgroundMaterial = backgroundMaterial;
    }

    /**
     * An Item Hashmap wich contains the items slot and its CItem Implementation.
     */
    private final HashMap<Integer, CItem> items = new HashMap<>();

    /**
     * An Item Hashmap which contains the items slot ad its CItem Implementation for a specific user.
     */
    private final HashMap<Player, HashMap<Integer, CItem>> customItems = new HashMap<>();

    /**
     * Set an item in the screen.
     * @param slot the slot where the item is placed.
     * @param item the item to be placed.
     */
    public void setItem(int slot, CItem item) {
        items.put(slot, item);
        if (cache && cached != null) {
            cached.setItem(slot, item.getStack());
        }
    }

    /**
     * Same as {@link CScreen#setItem(int, CItem)} but instead sets it only for a specific player.
     * @param player the player.
     * @param slot the slot where the item is placed.
     * @param item the item to be placed.
     */
    public void setItemNotCached(Player player, int slot, CItem item) {
        HashMap<Integer, CItem> items = customItems.getOrDefault(player, new HashMap<>());
        items.put(slot, item);
        customItems.put(player, items);
    }

    /**
     * Same as {@link CScreen#setItemNotCached(Player, int, CItem)} but instead sets it only in a specific inventory.
     * @param player the player.
     * @param slot the slot where the item is placed.
     * @param item the item to be placed.
     * @param inventory the inventory to place it.
     */
    public void setItemNotCached(Player player, int slot, CItem item, Inventory inventory) {
        setItemNotCached(player, slot, item);
        inventory.setItem(slot, item.getStack());
    }

    /**
     * Initialize the inventory for a specific player.
     * @param player the player.
     */
    public void init(Player player) {
    }

    /**
     * Initialize the inventory for a specific player.
     * @param player the player.
     * @param inventory the inventory.
     */
    public void init(Player player, Inventory inventory) {

    }

    public Component title(Player player) {
        return title;
    }

    /**
     * This method is called when the inventory is opened.
     * @param player the player who opened the inventory.
     * @param event the event that was triggered.
     */
    public void open(Player player, InventoryOpenEvent event) {
    }

    /**
     * This method is called when the inventory is closed.
     * @param player the player who closed the inventory.
     * @param event the event that was triggered.
     */
    public void close(Player player, InventoryCloseEvent event) {
    }

    public void background() {
        ItemStack stack = new ItemStackBuilder(backgroundMaterial).withName("§r").buildStack();
        for (int i = 0; i < 9; i++) setItem(i, new CItem(stack).notClickable());
        for (int i = rows * 9 - 9; i < rows * 9; i++) setItem(i, new CItem(stack).notClickable());
    }

    public void fullBackground() {
        CItem citem = new ItemStackBuilder(backgroundMaterial).withName("§r").buildCItem().notClickable();
        for (int i = 0; i < rows * 9; i++) setItem(i, citem);
    }

    public void background(Player player) {
        ItemStack stack = new ItemStackBuilder(backgroundMaterial).withName("§r").buildStack();
        for (int i = 0; i < 9; i++) setItemNotCached(player, i, new CItem(stack).notClickable());
        for (int i = rows * 9 - 9; i < rows * 9; i++) setItemNotCached(player, i, new CItem(stack).notClickable());
    }

    public void background(Player player, Inventory inventory) {
        CItem citem = new ItemStackBuilder(backgroundMaterial).withName("§r").buildCItem().notClickable();
        for (int i = 0; i < 9; i++) setItemNotCached(player, i, citem, inventory);
        for (int i = rows * 9 - 9; i < rows * 9; i++) setItemNotCached(player, i, citem, inventory);
    }

    public void fullBackground(Player player, Inventory inventory) {
        CItem citem = new ItemStackBuilder(backgroundMaterial).withName("§r").buildCItem().notClickable();
        for (int i = 0; i < rows * 9; i++) setItemNotCached(player, i, citem, inventory);
    }

    public Inventory buildInventory(Player player) {
        if (cache && cached != null) {
            return cached;
        }

        Inventory inventory = inventoryType == InventoryType.CHEST ? Bukkit.createInventory(null, rows * 9, title(player)) : Bukkit.createInventory(null, inventoryType, title(player));

        if (!cache) {
            // Clear all the custom items when the thing is not cached
            customItems.remove(player);
        }
        init(player);
        init(player, inventory);
        for (Map.Entry<Integer, CItem> entry : customItems.getOrDefault(player, new HashMap<>()).entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().getStack());
        }

        if (cache) {

            for (Map.Entry<Integer, CItem> entry : items.entrySet()) {
                inventory.setItem(entry.getKey(), entry.getValue().getStack());
            }

            cached = inventory;
        }

        return inventory;
    }

    public CItem item(Player player, int slot) {
        if (!cache) {
            return customItems.getOrDefault(player, new HashMap<>()).getOrDefault(slot, null);
        }
        return items.getOrDefault(slot, null);
    }

    public boolean isCached() {
        return cache;
    }
}
