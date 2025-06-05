package de.badgames.gameCore.events;

import de.badgames.gameCore.map.IMap;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event that is called when the game starts.
 */
@Getter
public class GameStartedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final IMap map;

    private final Player forceStarter;

    public GameStartedEvent(IMap map, Player forceStarter) {
        this.map = map;
        this.forceStarter = forceStarter;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }


    public static HandlerList getHandlerList() {
        return handlers;
    }
}