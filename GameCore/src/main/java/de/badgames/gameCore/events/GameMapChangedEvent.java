package de.badgames.gameCore.events;

import de.badgames.gameCore.map.IMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * GameMapChangedEvent is called when the map changed.
 * It contains the old map, the new map and the player who changed the map.
 * This event is called in GameManager when the map changes.
 */
@Getter
public class GameMapChangedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final IMap oldMap, newMap;

    private final Player changer;

    public GameMapChangedEvent(IMap oldMap, IMap newMap, Player changer) {
        this.oldMap = oldMap;
        this.newMap = newMap;
        this.changer = changer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}