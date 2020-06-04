package com.app.core.thread;

import android.os.Process;

import com.app.core.log.Logger;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class ExecutorSupplier {
    private static final int Cores = Runtime.getRuntime().availableProcessors();
    private final MainThreadExecutor mMainThreadTasks;
    private final PriorityThreadPoolExecutor mBackgroundTasks;
    private final PriorityThreadPoolExecutor mLightWeightBackgroundTasks;

    private static ExecutorSupplier instance;

    public static ExecutorSupplier getInstance() {
        if (instance == null) {
            synchronized (ExecutorSupplier.class) {
                instance = new ExecutorSupplier();
            }
        }
        return instance;
    }

    private ExecutorSupplier() {
        ThreadFactory backgroundFactory = new PriorityThreadFactory(Process.THREAD_PRIORITY_BACKGROUND);
        mBackgroundTasks = new PriorityThreadPoolExecutor(Cores, Cores, 0L, TimeUnit.SECONDS, backgroundFactory);
        mLightWeightBackgroundTasks = new PriorityThreadPoolExecutor(Cores, Cores, 0L, TimeUnit.SECONDS, backgroundFactory);
        mMainThreadTasks = new MainThreadExecutor();

        Logger.w("so luong core: "+Cores);
    }

    public MainThreadExecutor getMainThreadTasks() {
        return mMainThreadTasks;
    }

    public PriorityThreadPoolExecutor getBackgroundTasks() {
        return mBackgroundTasks;
    }

    public PriorityThreadPoolExecutor getLightWeightBackgroundTasks() {
        return mLightWeightBackgroundTasks;
    }
}
