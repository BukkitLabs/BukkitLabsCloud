package net.bukkitlabs.bukkitlabscloud.events;

import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.Event;

public class ConfigurationLoadEvent extends Event {

    private final ConfigHandler configHandler;

    public ConfigurationLoadEvent(ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }
}
