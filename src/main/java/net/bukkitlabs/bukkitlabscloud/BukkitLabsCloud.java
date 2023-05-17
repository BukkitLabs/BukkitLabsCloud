package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.packets.ConfigurationLoadPacket;
import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.PacketCannotBeProcessedException;
import net.bukkitlabs.bukkitlabscloud.util.event.PacketHandler;
import net.bukkitlabs.bukkitlabscloud.console.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BukkitLabsCloud {

    private static Logger logger;
    private static PacketHandler packetHandler;
    private static ConfigHandler configHandler;

    private BukkitLabsCloud() {
        setPacketHandler(new PacketHandler());
        ConfigHandler tempConfigHandler;
        setLogger(new Logger());
        getPacketHandler().registerListener(getLogger());
        try {
            tempConfigHandler = new ConfigHandler();
            getPacketHandler().call(new ConfigurationLoadPacket(tempConfigHandler));
        } catch (IOException | PacketCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Configs can't be loaded (System stops now): " + exception);
            tempConfigHandler = null;
            System.exit(0);
        }
        setConfigHandler(tempConfigHandler);
        getLogger().log(Logger.Level.INFO, "Starting BukkitLabsCloud...");
    }

    public static void main(String[] args) {
        new BukkitLabsCloud();
    }

    @NotNull
    public static Logger getLogger() {
        return logger;
    }

    private static void setLogger(@NotNull Logger logger) {
        BukkitLabsCloud.logger = logger;
    }

    @NotNull
    public static PacketHandler getPacketHandler() {
        return packetHandler;
    }

    private static void setPacketHandler(@NotNull PacketHandler packetHandler) {
        BukkitLabsCloud.packetHandler = packetHandler;
    }

    @NotNull
    public static ConfigHandler getConfigHandler() {
        return configHandler;
    }

    private static void setConfigHandler(@NotNull ConfigHandler configHandler) {
        BukkitLabsCloud.configHandler = configHandler;
    }
}