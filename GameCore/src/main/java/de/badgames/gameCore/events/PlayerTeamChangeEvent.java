package de.badgames.gameCore.events;

import de.badgames.gameCore.team.Team;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a player changes their team.
 */
@Getter
public class PlayerTeamChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private final Team oldTeam, newTeam;

    private final Player player;

    public PlayerTeamChangeEvent(Team oldTeam, Team newTeam, Player player) {
        this.oldTeam = oldTeam;
        this.newTeam = newTeam;
        this.player = player;
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