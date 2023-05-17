package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.util.logger.Logger;

public class BukkitLabsCloud {

    private final Logger logger;

    public static void main(String[] args) {
        new BukkitLabsCloud();
    }

    private BukkitLabsCloud(){
        this.logger=new Logger();
        logger.log(Logger.Level.INFO,"Starting BukkitLabsCloud...");
    }
    public Logger getLogger(){
        return logger;
    }
}