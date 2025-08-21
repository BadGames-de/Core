package de.badgames.pluginCore.inventory;

import de.badgames.pluginCore.PluginCore;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.function.Consumer;

public class CItem {

    @Getter
    private final ItemStack stack;
    private Consumer<CClickEvent> clickFunction;
    private boolean clickable = true;
    @Getter
    private boolean moveable = false;

    public CItem(ItemStack stack) {
        this.stack = stack;
    }

    public CItem addIdentifier(String identifier) {
        if (this.stack.hasItemMeta()) {
            var container = this.stack.getItemMeta().getPersistentDataContainer();
            container.set(PluginCore.getInstance().getScreens().getItemIdentifier(), PersistentDataType.STRING, identifier);
        }

        return this;
    }

    public CItem onClick(Consumer<CClickEvent> clickFunction) {
        this.clickFunction = clickFunction;
        return this;
    }

    public CItem notClickable() {
        this.clickable = false;
        return this;
    }

    public CItem clickable() {
        this.clickable = true;
        return this;
    }

    public CItem moveable(boolean moveable) {
        this.moveable = moveable;
        return this;
    }

    public CItem clickable(boolean clickable) {
        this.clickable = clickable;
        return this;
    }

    public boolean click(CClickEvent event) {
        if (!clickable) return true;

        if (clickFunction == null) {
            return false;
        }

        clickFunction.accept(event);
        return true;
    }
}
