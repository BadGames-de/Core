package de.badgames.cloudhelper.modules;

import de.badgames.cloudhelper.ICloudHandler;
import dev.derklaro.aerogel.Injector;
import eu.cloudnetservice.driver.inject.InjectionLayer;
import eu.cloudnetservice.driver.registry.ServiceRegistry;
import eu.cloudnetservice.modules.bridge.BridgeServiceHelper;
import eu.cloudnetservice.modules.bridge.player.PlayerManager;
import eu.cloudnetservice.modules.bridge.player.executor.ServerSelectorType;
import eu.cloudnetservice.wrapper.configuration.WrapperConfiguration;
import net.kyori.adventure.text.TextComponent;

import java.util.UUID;

/**
 * CloudHandler for CloudNET
 */
public class CloudNETHandler implements ICloudHandler {
    static BridgeServiceHelper bridgeServiceHelper = null;
    static PlayerManager playerManager = null;
    static WrapperConfiguration wrapperConfiguration = null;

    @Override
    public void init() {
        getBridgeService();
        getPlayerManager();
    }

    @Override
    public void setInGame() {
        getBridgeService().changeToIngame();
    }

    @Override
    public void setLobby() {
        // CloudNET does not have a specific "Lobby" state.
    }

    @Override
    public String getServerName() {
        return getWrapperConfiguration().serviceInfoSnapshot().name();
    }

    @Override
    public void changeMOTD(String motd) {
        getBridgeService().motd().set(motd);
    }

    @Override
    public String getMOTD() {
        return getBridgeService().motd().get();
    }

    @Override
    public boolean sendPlayerToLobby(UUID player) {
        return sendPlayerToGroup(player, "Lobby");
    }

    @Override
    public boolean sendPlayerToGroup(UUID player, String group) {
        try {
            getPlayerManager().playerExecutor(player)
                    .connectToGroup(group, ServerSelectorType.LOWEST_PLAYERS);

            return true;
        } catch (Exception ignore) {
        }

        return false;
    }

    @Override
    public boolean sendPlayerToServer(UUID player, String serverName) {
        try {
            getPlayerManager().playerExecutor(player)
                    .connect(serverName);

            return true;
        } catch (Exception ignore) {
        }

        return false;
    }

    @Override
    public void setMaxPlayers(int maxPlayers) {
        getBridgeService().maxPlayers().set(maxPlayers);
    }

    @Override
    public void setProperty(String key, String value) {
        // CloudNET does not support adding properties directly like this.

        // Figure out how to do the property stuff since we now have access to it using the WrapperConfiguration.
    }

    @Override
    public String getProperty(String key) {
        // CloudNET does not support getting properties directly like this.
        return null;
    }

    @Override
    public int getMaxPlayers() {
        return getBridgeService().maxPlayers().get();
    }

    @Override
    public void kickPlayer(UUID player, TextComponent reason) {
        getPlayerManager().playerExecutor(player).kick(reason);
    }

    private static BridgeServiceHelper getBridgeService() {
        if (bridgeServiceHelper == null) {
            try (InjectionLayer<Injector> injector = InjectionLayer.ext()) {
                bridgeServiceHelper = injector.instance(BridgeServiceHelper.class);
            } catch (Exception ignore) {
            }
        }

        return bridgeServiceHelper;
    }

    private static PlayerManager getPlayerManager() {
        if (playerManager == null) {
            try (InjectionLayer<Injector> injector = InjectionLayer.ext()) {
                ServiceRegistry serviceRegistry = injector.instance(ServiceRegistry.class);
                playerManager = serviceRegistry.defaultInstance(PlayerManager.class);
            } catch (Exception ignore) {
            }
        }

        return playerManager;
    }

    private static WrapperConfiguration getWrapperConfiguration() {
        if (wrapperConfiguration == null) {
            try (InjectionLayer<Injector> injector = InjectionLayer.ext()) {
                wrapperConfiguration = injector.instance(WrapperConfiguration.class);
            } catch (Exception ignore) {
            }
        }

        return wrapperConfiguration;
    }
}
