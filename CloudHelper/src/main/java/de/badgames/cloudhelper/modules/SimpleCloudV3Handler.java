package de.badgames.cloudhelper.modules;

import app.simplecloud.controller.api.ControllerApi;
import app.simplecloud.controller.shared.server.Server;
import app.simplecloud.droplet.player.api.PlayerApi;
import build.buf.gen.simplecloud.controller.v1.ServerState;
import build.buf.gen.simplecloud.droplet.player.v1.CloudPlayerConnectResult;
import de.badgames.cloudhelper.ICloudHandler;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

/**
 * CloudHandler for SimpleCloudV3
 */
public class SimpleCloudV3Handler implements ICloudHandler {
    private PlayerApi.Future playerApi;
    private ControllerApi.Future controllerApi;
    private Server simpleCloudServer;

    @Override
    public void init() {
        getPlayerAPI();
        getControllerAPI();
    }

    @Override
    public void setInGame() {
        getControllerAPI().getServers().updateServerState(getUniqueId(), ServerState.INGAME);
    }

    @Override
    public String getServerName() {
        return getServer(false).getGroup() + "-" + getServer(false).getNumericalId();
    }

    @Override
    public void changeMOTD(String motd) {
        getControllerAPI().getServers().updateServerProperty(getUniqueId(), "motd", motd);
    }

    @Override
    public String getMOTD() {
        return getServer(true).getProperties().get("motd");
    }

    @Override
    public boolean sendPlayerToLobby(UUID player) {
        return sendPlayerToGroup(player, "lobby");
    }

    @Override
    public boolean sendPlayerToGroup(UUID player, String group) {
        var servers = controllerApi.getServers().getServersByGroup(group).join();
        if (servers.isEmpty()) {
            return false;
        }

        var server = servers.get((int) (Math.random() * servers.size()));
        return sendPlayerToServer(player, group + "-" + server.getNumericalId());
    }

    @Override
    public boolean sendPlayerToServer(UUID player, String serverName) {
        return playerApi.connectPlayer(player, serverName).join() == CloudPlayerConnectResult.SUCCESS;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        // Not supported by SimpleCloud
    }

    @Override
    public void setProperty(String key, String value) {
        getControllerAPI().getServers().updateServerProperty(getUniqueId(), key, value);
    }

    @Override
    public String getProperty(String key) {
        return getControllerAPI().getServers().getServerById(getUniqueId()).join().getProperties().get(key);
    }

    @Override
    public int getMaxPlayers() {
        return (int) getServer(false).getMaxPlayers();
    }

    @Override
    public void kickPlayer(UUID player, TextComponent reason) {
        getPlayerAPI().getOnlinePlayer(player).thenAccept(cloudPlayer -> {
            if (cloudPlayer != null) {
                cloudPlayer.kick(reason);
            }
        });
    }

    public String getUniqueId() {
        return System.getenv("SIMPLECLOUD_UNIQUE_ID");
    }

    public Server getServer(boolean ignoreCache) {
        if (simpleCloudServer == null || ignoreCache) {
            simpleCloudServer = getControllerAPI().getServers().getServerById(getUniqueId()).join();
        }
        return simpleCloudServer;
    }

    public ControllerApi.Future getControllerAPI() {
        if (controllerApi == null) {
            controllerApi = ControllerApi.createFutureApi();
        }

        return controllerApi;
    }

    public PlayerApi.Future getPlayerAPI() {
        if (playerApi == null) {
            playerApi = PlayerApi.createFutureApi();
        }
        return playerApi;
    }
}