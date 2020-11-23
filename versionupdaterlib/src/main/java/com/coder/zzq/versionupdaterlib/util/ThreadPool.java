package com.coder.zzq.versionupdaterlib.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private static ScheduledExecutorService sExecutorService;

    public static void submit(Runnable task) {
        SingletonCreator.get().submit(task);
    }

    public static void submitDelay(Runnable task, int delay) {
        SingletonCreator.get().schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public static void shutdown() {
        if (sExecutorService != null) {
            sExecutorService.shutdownNow();
            sExecutorService = null;
        }
    }

    private static class SingletonCreator {
        static {
            sExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        public static ScheduledExecutorService get() {
            return sExecutorService;
        }
    }
}
