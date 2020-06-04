package com.app.core.thread;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

class MainThreadExecutor implements Executor {
    private final Handler handler = new Handler(Looper.getMainLooper());
    @Override
    public void execute(@NonNull Runnable command) {
        handler.post(command);
    }

    public void execute(@NonNull Runnable command, long delay) {
        handler.postDelayed(command, delay);
    }
}
