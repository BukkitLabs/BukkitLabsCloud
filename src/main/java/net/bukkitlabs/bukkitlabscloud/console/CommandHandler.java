package net.bukkitlabs.bukkitlabscloud.console;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class CommandHandler {
    private final Map<String, Command> commands;
    private final Scanner scanner;

    public CommandHandler() {
        commands = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void registerCommand(@NotNull final Command command) {
        commands.put(command.getLabel(), command);
    }

    @NotNull
    public List<Command> getAllRegisteredCommands() {
        return new ArrayList<>(commands.values());
    }

    public void startListening() {
        while (true) {
            String input = scanner.nextLine();
            /*
            if (input.equalsIgnoreCase("exit")) {

                break;
            }
             */
            String[] inputParts = input.split(" ");
            String commandLabel = inputParts[0];
            String[] args = Arrays.copyOfRange(inputParts, 1, inputParts.length);
            handleCommand(commandLabel, args);
        }
        //scanner.close();
    }

    private void handleCommand(@NotNull final String commandLabel, @NotNull final String[] args) {
        final Command command = commands.get(commandLabel);
        if (command == null) {
            BukkitLabsCloud.getLogger().log(Logger.Level.WARN, "Unknown Command (" + commandLabel + "). Type help for all Help!");
            return;
        }
        if (!command.getCloudCommand().onCommand(command, args))
            BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "Usage: " + command.getUsage());
    }
}
