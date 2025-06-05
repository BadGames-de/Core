package de.badgames.pluginCore.util;

import com.cryptomorin.xseries.XEnchantment;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import de.badgames.pluginCore.inventory.CItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import java.util.*;
import java.util.List;

/**
 * A simple builder class for the ItemStack
 * by <i>@ExpDev</i>
 * <p>
 * <b>Note:</b> Colors are parsed, so you can use legacy methods for setting textstyles.
 * <p>
 * <i>Uses <b>Java 8</b>, so go download it if you ain't already!</i>
 */
public class ItemStackBuilder {

    protected static MiniMessage miniMessage = MiniMessage.miniMessage();

    // Fundamentals
    private XMaterial material = XMaterial.AIR;
    private int amount = 1;
    private short durability = 0; // id -> 324:2 <- durability

    // Meta
    private Component name = null;
    private List<Component> lore = null;

    // Features
    private boolean unbreakable = false;

    // Enchantments and flags
    private Map<XEnchantment, Integer> enchantments = null;
    private Map<Enchantment, Integer> fallBackEnchantments = null;
    private Set<ItemFlag> itemFlags = null;

    private int customModelData = 0; // Custom model data, not used by default

    // Leather color
    private Color color;

    PotionType baseType = null;

    // Construction of a new builder
    public ItemStackBuilder() {
    }

    public ItemStackBuilder(Material material) {
        this.material = XMaterial.matchXMaterial(material);
    }

    public ItemStackBuilder(XMaterial material) {
        this.material = material;
    }

    /**
     * Constructs a new builder from an existing ItemStack.
     * Does not copy over Potion effects or color.
     * @param itemStack ItemStack to copy
     */
    public ItemStackBuilder(ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("ItemStack cannot be null");
        }

