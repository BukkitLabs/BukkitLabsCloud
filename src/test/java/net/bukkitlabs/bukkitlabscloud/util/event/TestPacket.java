package net.bukkitlabs.bukkitlabscloud.util.event;

import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;
import org.jetbrains.annotations.NotNull;

public class TestPacket extends Packet {

    private String testValue;

    public TestPacket(@NotNull String testValue) {
        this.testValue = testValue;
    }

    @NotNull
    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(@NotNull String testValue) {
        this.testValue = testValue;
    }
}
