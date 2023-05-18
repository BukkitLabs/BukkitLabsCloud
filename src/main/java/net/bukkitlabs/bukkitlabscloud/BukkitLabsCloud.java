package net.bukkitlabs.bukkitlabscloud;

import net.bukkitlabs.bukkitlabscloud.console.CommandHandler;
import net.bukkitlabs.bukkitlabscloud.console.Logger;
import net.bukkitlabs.bukkitlabscloud.console.commands.HelpCommand;
import net.bukkitlabs.bukkitlabscloud.console.commands.ServerCommand;
import net.bukkitlabs.bukkitlabscloud.events.ConfigurationLoadEvent;
import net.bukkitlabs.bukkitlabscloud.events.ServerStartEvent;
import net.bukkitlabs.bukkitlabscloud.events.ServerStopEvent;
import net.bukkitlabs.bukkitlabscloud.handler.ConfigHandler;
import net.bukkitlabs.bukkitlabscloud.util.event.EventCannotBeProcessedException;
import net.bukkitlabs.bukkitlabscloud.util.event.EventHandler;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class BukkitLabsCloud {

    private static Logger logger;
    private static EventHandler eventHandler;
    private static ConfigHandler configHandler;
    private static CommandHandler commandHandler;

    private BukkitLabsCloud() {
        setEventHandler(new EventHandler());
        ConfigHandler tempConfigHandler;
        setLogger(new Logger());
        getEventHandler().registerListener(getLogger());
        try {
            tempConfigHandler = new ConfigHandler();
            getEventHandler().call(new ConfigurationLoadEvent(tempConfigHandler));
        } catch (IOException | EventCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Configs can't be loaded (System stops now): " + exception);
            tempConfigHandler = null;
            System.exit(0);
        }
        setConfigHandler(tempConfigHandler);
        setCommandHandler(new CommandHandler());
        this.registerCommands();
        getLogger().log(Logger.Level.INFO, "Starting BukkitLabsCloud...");
        try {
            getEventHandler().call(new ServerStartEvent());
        } catch (EventCannotBeProcessedException exception) {
            getLogger().log(Logger.Level.ERROR, "Event call failed: " + exception);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                getEventHandler().call(new ServerStopEvent());
            } catch (EventCannotBeProcessedException exception) {
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
    public static EventHandler getEventHandler() {
        return eventHandler;
    }

    private static void setEventHandler(@NotNull EventHandler eventHandler) {
        BukkitLabsCloud.eventHandler = eventHandler;
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