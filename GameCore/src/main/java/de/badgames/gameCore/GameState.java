package de.badgames.gameCore;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

/**
 * This class is used to create a game state.
 */
public abstract class GameState {

    /**
     * The name of the game state.
     */
    @Getter
    private final String name;

    /**
     * The countdown for the game state.
     */
    public int count;

    /**
     * If the game state is paused.
     */
    public boolean paused = false;

    /**
     * Constructor for the game state.
     * @param name the name of the game state.
     * @param count the countdown for the game state.
     */
    public GameState(String name, int count) {
        this.name = name;
        this.count = count;
    }

    /**
     * This method is called when the game state is started.
     */
    public abstract void start();

    /**
     * This method is called when the game state should be forcefully started.
     * @param player the player that started the game state.
     */
    public void forceStart(Player player) {
    }

    /**
     * This method is called when an entity interaction happens.
     * @param event the event that is called when an entity interacts with something.
     */
    public void onEntityInteract(EntityInteractEvent event) {
    }

    /**
     * This method is called when an entity clicks at something in their inventory.
     * @param event the event that is called.
     */
    public void onInventoryClick(InventoryClickEvent event) {}

    /**
     * This method is called when a player interaction happens.
     * @param event the event that is called when a player interacts with something.
     */
    public void onInteract(PlayerInteractEvent event) {
    }

    /**
     * This method is called when a player interacts with an entity.
     * @param event the event that is called when a player interacts with an entity.
     */
    public void onInteractEntity(PlayerInteractEntityEvent event) {
    }

    /**
     * Use {@link GameState#onInteractEntity(PlayerInteractEntityEvent)} instead.
     * Unless location is needed.
     */
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
    }

    /**
     * This method is called when a player interacts with a redstone block.
     * @param event the event that is called when a player interacts with a redstone block.
     */
    public void onBlockRedstone(BlockRedstoneEvent event) {
    }

    /**
     * This method is called when a player moves.
     * @param event the event that is called when a player moves.
     */
    public void onMove(PlayerMoveEvent event) {
    }

    /**
     * This method is called when a player dies.
     * @param event the event that is called when a player dies.
     */
    public void onDeath(PlayerDeathEvent event) {
    }

    /**
     * This method is called when an entity takes damage.
     * @param event the event that is called when an entity takes damage.
     */
    public void onDamage(EntityDamageEvent event) {
    }

    /**
     * This method is called when an entity takes damage by another entity.
     * @param event the event that is called when an entity takes damage by another entity.
     */
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
    }

    /**
     * This method is called when a projectile hits anything.
     * @param event the event that is called when a projectile hits anything.
     */
    public void onProjectileHit(ProjectileHitEvent event) {
    }

    /**
     * This method is called when a projectile is launched.
     * @param event the event that is called when a projectile launched.
     */
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
    }

    /**
     * This method is called when a player places a block.
     * @param event the event that is called when a player places a block.
     */
    public void onPlace(BlockPlaceEvent event) {
    }

    /**
     * This method is called when a player drops an item.
     * @param event the event that is called when a player drops an item.
     */
    public void onDrop(PlayerDropItemEvent event) {
    }

    /**
     * This method is called when an entity picks up an item.
     * @param event the event that is called when an entity picks up an item.
     */
    public void onPickup(EntityPickupItemEvent event) {
    }

    /**
     * This method is called when a player breaks a block.
     * @param event the event that is called when a player breaks a block.
     */
    public void onBreak(BlockBreakEvent event) {
    }

    /**
     * This method is called when anything regarding block physics happens.
     * @param event the event information.
     */
    public void onPhysics(BlockPhysicsEvent event) {
    }

    /**
     * This method is called when an entity spawns.
     * @param event the event that is called when an entity spawns.
     */
    public void onSpawn(EntitySpawnEvent event) {
    }

    /**
     * This method is called when a player respawns.
     * @param event the event that is called when a player respawns.
     */
    public void onRespawn(PlayerRespawnEvent event) {
    }

    /**
     * This method is called after a player respawns.
     * @param event the event that is called after a player respawns.
     */
    public void onPostRespawn(PlayerPostRespawnEvent event) {
    }

    /**
     * This method is called when an entity explodes.
     * @param event the event that is called when an entity explodes.
     */
    public void onEntityExplode(EntityExplodeEvent event) {
    }

    /**
     * This method is called when a block explodes.
     * @param event the event that is called when a block explodes.
     */
    public void onBlockExplode(BlockExplodeEvent event) {
    }

    /**
     * This method is called when a firework explodes.
     * @param event the event that is called when a firework explodes.
     */
    public void onFirework(FireworkExplodeEvent event) {
    }

    /**
     * This method is called when hungerlevel changes.
     * @param event the event data.
     */
    public void onFood(FoodLevelChangeEvent event) {
    }

    /**
     * This method is called when a player tries to craft.
     * @param event the event.
     */
    public void onCraft(CraftItemEvent event) {
    }

    /**
     * This method is called when a player joins the game.
     * @param player the player that joined the game.
     */
    public void join(Player player) {
    }

    /**
     * This method is called when a player quits the game.
     * @param player the player that quit the game.
     */
    public void quit(Player player) {
    }

    /**
     * Handle player deaths.
     * @param player the player that died.
     */
    public void handleDeath(Player player) {
    }

}
