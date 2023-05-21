package net.bukkitlabs.bukkitlabscloud.packet;

import net.bukkitlabs.bukkitlabscloudapi.internal.event.Packet;

public class ServerShutdownEvent extends Packet {

    public ServerShutdownEvent() {
        super(true);
    }
}
