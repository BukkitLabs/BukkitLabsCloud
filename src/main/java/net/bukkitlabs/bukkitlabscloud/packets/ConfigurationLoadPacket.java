package net.bukkitlabs.bukkitlabscloud.packets;

import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;
import org.jetbrains.annotations.NotNull;

public class ConfigurationLoadPacket extends Packet {

    private final ConfigHandler configHandler;

    public ConfigurationLoadPacket(@NotNull final ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    @NotNull
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
