package net.bukkitlabs.bukkitlabscloud.util.event;

import org.jetbrains.annotations.NotNull;

public class AnotherCancelableTestEvent extends Event implements Cancelable {

    private String testValue;
    private boolean canceled;

    public AnotherCancelableTestEvent(@NotNull String testValue) {
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
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
