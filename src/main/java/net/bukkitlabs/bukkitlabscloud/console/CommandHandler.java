package net.bukkitlabs.bukkitlabscloud.console;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import net.bukkitlabs.bukkitlabscloud.console.commands.HelpCommand;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandHandler{
    private final Map<String,Command> commands;
    private Scanner scanner;

    public CommandHandler(){
        commands=new HashMap<>();
        scanner=new Scanner(System.in);
    }

    public void registerCommand(@NotNull final CloudCommand cloudCommand,@NotNull final String name,@NotNull final String description){
        Command command=new Command(cloudCommand,name,description);
        commands.put(name,command);
    }

    public void startListening(){
        while(true){
            String input=scanner.nextLine();
            /*
            if (input.equalsIgnoreCase("exit")) {

                break;
            }
             */
            String[] inputParts=input.split(" ");
            String commandLabel=inputParts[0];
            String[] args=Arrays.copyOfRange(inputParts,1,inputParts.length);
            handleCommand(commandLabel,args);
        }
        //scanner.close();
    }

    private void handleCommand(@NotNull final String commandLabel,@NotNull final String[] args){
        Command command=commands.get(commandLabel);
        if(command!=null){
            CloudCommand cloudCommand=command.getCloudCommand();
            cloudCommand.onCommand(command,commandLabel,args);
        }else{
            BukkitLabsCloud.getLogger().log(Logger.Level.WARN,"Unknown Command ("+commandLabel+"). Type help for all Help!");
        }
    }

}
