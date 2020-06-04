package com.app.core.background;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.app.core.log.Logger;

public class LocationIntentService extends JobIntentService {
    static final int LOCATION_JOB_ID = 1000;
    private static final String ACTION_LOCATION = "action.LOCATION_DATA";

    public static void enqueueWork(@NonNull Context context) {
        Intent intent = new Intent(context, LocationIntentService.class);
        intent.setAction(ACTION_LOCATION);
        enqueueWork(context, LocationIntentService.class, LOCATION_JOB_ID, intent);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Logger.w("onHandleWork() called with: intent = [" + intent + "]");
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case ACTION_LOCATION:

                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.w("all work done");
    }
}
