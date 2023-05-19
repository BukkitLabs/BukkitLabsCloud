package net.bukkitlabs.bukkitlabscloud.util.event.api;

import org.jetbrains.annotations.NotNull;

public abstract class Packet {

    private final boolean async;
    private String name;

    protected Packet() {
        this(false);
    }

    protected Packet(boolean isAsync) {
        this.async = isAsync;
    }

    protected Packet(@NotNull String name) {
        this(name, false);
    }

    protected Packet(@NotNull String name, boolean isAsync) {
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
