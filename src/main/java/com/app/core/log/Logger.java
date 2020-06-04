package com.app.core.log;

import android.util.Log;

/**
 * Created by phuong on 05/03/2017.
 */

public final class Logger {

    public static void w(String object) {
        if (Trace.enable()) {
            String LOG = "android:com.app.core";
            if (object != null) Log.w(LOG, object);
        }
    }
    public static void d(String object) {
        if (Trace.enable()) {
            String LOG = "android:com.app.core";
            if (object != null) Log.d(LOG, object);
        }
    }
}
