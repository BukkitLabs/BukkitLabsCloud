package net.bukkitlabs.bukkitlabscloud.util.event;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventHandler {

    private final Map<Method, Listener> methodeList = new HashMap<>();

    public void registerListener(@NotNull final Listener listener) {
        for (final Method method : listener.getClass().getDeclaredMethods()) {
            if (!method.isAnnotationPresent(Callable.class) ||
                    method.getParameterTypes().length != 1) continue;
            methodeList.put(method, listener);
        }
    }

    public void call(@NotNull final Event event) throws EventCannotBeProcessedException {
        final Method[] methods = this.methodeList.keySet().stream()
                .filter(method -> method.getParameterTypes()[0].equals(event.getClass()))
                .sorted((methode1, methode2) -> methode2.getAnnotation(Callable.class).priority().getSlot() -
                        methode1.getAnnotation(Callable.class).priority().getSlot())
                .toArray(Method[]::new);
        if (event instanceof Cancelable cancelable) {
            this.cancelableCall(cancelable, methods);
            return;
        }
        for (final Method method : methods) {
            if (!method.isAnnotationPresent(Callable.class) ||
                    method.getParameterTypes().length != 1 ||
                    !method.getParameterTypes()[0].equals(event.getClass())) continue;
            try {
                method.invoke(this.methodeList.get(method), event);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new EventCannotBeProcessedException(event);
            }
        }
    }

    private void cancelableCall(final @NotNull Cancelable event, final @NotNull Method[] methods) throws EventCannotBeProcessedException {
        boolean canceled = false;
        for (final Method method : methods) {
            if (!method.isAnnotationPresent(Callable.class) ||
                    method.getParameterTypes().length != 1 ||
                    !method.getParameterTypes()[0].equals(event.getClass()) ||
                    (canceled && !method.getAnnotation(Callable.class).ignoreCancelled())) continue;
            try {
                method.invoke(this.methodeList.get(method), event);
                if (event.isCanceled()) canceled = true;
            } catch (IllegalAccessException | InvocationTargetException exception) {
                throw new EventCannotBeProcessedException((Event) event);
            }
        }
    }
}
