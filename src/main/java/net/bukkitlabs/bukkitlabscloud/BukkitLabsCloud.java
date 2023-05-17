package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.logger.Logger;

import java.io.IOException;

public class BukkitLabsCloud {

    private static BukkitLabsCloud instance;
    private final Logger logger;
    private final ConfigHandler configHandler;

    private BukkitLabsCloud() {
        instance=this;
        ConfigHandler tempConfigHandler;
        this.logger = new Logger();
        try {
            tempConfigHandler = new ConfigHandler();
        } catch (IOException exception) {
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
    public static BukkitLabsCloud getInstance(){
        return instance;
    }
}