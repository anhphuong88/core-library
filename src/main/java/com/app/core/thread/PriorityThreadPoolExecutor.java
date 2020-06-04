package com.app.core.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PriorityThreadPoolExecutor extends ThreadPoolExecutor {
    PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new PriorityBlockingQueue<>(),threadFactory);
    }

    @Override
    public Future<?> submit(@NonNull Runnable task) {
        PriorityFutureTask futureTask = new PriorityFutureTask((PriorityRunnable) task);
        execute(futureTask);
        return futureTask;
    }

    class PriorityFutureTask extends FutureTask<PriorityRunnable> implements Comparable<PriorityFutureTask> {

        final PriorityRunnable priorityRunnable;

        PriorityFutureTask(PriorityRunnable priorityRunnable) {
            super(priorityRunnable, null);
            this.priorityRunnable = priorityRunnable;
        }

        @Override
        public int compareTo(@NonNull PriorityFutureTask o) {
            Priority p1 = priorityRunnable.getPriority();
            Priority p2 = o.priorityRunnable.getPriority();
            return p2.ordinal() - p1.ordinal();
        }
    }
}
