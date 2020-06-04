package com.app.core.thread;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

class PriorityThreadFactory implements ThreadFactory {
    private int mThreadPriority;

    PriorityThreadFactory(int mThreadPriority) {
        this.mThreadPriority = mThreadPriority;
    }

    @Override
    public Thread newThread(@NonNull Runnable r) {
        Runnable runnable = () -> {
            try {
                Process.setThreadPriority(mThreadPriority);
            } catch (Throwable ignored) {}
            r.run();
        };
        return new Thread(runnable);
    }
}
