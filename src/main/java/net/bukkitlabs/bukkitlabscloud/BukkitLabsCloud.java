package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.command.HelpCommand;
import net.bukkitlabs.bukkitlabscloud.command.ServerCommand;
import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.packet.ServerInitializeEvent;
import net.bukkitlabs.bukkitlabscloud.packet.ServerShutdownEvent;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.CommandHandler;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.Logger;
import net.bukkitlabs.bukkitlabscloudapi.internal.event.Listener;
import net.bukkitlabs.bukkitlabscloudapi.internal.event.PacketCannotBeProcessedException;
import net.bukkitlabs.bukkitlabscloudapi.internal.event.PacketCatch;
import net.bukkitlabs.bukkitlabscloudapi.internal.event.PacketHandler;
import net.bukkitlabs.bukkitlabscloudapi.internal.packet.LoggerConfigurationLoadEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BukkitLabsCloud implements Listener {

    private static Logger logger;
    private static PacketHandler packetHandler;
    private static ConfigHandler configHandler;
    private static CommandHandler commandHandler;

    private BukkitLabsCloud() {
        setPacketHandler(new PacketHandler());
        setLogger(new Logger());
        getPacketHandler().registerListener(getLogger());
        getPacketHandler().registerListener(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                getPacketHandler().call(new ServerShutdownEvent());
            } catch (PacketCannotBeProcessedException exception) {
                getLogger().log(Logger.Level.ERROR, "Event call failed: " + exception);
            }
        }));

        try {
            getPacketHandler().call(new ServerInitializeEvent());
        } catch (PacketCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Event call failed: " + exception);
        }

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

    @PacketCatch
    private void onServerInitialization(final ServerInitializeEvent event) {
        try {
            setConfigHandler(new ConfigHandler());
            getPacketHandler().call(new LoggerConfigurationLoadEvent(
                    getConfigHandler().getGeneralConfiguration().getString("logger.timeFormat", null),
                    getConfigHandler().getGeneralConfiguration().getString("logger.dateFormat", null)
            ));
        } catch (IOException | PacketCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Configs can't be loaded (System stops now): " + exception);
            System.exit(0);
        }

        setCommandHandler(new CommandHandler(BukkitLabsCloud.getPacketHandler(), BukkitLabsCloud.getLogger()));
        this.registerCommands();

        getLogger().log(Logger.Level.INFO, "Starting BukkitLabsCloud...");
    }

    @PacketCatch
    private void onServerShutdown(final ServerShutdownEvent event) {
        getLogger().log(Logger.Level.INFO, "Goodbye...");
    }

    private void registerCommands() {
        new HelpCommand();
        new ServerCommand();
    }
}