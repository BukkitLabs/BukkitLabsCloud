package net.bukkitlabs.bukkitlabscloud.console;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import org.jetbrains.annotations.NotNull;

public abstract class CloudCommand{

    protected CloudCommand(@NotNull final String name, @NotNull final String description) {

    }

    public abstract boolean onCommand(@NotNull final Command command, @NotNull final String commandLabel, @NotNull final String[] args);
}


