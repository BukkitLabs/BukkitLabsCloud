package net.bukkitlabs.bukkitlabscloud.command;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.CloudCommand;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.Command;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.Logger;
import org.jetbrains.annotations.NotNull;

public class ServerCommand implements CloudCommand {

    public ServerCommand() {
        BukkitLabsCloud.getCommandHandler().registerCommand(new Command.Builder()
                .setCloudCommand(this)
                .setLabel("server")
                .setUsage("server [start|stop|restart|create] [{name}] [spigot|bukkit|paper] [{version}]")
                .getDescription("Interacts with the Servers!")
                .build());
    }

    @Override
    public boolean onCommand(@NotNull Command command, String[] args) {
        if (args[0] != null && args[1] != null) {
            final String option = args[0];
            final String name = args[1];
            switch (option) {
                case "start":
                    // TODO: TabCompletion for name
                    // TODO: api.startServer(name) resturns a Log (example: FINE:"Server name started" or ERROR:"Server name could not start + exception")
                    // Log log=api.startServer(name);
                    // BukkitLabsCloud.getLogger().log(log);
                    break;
                case "stop":
                    // TODO: TabCompletion for name
                    // TODO: api.stopServer(name) resturns a Log (example: FINE:"Server name stopped" or ERROR:"Server name could not stop + exception")
                    // Log log=api.stop(name);
                    // BukkitLabsCloud.getLogger().log(log);
                    break;
                case "restart":
                    // TODO: TabCompletion for name
                    // TODO: api.restartServer(name) resturns a Log (example: FINE:"Server name is restarting" or ERROR:"Server name could not restart + exception")
                    // Log log=api.restart(name);
                    // BukkitLabsCloud.getLogger().log(log);
                    break;
                case "create":
                    if (args[2] != null && args[3] != null) {
                        final String api = args[2];
                        final String version = args[3];
                        // TODO: TabCompletion for name
                        // TODO: TabCompletion for api
                        // TODO: TabCompletion for version
                        // TODO: api.createServer(name,api,version) resturns a Log (example: FINE:"Server name is creating" or ERROR:"Server name could not creat + exception")
                    } else {

                    }
                    break;
                default:
                    BukkitLabsCloud.getLogger().log(Logger.Level.ERROR, "Invalid arguement!");
                    break;

            }
        } else {

        }

        return false;
    }
}
