package de.badgames.pluginCore.commands;

import de.badgames.pluginCore.util.ConfigUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs instanceof Player player && player.hasPermission("set")) {
            if (args.length == 0) {
                player.sendMessage(Component.text("§c/set <name> §8-> §7Setzt eine Position."));
            } else {
                Location playerLocation = player.getLocation();

                Location blockLocation = playerLocation.getBlock().getLocation();

                Location newLocation = new Location(blockLocation.getWorld(), blockLocation.getX() + 0.5, blockLocation.getY(), blockLocation.getZ() + 0.5);
                newLocation.setYaw(playerLocation.getYaw());
                newLocation.setPitch(playerLocation.getPitch());

                player.teleport(newLocation);
                ConfigUtil.setLocation(args[0], newLocation);
                player.sendMessage(Component.text("§cPosition " + args[0] + " §7gesetzt."));
            }
        } else {
            cs.sendMessage(Component.text("§cKeine Rechte!"));
        }

        return false;
    }

}
