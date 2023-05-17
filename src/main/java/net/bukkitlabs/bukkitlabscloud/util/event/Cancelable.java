package net.bukkitlabs.bukkitlabscloud.util.event;

public interface Cancelable {

    void setCanceled(boolean canceled);

    boolean isCanceled();
}
