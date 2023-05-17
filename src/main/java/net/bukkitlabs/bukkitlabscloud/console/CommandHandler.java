package net.bukkitlabs.bukkitlabscloud.console;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler  implements Runnable {

    private final Map<String, Command> commands;
    private Scanner scanner;

    public CommandHandler() {
        commands = new HashMap<>();
    }

    public void registerCommand(Command command) {
        commands.put(command.getName(), command);
    }

    @Override
    public void run(){
        while (!Thread.currentThread().isInterrupted()) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                continue;
            }
            String[] inputParts = input.split(" ");
            String commandLabel = inputParts[0];

            Command command = commands.get(commandLabel);
            if (command != null) {
                String[] args = new String[inputParts.length - 1];
                System.arraycopy(inputParts, 1, args, 0, args.length);
                //onCommand()
            } else {
                System.out.println("Unbekanntes Kommando: " + commandLabel);
            }
        }
    }
}
