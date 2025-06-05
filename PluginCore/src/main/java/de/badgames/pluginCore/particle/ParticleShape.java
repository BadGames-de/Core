package de.badgames.pluginCore.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface ParticleShape {

    void renderShape(Player player, Location[] locations, ParticleBuilder builder);

}
