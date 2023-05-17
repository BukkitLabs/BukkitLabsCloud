package net.bukkitlabs.bukkitlabscloud.console;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private Map<String, Command> commands;

    public CommandHandler() {
        commands = new HashMap<>();
    }

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }
}
