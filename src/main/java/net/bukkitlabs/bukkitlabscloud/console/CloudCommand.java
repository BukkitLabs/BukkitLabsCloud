package net.bukkitlabs.bukkitlabscloud.console;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public interface CloudCommand {

    boolean onCommand(@NotNull final Command command, final String[] args);

    @NotNull
    default List<String> onTab(@NotNull final Command command, final String[] args) {
        return Collections.emptyList();
    }
}


