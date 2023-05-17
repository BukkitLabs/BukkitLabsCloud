package net.bukkitlabs.bukkitlabscloud.util.event;

import org.jetbrains.annotations.NotNull;

public abstract class Event {

    private final boolean async;
    private String name;

    protected Event() {
        this(false);
    }

    protected Event(boolean isAsync) {
        this.async = isAsync;
    }

    protected Event(@NotNull String name) {
        this(name, false);
    }

    protected Event(@NotNull String name, boolean isAsync) {
        this.name = name;
        this.async = isAsync;
    }

    @NotNull
    public String getEventName() {
        return this.name == null ? this.getClass().getSimpleName() : this.name;
    }

    public final boolean isAsynchronous() {
        return this.async;
    }
}
