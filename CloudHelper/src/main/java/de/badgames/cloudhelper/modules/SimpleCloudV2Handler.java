package de.badgames.cloudhelper.modules;

import de.badgames.cloudhelper.ICloudHandler;
import eu.thesimplecloud.api.CloudAPI;
import eu.thesimplecloud.api.player.ICloudPlayer;
import eu.thesimplecloud.api.service.ICloudService;
import eu.thesimplecloud.api.service.ServiceState;
import eu.thesimplecloud.plugin.startup.CloudPlugin;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * CloudHandler for SimpleCloudV2
 */
public class SimpleCloudV2Handler implements ICloudHandler {
    private ICloudService cloudService;

    @Override
    public void init() {
        getCloudService();
    }

    @Override
    public void setInGame() {
        getCloudService().setState(ServiceState.INVISIBLE);
    }

    @Override
    public String getServerName() {
        return getCloudService().getName();
    }

    @Override
    public void changeMOTD(String motd) {
        getCloudService().setMOTD(motd);
    }

    @Override
    public String getMOTD() {
        return getCloudService().getMOTD();
    }

    @Override
    public boolean sendPlayerToLobby(UUID player) {
        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player);
        if (cloudPlayer != null) {
            cloudPlayer.sendToLobby().getBlocking();
            return true;
        }

        return false;
    }

    @Override
    public boolean sendPlayerToGroup(UUID player, String group) {
        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player);
        if (cloudPlayer != null) {
            var services = CloudAPI.getInstance().getCloudServiceManager().getCloudServicesByGroupName(group);
            if (services.isEmpty()) {
                return false;
            }

            var filteredServices = services.stream().filter(x -> x.getState() == ServiceState.VISIBLE || x.getState() == ServiceState.INVISIBLE).toList();

            if (filteredServices.isEmpty()) {
                return false;
            }

            var service = filteredServices.get((int) (Math.random() * filteredServices.size()));

            try {

                return CloudAPI.getInstance().getCloudPlayerManager().connectPlayer(cloudPlayer, service).isSuccess();
            } catch (Exception e) {
                Bukkit.getLogger().warning("Could not send player to group " + group + ": " + e.getMessage());
            }

            return false;
        }

        return false;
    }

    @Override
    public boolean sendPlayerToServer(UUID player, String serverName) {
        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player);
        if (cloudPlayer != null) {
            var service = CloudAPI.getInstance().getCloudServiceManager().getCloudServiceByName(serverName);
            if (service == null) {
                return false;
            }

            if (service.getState() != ServiceState.VISIBLE && service.getState() != ServiceState.INVISIBLE) {
                return false;
            }

            try {

                return CloudAPI.getInstance().getCloudPlayerManager().connectPlayer(cloudPlayer, service).isSuccess();
            } catch (Exception e) {
                Bukkit.getLogger().warning("Could not send player to server " + serverName + ": " + e.getMessage());
            }

            return false;
        }

        return false;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        getCloudService().setMaxPlayers(maxPlayers);
    }

    @Override
    public void setProperty(String key, String value) {
        getCloudService().setProperty(key, value);
    }

    @Override
    public String getProperty(String key) {
        return getCloudService().getProperty(key).getValueAsString();
    }

    @Override
    public int getMaxPlayers() {
        return getCloudService().getMaxPlayers();
    }

    @Override
    public void kickPlayer(UUID player, TextComponent reason) {
        ICloudPlayer cloudPlayer = CloudAPI.getInstance().getCloudPlayerManager().getCachedCloudPlayer(player);
        if (cloudPlayer != null) {
            cloudPlayer.kick(reason.content()).getBlocking();
        }
    }

    public ICloudService getCloudService() {
        if (cloudService == null) {
            cloudService = CloudPlugin.getInstance().thisService();
        }
        return cloudService;
    }
}