        if (itemStack.getItemMeta() != null) {
            this.name = itemStack.getItemMeta().displayName();
            this.lore = itemStack.getItemMeta().lore();
            this.itemFlags = itemStack.getItemMeta().getItemFlags();
            this.unbreakable = itemStack.getItemMeta().isUnbreakable();
            if (itemStack.getItemMeta().hasCustomModelData())
                this.customModelData = itemStack.getItemMeta().getCustomModelData();
        }
        this.material = XMaterial.matchXMaterial(itemStack.getType());
        this.amount = itemStack.getAmount();
        this.durability = itemStack.getDurability();
        parseEnchantments(itemStack.getEnchantments());
    }

    private static String parseColorAmp(String string) {
        string = string.replaceAll("(§([a-z0-9]))", "\u00A7$2");
        string = string.replaceAll("(&([a-z0-9]))", "\u00A7$2");
        string = string.replace("&&", "&");
        return string;
    }

    public ItemStackBuilder asMaterial(XMaterial material) {
        this.material = material;
        return this;
    }

    public ItemStackBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder withData(short data) {
        this.durability = data;
        return this;
    }

    // In case you are too lazy to cast (if you're using int)
    public ItemStackBuilder withData(int data) {
        return withData((short) data);
    }

    public ItemStackBuilder withName(TextComponent name) {
        this.name = name.decoration(TextDecoration.ITALIC, false);
        return this;
    }

    public ItemStackBuilder withName(String name) {
        this.name = LegacyComponentSerializer.legacySection().deserialize(name);
        if (!name.contains("§o")) {
            this.name = this.name.decoration(TextDecoration.ITALIC, false);
        }
        return this;
    }

    public ItemStackBuilder withMiniMessageName(String name) {
        this.name = miniMessage.deserialize(name);
        return this;
    }

    public ItemStackBuilder withComponentLore(TextComponent... lines) {
        return withComponentLore(Arrays.asList(lines));
    }

    public ItemStackBuilder withComponentLore(List<TextComponent> lines) {
        return withLore(lines.stream().map(TextComponent::content).toList());
    }

    public ItemStackBuilder withLore(List<String> lines) {
        return withLoreWithComponent(lines.stream().map(x -> {
            var des = LegacyComponentSerializer.legacySection().deserialize(x);
            if (!x.contains("§o")) {
                des = des.decoration(TextDecoration.ITALIC, false);
            }

            return des.asComponent();
        }).toList());
    }

    public ItemStackBuilder withLore(String... lines) {
        return withLore(Arrays.asList(lines));
    }

    public ItemStackBuilder withMiniMessageLore(List<String> lines) {
        return withLoreWithComponent(lines.stream().map(x -> miniMessage.deserialize(x)).toList());
    }

    public ItemStackBuilder withMiniMessageLore(String... lines) {
        return withMiniMessageLore(Arrays.asList(lines));
    }

    public ItemStackBuilder withLoreWithComponent(List<Component> lines) {
        this.lore = lines;
        return this;
    }

    public ItemStackBuilder withLore(Component... lines) {
        return withLoreWithComponent(Arrays.asList(lines));
    }

    // Just calls ItemMeta#setUnbreakable(true), don't know if compatible with old versions
    public ItemStackBuilder makeUnbreakable() {
        this.unbreakable = true;
        return this;
    }

    public ItemStackBuilder withBukkitEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = new HashMap<>();
        parseEnchantments(enchantments);
        return this;
    }

    private void parseEnchantments(Map<Enchantment, Integer> enchantments) {
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            Optional<XEnchantment> enchantmentOptional = XEnchantment.matchXEnchantment(entry.getKey().getName());
            if (enchantmentOptional.isPresent()) {
                if (this.enchantments == null) {
                    this.enchantments = new HashMap<>();
                }

                this.enchantments.put(enchantmentOptional.get(), entry.getValue());
            } else {
                if (this.fallBackEnchantments == null) {
                    this.fallBackEnchantments = new HashMap<>();
                }

                this.fallBackEnchantments.put(entry.getKey(), entry.getValue());
            }
        }
    }

    // Enchantments
    public ItemStackBuilder withEnchantments(Map<XEnchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemStackBuilder addEnchantment(XEnchantment enchantment, int level) {
        // Make sure we have something to add the enchantment to
        if (enchantments == null) {
            this.enchantments = new HashMap<XEnchantment, Integer>();
        }

        enchantments.put(enchantment, level);
        return this;
    }

    public ItemStackBuilder withLeatherColor(Color color) {
        this.color = color;
        return this;
    }

    public ItemStackBuilder withBasePotionType(PotionType type) {
        this.baseType = type;
        return this;
    }

    // Flags
    public ItemStackBuilder withItemFlags(Set<ItemFlag> flags) {
        this.itemFlags = flags;
        return this;
    }

    // Can be used to add only 1 ItemFlag (#withItemFlags(ItemFlag.HIDE_ENCHANTMENTS))
    public ItemStackBuilder withItemFlags(ItemFlag... flags) {
        return withItemFlags(new HashSet<ItemFlag>(Arrays.asList(flags)));
    }

    public SkullBuilder toSkullBuilder() {
        return new SkullBuilder(this);
    }

    /**
     * Builds the ItemStack with durability from this instance
     *
     * @return ItemStack with meta
     */
    public ItemStack buildStack() {
        /*ConfigurationSection config = new MemoryConfiguration();
        config.set("amount", amount);
        config.set("damage", durability);
        if (name != null && (name instanceof TextComponent tx && !tx.content().isBlank())) config.set("name", name);
        if (lore != null && !lore.isEmpty()) config.set("lore", lore);
        config.set("unbreakable", unbreakable);
        if (enchantments != null && !enchantments.isEmpty()) config.set("enchantments", enchantments);
        if (color != null) config.set("color", color.asRGB());
        if (baseType != null) config.set("base-type", baseType.toString());
        if (effects != null && !effects.isEmpty()) config.set("effects", effects);
        if (itemFlags != null) config.set("flags", itemFlags);


        // Lastly, return the stack
        return XItemStack.edit(material.or(XMaterial.WOODEN_SWORD).parseItem(), config, s -> s, null);*/

        // Creating a new ItemStack
        ItemStack itemStack = new ItemStack(material.parseMaterial(), amount, durability);

        // Getting the stack's meta
        final ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return null;

        // Meta
        // Set displayname if name is not null
        if (name != null) {
            itemMeta.displayName(name);
        }
        // Set lore if it is not null nor empty
        if (lore != null && !lore.isEmpty()) {
            itemMeta.lore(lore);
        }
        // Add enchantments if any
        if (enchantments != null && !enchantments.isEmpty()) {
            // Doing this so I don't have to keep unsafe and safe enchantments separately
            // Ignore all stupid enchantment restrictions ;)
            enchantments.forEach((ench, lvl) -> itemMeta.addEnchant(ench.getEnchant(), lvl, true));
            // Add fallback enchantments if any
            if (fallBackEnchantments != null && !fallBackEnchantments.isEmpty()) {
                fallBackEnchantments.forEach((ench, lvl) -> itemMeta.addEnchant(ench, lvl, true));
            }
        }
        // Add flags if any
        if (itemFlags != null && !itemFlags.isEmpty()) {
            itemMeta.addItemFlags(itemFlags.toArray(new ItemFlag[itemFlags.size()]));
        }
        // Deprecated in newer versions, but newer method does not exist in older
        // Only call when unbreakable is true, to refrain from calling as much as possible
        // You could of course always implement your own unbreakable method here
        if (unbreakable) itemMeta.setUnbreakable(true);

        if (color != null && itemMeta instanceof LeatherArmorMeta meta) {
            meta.setColor(color);
        }

        if (baseType != null && itemMeta instanceof PotionMeta meta) {
            meta.setBasePotionData(new PotionData(baseType));
        }

        if (customModelData > 0) {
            itemMeta.setCustomModelData(customModelData);
        }

        // Set the new ItemMeta
        itemStack.setItemMeta(itemMeta);

        // Lastly, return the stack
        return itemStack;
    }

    /**
     * Builds a CItem from the ItemStack.
     * @return CItem.
     */
    public CItem buildCItem() {
        return new CItem(buildStack());
    }

    /**
     * A simple builder for a skull with owner
     * <p>
     * <b>Note:</b> Uses the ItemStackBuilder builder ;)
     */
    public class SkullBuilder {

        // Fundamentals
        private ItemStackBuilder stackBuilder;

        private Profileable profileable;

        private SkullBuilder(ItemStackBuilder stackBuilder) {
            this.stackBuilder = stackBuilder;
        }

        public SkullBuilder withProfileable(Profileable profileable) {
            this.profileable = profileable;
            return this;
        }

        public SkullBuilder withName(String name) {
            this.profileable = Profileable.username(name);
            return this;
        }

        public SkullBuilder withPlayer(Player player) {
            this.profileable = Profileable.of(player);
            return this;
        }

        /**
         * Builds a skull from an owner
         *
         * @return ItemStack skull with owner
         */
        public ItemStack buildSkull() {
            return XSkull.of(stackBuilder.asMaterial(XMaterial.PLAYER_HEAD).buildStack()).profile(profileable).apply();
        }
    }
}