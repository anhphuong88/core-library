package com.app.core.thread;

public class UIThread {
    public static void run(Runnable r) {
        ExecutorSupplier.getInstance().getMainThreadTasks().execute(r);
    }

    public static void run(Runnable r, long delay) {
        ExecutorSupplier.getInstance().getMainThreadTasks().execute(r, delay);
    }
}
