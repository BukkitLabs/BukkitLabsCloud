package net.bukkitlabs.bukkitlabscloud.util.event;

import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;
import org.jetbrains.annotations.NotNull;

public class PacketCannotBeProcessedException extends Exception {

    private final Packet packet;

    public PacketCannotBeProcessedException(@NotNull Packet packet) {
        super("Event " + packet.getEventName() + " can't be processed!");
        this.packet = packet;
    }

    @NotNull
    public Packet getEvent() {
        return packet;
    }
}
