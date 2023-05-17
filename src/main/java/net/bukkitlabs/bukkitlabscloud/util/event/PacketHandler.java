package net.bukkitlabs.bukkitlabscloud.util.event;

import net.bukkitlabs.bukkitlabscloud.util.event.api.Cancelable;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class PacketHandler {

    private final Map<Method, Listener> methodeList = new HashMap<>();

    public void registerListener(@NotNull final Listener listener) {
        for (final Method method : listener.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Packet.class) ||
                    method.getParameterTypes().length != 1) continue;
            methodeList.put(method, listener);
        }
    }

    public void call(@NotNull final net.bukkitlabs.bukkitlabscloud.util.event.api.Packet packet) throws PacketCannotBeProcessedException {
        final Method[] methods = this.methodeList.keySet().stream()
                .filter(method -> method.getParameterTypes()[0].equals(packet.getClass()))
                .sorted((methode1, methode2) -> methode2.getAnnotation(Packet.class).priority().getSlot() -
                        methode1.getAnnotation(Packet.class).priority().getSlot())
                .toArray(Method[]::new);
        if (packet instanceof Cancelable cancelable) {
            this.cancelableCall(cancelable, methods);
            return;
        }
        for (final Method method : methods) {
            if (!method.isAnnotationPresent(Packet.class) ||
                    method.getParameterTypes().length != 1 ||
                    !method.getParameterTypes()[0].equals(packet.getClass())) continue;
            try {
                method.invoke(this.methodeList.get(method), packet);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new PacketCannotBeProcessedException(packet);
            }
        }
    }

    private void cancelableCall(final @NotNull Cancelable event, final @NotNull Method[] methods) throws PacketCannotBeProcessedException {
        boolean canceled = false;
        for (final Method method : methods) {
            if (!method.isAnnotationPresent(Packet.class) ||
                    method.getParameterTypes().length != 1 ||
                    !method.getParameterTypes()[0].equals(event.getClass()) ||
                    (canceled && !method.getAnnotation(Packet.class).ignoreCancelled())) continue;
            try {
                method.invoke(this.methodeList.get(method), event);
                if (event.isCanceled()) canceled = true;
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new PacketCannotBeProcessedException((net.bukkitlabs.bukkitlabscloud.util.event.api.Packet) event);
            }
        }
    }
}
