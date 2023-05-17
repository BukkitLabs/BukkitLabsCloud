package net.bukkitlabs.bukkitlabscloud.util.event;

public interface Cancelable {

    boolean isCanceled();

    void setCanceled(boolean canceled);
}
