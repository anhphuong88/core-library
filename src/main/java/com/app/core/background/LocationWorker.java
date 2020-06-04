package com.app.core.background;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.LocationServices;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public abstract class LocationWorker extends Worker {

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            LocationServices.getFusedLocationProviderClient(getApplicationContext())
                    .getLastLocation()
                    .addOnFailureListener((e) -> {
                        updateLocation(null);
                    })
                    .addOnSuccessListener(this::updateLocation);
        }
        return Result.success();
    }

    public abstract void updateLocation(Location location);
}
