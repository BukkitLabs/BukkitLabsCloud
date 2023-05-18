package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.packets.ConfigurationLoadEvent;
import net.bukkitlabs.bukkitlabscloud.console.CommandHandler;
import net.bukkitlabs.bukkitlabscloud.console.Logger;
import net.bukkitlabs.bukkitlabscloud.console.commands.HelpCommand;
import net.bukkitlabs.bukkitlabscloud.console.commands.ServerCommand;
import net.bukkitlabs.bukkitlabscloud.packets.ServerStartEvent;
import net.bukkitlabs.bukkitlabscloud.packets.ServerStopEvent;
import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.PacketCannotBeProcessedException;
import net.bukkitlabs.bukkitlabscloud.util.event.PacketHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BukkitLabsCloud implements Listener {

    private static Logger logger;
    private static PacketHandler packetHandler;
    private static ConfigHandler configHandler;
    private static CommandHandler commandHandler;

    private BukkitLabsCloud() {
        setPacketHandler(new PacketHandler());
        ConfigHandler tempConfigHandler;
        setLogger(new Logger());
        getPacketHandler().registerListener(getLogger());
        getPacketHandler().registerListener(this);
        try {
            tempConfigHandler = new ConfigHandler();
            getPacketHandler().call(new ConfigurationLoadEvent(tempConfigHandler));
        } catch (IOException | PacketCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Configs can't be loaded (System stops now): " + exception);
            tempConfigHandler = null;
            System.exit(0);
        }
        setConfigHandler(tempConfigHandler);
        setCommandHandler(new CommandHandler());
        this.registerCommands();
        getLogger().log(Logger.Level.INFO, "Starting BukkitLabsCloud...");
        try {
            getPacketHandler().call(new ServerStartEvent());
        } catch (PacketCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Event call failed: " + exception);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                getPacketHandler().call(new ServerStopEvent());
            } catch (PacketCannotBeProcessedException exception) {
                getLogger().log(Logger.Level.ERROR, "Event call failed: " + exception);
            }
        }));
        getCommandHandler().startListening();
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

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    private static void setCommandHandler(CommandHandler commandHandler) {
        BukkitLabsCloud.commandHandler = commandHandler;
    }

    private void registerCommands() {
        new HelpCommand();
        new ServerCommand();
    }
}