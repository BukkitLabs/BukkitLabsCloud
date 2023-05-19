package net.bukkitlabs.bukkitlabscloud.packet;

import net.bukkitlabs.bukkitlabscloud.util.config.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;
import org.jetbrains.annotations.NotNull;

public class ConfigurationLoadEvent extends Packet {

    private final ConfigHandler configHandler;

    public ConfigurationLoadEvent(@NotNull final ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    @NotNull
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
