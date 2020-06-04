package com.app.core.thread;

import com.app.core.action.Action;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BackgroundThread {
    public static Future run(Action r) {
        return ExecutorSupplier.getInstance().getBackgroundTasks().submit(new PriorityRunnable(Priority.MEDIUM) {
            @Override
            public void run() {
                r.invoke();
            }
        });
    }

    public static Future runNow(Action r) {
        return ExecutorSupplier.getInstance().getBackgroundTasks().submit(new PriorityRunnable(Priority.HIGH) {
            @Override
            public void run() {
                r.invoke();
            }
        });
    }

    public static Future runLater(Action r) {
        return ExecutorSupplier.getInstance().getBackgroundTasks().submit(new PriorityRunnable(Priority.LOW) {
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
        PriorityThreadPoolExecutor executor = ExecutorSupplier.getInstance().getBackgroundTasks();
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
