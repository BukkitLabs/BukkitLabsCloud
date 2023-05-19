package net.bukkitlabs.bukkitlabscloud.util.event.api;

public interface Cancelable {

    boolean isCanceled();

    void setCanceled(boolean canceled);
}
