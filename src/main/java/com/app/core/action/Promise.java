package com.app.core.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Promise {
    private Func<Boolean> r;
    private Action success, failure;
    public Promise(@NonNull Func<Boolean> r, @Nullable Action success, @Nullable Action failure) {
        this.r = r;
        this.success = success;
        this.failure = failure;
    }

    public void run() {
        if (r.invoke()) {
            if (success != null) success.invoke();
        } else {
            if (failure != null) failure.invoke();;
        }
    }
}
