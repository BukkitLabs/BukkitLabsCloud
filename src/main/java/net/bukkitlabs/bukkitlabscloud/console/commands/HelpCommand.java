package net.bukkitlabs.bukkitlabscloud.console.commands;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import net.bukkitlabs.bukkitlabscloud.console.CloudCommand;
import net.bukkitlabs.bukkitlabscloud.console.Command;
import net.bukkitlabs.bukkitlabscloud.console.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class HelpCommand implements CloudCommand {

    public HelpCommand() {
        BukkitLabsCloud.getCommandHandler().registerCommand(new Command.Builder()
                .setCloudCommand(this)
                .setLabel("help")
                .setUsage("help ?[{name}]")
                .getDescription("Shows you a List of commands!")
                .build());
    }

    @Override
    public boolean onCommand(@NotNull Command command, String[] args) {
        final String line = "================================================";
        switch (args.length) {
            case 0 -> {
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, line);
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "");
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "Commands:");
                BukkitLabsCloud.getCommandHandler().getAllRegisteredCommands().forEach(iteration ->
                        BukkitLabsCloud.getLogger().log(Logger.Level.INFO, iteration.getLabel() + " -> " + iteration.getDescription()));
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "");
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, line);
                return true;
            }
            case 1 -> {
                Command target = BukkitLabsCloud.getCommandHandler()
                        .getAllRegisteredCommands()
                        .stream()
                        .filter(iteration -> iteration.getLabel().equalsIgnoreCase(args[0]))
                        .findFirst()
                        .orElse(null);
                if (target == null) {
                    BukkitLabsCloud.getLogger().log(Logger.Level.WARN, "The command " + args[0] + " was not found!");
                    return true;
                }
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, line);
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "");
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "Label: " + target.getLabel());
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "Usage: " + target.getUsage());
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "Description: " + target.getDescription());
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "");
                BukkitLabsCloud.getLogger().log(Logger.Level.INFO, line);
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public @NotNull List<String> onTab(@NotNull Command command, String[] args) {
        return args.length == 1 ? BukkitLabsCloud.getCommandHandler()
                .getAllRegisteredCommands()
                .stream()
                .map(Command::getLabel)
                .toList() : Collections.emptyList();
    }
}