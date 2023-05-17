package net.bukkitlabs.bukkitlabscloud.util.event;

import org.jetbrains.annotations.NotNull;

public class TestEvent extends Event {

    private String testValue;

    public TestEvent(@NotNull String testValue) {
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
