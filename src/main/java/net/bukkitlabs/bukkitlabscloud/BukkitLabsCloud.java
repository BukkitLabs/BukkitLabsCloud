package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.events.ConfigurationLoadEvent;
import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.EventCannotBeProcessedException;
import net.bukkitlabs.bukkitlabscloud.util.event.EventHandler;
import net.bukkitlabs.bukkitlabscloud.util.logger.Logger;

import java.io.IOException;

public class BukkitLabsCloud {

    private final Logger logger;
    private final EventHandler eventHandler;
    private final ConfigHandler configHandler;

    private BukkitLabsCloud() {
        eventHandler = new EventHandler();
        ConfigHandler tempConfigHandler;
        this.logger = new Logger();
        eventHandler.registerListener(logger);
        try {
            tempConfigHandler = new ConfigHandler();
            eventHandler.call(new ConfigurationLoadEvent(tempConfigHandler));
        } catch (IOException | EventCannotBeProcessedException exception) {
            this.logger.log(Logger.Level.ERROR, "Configs can't be loaded (System stops now): " + exception);
            tempConfigHandler = null;
            System.exit(0);
        }
        this.configHandler = tempConfigHandler;
        logger.log(Logger.Level.INFO, "Starting BukkitLabsCloud...");
    }

    public static void main(String[] args) {
        new BukkitLabsCloud();
    }

    public Logger getLogger() {
        return logger;
    }

    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }
}