package de.badgames.gameCore.team;

import de.badgames.prefix.api.PrefixApi;
import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Interface for managing teams in a game.
 * @param <T> The type of team being managed.
 */
public interface ITeamManager<T extends Team> {

    /**
     * Returns the PrefixApi instance used for managing team prefixes.
     * @return The PrefixApi instance.
     */
    public PrefixApi getPrefixApi();

    /**
     * Registers the teams with the PrefixApi.
     */
    default public void registerPrefixGroups() {
        if (getPrefixApi() == null) {
            return;
        }

        int index = 1000;
        for (Team team : getTeams()) {
            team.setPriority(index++);
            getPrefixApi().addGroup(team);
        }
    }

    /**
     * Returns the team based on the given name.
     * @param name The name of the team.
     * @return The team with the given name, or null if not found.
     */
    public default T getTeam(String name) {
        for (T team : getTeams()) {
            if (team.getName().equalsIgnoreCase(name)) {
                return team;
            }
        }

        return null;
    }

    /**
     * Returns the team of the given player.
     * @param player The player whose team is to be found.
     * @return The team of the player, or null if not found.
     */
    public default T getTeam(Player player) {
        for (T team : getTeams()) {
            if (team.containsPlayer(player)) {
                return team;
            }
        }

        return null;
    }

    /**
     * Ticks all teams, updating their states.
     */
    public default void tick() {
        for (T team : getTeams()) {
            team.tick();
        }
    }

    /**
     * Check if the two players are in the same team-
     * @param player first player.
     * @param player2 second player.
     * @return True if they are in the same team.
     */
    default boolean sameTeam(Player player, Player player2) {
        return getTeam(player).equals(getTeam(player2));
    }

    /**
     * Adds a team to the manager.
     * @param team The team to be added.
     */
    public default void addTeam(T team) {
        getTeams().add(team);
    }

    /**
     * Get team with the least players.
     * @return The team with the least players.
     */
    public T getTeamWithLeastPlayers();

    /**
     * Get all teams.
     * @return A list of all teams.
     */
    public ArrayList<T> getTeams();
}
