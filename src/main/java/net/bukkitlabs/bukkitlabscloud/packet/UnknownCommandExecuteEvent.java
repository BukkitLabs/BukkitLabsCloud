package net.bukkitlabs.bukkitlabscloud.packet;

import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;
import org.jetbrains.annotations.NotNull;

public class UnknownCommandExecuteEvent extends Packet{
    private final String commandLabel;
    private final String[] args;

    public UnknownCommandExecuteEvent(@NotNull final String commandLabel,@NotNull final String[] args){
        this.commandLabel=commandLabel;
        this.args=args;
    }
    public String getCommandLabel(){
        return commandLabel;
    }

    public String[] getArgs(){
        return args;
    }
}
