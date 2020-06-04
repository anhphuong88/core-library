package com.app.core.thread;

import com.app.core.action.Action;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BackgroundHeavyThread {
    public static Future run(Action r) {
        return ExecutorSupplier.getInstance().getLightWeightBackgroundTasks().submit(new PriorityRunnable(Priority.HIGH) {
            @Override
            public void run() {
                r.invoke();
            }
        });
    }

    public static void cancel(Future future) {
        future.cancel(true);
    }

    public static void shutdown() {
        PriorityThreadPoolExecutor executor = ExecutorSupplier.getInstance().getLightWeightBackgroundTasks();
        try {
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {
        } finally {
            executor.isTerminated();
            executor.shutdownNow();
        }
    }
}
