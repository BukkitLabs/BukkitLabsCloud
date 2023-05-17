package net.bukkitlabs.bukkitlabscloud.util.event;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class EventHandler {

    private final List<Listener> listenerList = new LinkedList<>();

    public void registerListener(@NotNull final Listener listener) {
        listenerList.add(listener);
    }

    public void call(@NotNull final Event event) throws EventCannotBeProcessedException {
        if (event instanceof Cancelable cancelable) {
            this.cancelableCall(cancelable);
            return;
        }
        for (Listener listener : listenerList) {
            for (final Method method : listener.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Callable.class) ||
                        method.getParameterTypes().length != 1 ||
                        !method.getParameterTypes()[0].equals(event.getClass())) continue;
                try {
                    method.invoke(listener, event);
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    throw new EventCannotBeProcessedException(event);
                }
            }
        }
    }

    private void cancelableCall(final Cancelable event) throws EventCannotBeProcessedException {
        boolean canceled = false;
        for (Listener listener : listenerList) {
            for (final Method method : listener.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Callable.class) ||
                        method.getParameterTypes().length != 1 ||
                        !method.getParameterTypes()[0].equals(event.getClass()) ||
                        (canceled && !method.getAnnotation(Callable.class).ignoreCancelled())) continue;
                try {
                    method.invoke(listener, event);
                    if (event.isCanceled()) canceled = true;
                } catch (IllegalAccessException | InvocationTargetException exception) {
                    throw new EventCannotBeProcessedException((Event) event);
                }
            }
        }
    }
}
