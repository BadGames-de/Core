package de.badgames.gameCore;

import de.badgames.gameCore.map.GenericMap;
import de.badgames.gameCore.team.ITeamManager;
import de.badgames.gameCore.team.Team;
import de.badgames.gameCore.util.MapLoader;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Interface for the GameManager.
 * @param <T> extends GenericMap.
 * @param <R> extends Team.
 */
public interface IGameManager<T extends GenericMap, R extends Team> {

    /**
     * Starts the game.
     * @param currentState the current state of the game.
     */
    public void setCurrentState(GameState currentState);

    /**
     * Joins a player to the game.
     * @param player the player to join.
     */
    public default void join(Player player) {
        getCurrentState().join(player);
    }

    /**
     * Quits a player from the game.
     * @param player the player to quit.
     */
    public default void quit(Player player) {
        getCurrentState().quit(player);
    }

    /**
     * Changes the map of the game.
     * @param map the new map to set.
     * @param player the player who changed the map.
     */
    public void changeMap(T map, Player player);

    /**
     * Get max team size.
     * @return the max team size.
     */
    public int getMaxTeamSize();

    /**
     * Get the current map.
     * @return the current map.
     */
    public T getMap();

    /**
     * Get the map loader.
     * @return the map loader.
     */
    public MapLoader<T> getMapLoader();

    /**
     * Check if a specific path is set in the map config.
     * @param path the path to check.
     * @return true if the path is set, false otherwise.
     */
    public boolean isSetMap(String path);

    /**
     * Get the map location.
     * @param path the path to the map.
     * @return the location of the map.
     */
    public Location getMapLocation(String path);

    /**
     * Get the team manager.
     * @return the team manager.
     */
    public ITeamManager<R> getTeamManager();

    /**
     * Get the current state of the game.
     * @return the current state of the game.
     */
    public GameState getCurrentState();

    /**
     * Check if in this state a player should be allowed to run lobby commands.
     * @return true if the player can run lobby commands, false otherwise.
     */
    public boolean canRunLobbyCommands();

}
