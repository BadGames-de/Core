package de.badgames.gameCore.events;

import de.badgames.gameCore.map.IMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event that is called when the map changes.
 * And can be cancelled.
 */
@Getter
public class GameMapChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private final IMap oldMap, newMap;

    private final Player changer;

    public GameMapChangeEvent(IMap oldMap, IMap newMap, Player changer) {
        this.oldMap = oldMap;
        this.newMap = newMap;
        this.changer = changer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}