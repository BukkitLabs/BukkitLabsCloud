package net.bukkitlabs.bukkitlabscloud.util;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoopManager{
    private final List<Runnable> loops;
    private final ExecutorService executor;

    public LoopManager() {
        loops = new ArrayList<>();
        executor = Executors.newCachedThreadPool();
    }

    public void addLoop(@NotNull final Runnable loop) {
        loops.add(loop);
    }

    public void start() {
        for (Runnable loop : loops) {
            executor.submit(loop);
        }
    }

    public void stop() {
        executor.shutdownNow();
    }
}
