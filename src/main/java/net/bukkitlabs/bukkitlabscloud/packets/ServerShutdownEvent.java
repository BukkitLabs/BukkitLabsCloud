package net.bukkitlabs.bukkitlabscloud.packets;

import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;

public class ServerShutdownEvent extends Packet {

    public ServerShutdownEvent() {
        super(true);
    }
}
