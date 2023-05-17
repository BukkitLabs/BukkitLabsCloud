package net.bukkitlabs.bukkitlabscloud.util.event;

import net.bukkitlabs.bukkitlabscloud.util.event.api.Cancelable;
import net.bukkitlabs.bukkitlabscloud.util.event.api.Packet;
import org.jetbrains.annotations.NotNull;

public class CancelableTestPacket extends Packet implements Cancelable {

    private String testValue;
    private boolean canceled;

    public CancelableTestPacket(@NotNull String testValue) {
        this.testValue = testValue;
    }

    @NotNull
    public String getTestValue() {
        return testValue;
    }

    public void setTestValue(@NotNull String testValue) {
        this.testValue = testValue;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
