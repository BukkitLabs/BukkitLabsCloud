package net.bukkitlabs.bukkitlabscloud.console;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import org.jetbrains.annotations.NotNull;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;

import java.util.*;

public class CommandHandler {
    private final Map<String, Command> commands;
    private final LineReader reader;

    public CommandHandler() {
        commands = new HashMap<>();
        reader = LineReaderBuilder.builder()
                .completer(new CompleterHandler())
                .build();
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
            final String input = reader.readLine();
            if (input.equalsIgnoreCase("exit")) {
                System.exit(0);
                return;
            }
            final String[] inputParts = input.split(" ");
            final String commandLabel = inputParts[0];
            final String[] args = Arrays.copyOfRange(inputParts, 1, inputParts.length);
            handleCommand(commandLabel, args);
        }
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
