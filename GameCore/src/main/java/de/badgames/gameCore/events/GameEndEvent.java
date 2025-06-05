package de.badgames.gameCore.events;

import de.badgames.gameCore.team.Team;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * This event is called when the game ends.
 * It is used to notify the game manager and other plugins that the game has ended.
 * The event contains the winning team.
 */
@Getter
public class GameEndEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Team winner;

    public GameEndEvent(Team winner) {
        this.winner = winner;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}