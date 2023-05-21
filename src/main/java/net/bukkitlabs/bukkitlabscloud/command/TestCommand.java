package net.bukkitlabs.bukkitlabscloud.command;

import net.bukkitlabs.bukkitlabscloud.BukkitLabsCloud;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.CloudCommand;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.Command;
import net.bukkitlabs.bukkitlabscloudapi.internal.console.Logger;
import net.bukkitlabs.bukkitlabscloudapi.socket.packet.ServerRegisterPacket;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetSocketAddress;

public class TestCommand implements CloudCommand {

    public TestCommand() {
        BukkitLabsCloud.getCommandHandler().registerCommand(new Command.Builder()
                .setCloudCommand(this)
                .setLabel("test")
                .setUsage("test")
                .getDescription("A Command to test the application!")
                .build());
    }

    @Override
    public boolean onCommand(@NotNull Command command, String[] strings) {
        try {
            BukkitLabsCloud.getServer().broadcast(new ServerRegisterPacket(
                    "TestServer",
                    new InetSocketAddress("ip", 22)
            ));
            BukkitLabsCloud.getLogger().log(Logger.Level.INFO, "Packet send!");
        } catch (IOException exception) {
            BukkitLabsCloud.getLogger().log(Logger.Level.ERROR, "Packet cannot be broadcast: ", exception);
        }
        return true;
    }
}
