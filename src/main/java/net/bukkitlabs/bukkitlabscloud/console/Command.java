package net.bukkitlabs.bukkitlabscloud.console;

import org.jetbrains.annotations.NotNull;

public class Command{
    private final CloudCommand cloudCommand;
    private final String name;
    private final String description;

    public Command(@NotNull final CloudCommand cloudCommand,@NotNull final String name,@NotNull final String description) {
        this.cloudCommand=cloudCommand;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public CloudCommand getCloudCommand(){
        return cloudCommand;
    }
}
