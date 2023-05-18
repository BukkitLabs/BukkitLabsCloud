package net.bukkitlabs.bukkitlabscloud.console.commands;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import net.bukkitlabs.bukkitlabscloud.console.CloudCommand;
import net.bukkitlabs.bukkitlabscloud.console.Command;
import net.bukkitlabs.bukkitlabscloud.console.CommandHandler;
import net.bukkitlabs.bukkitlabscloud.console.Logger;

public class HelpCommand extends CloudCommand {

    public HelpCommand(){
       super("help","Help Command");
    }

    @Override
    public boolean onCommand(Command command,String commandLabel,String[] args){
        BukkitLabsCloud.getLogger().log(Logger.Level.INFO,"================================================");
        BukkitLabsCloud.getLogger().log(Logger.Level.INFO,"");
        BukkitLabsCloud.getLogger().log(Logger.Level.INFO,"Commands:");
        BukkitLabsCloud.getLogger().log(Logger.Level.INFO,"");
        BukkitLabsCloud.getLogger().log(Logger.Level.INFO,"");
        BukkitLabsCloud.getLogger().log(Logger.Level.INFO,"================================================");
        return false;
    }
}