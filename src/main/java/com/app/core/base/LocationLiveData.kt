package com.app.core.base

import android.Manifest
import android.arch.lifecycle.LiveData
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.Location
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import java.lang.ref.WeakReference

internal class LocationLiveData(context: Context) : LiveData<Location>(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private var mGoogleLocation: GoogleApiClient? = null
    private var mLastLocation: Location? = null
    private var isFrequencyUpdate = false

    private val refContext: WeakReference<Context> = WeakReference(context)

    @Synchronized
    fun buildGoogleLocation(isFrequencyUpdate: Boolean) {
        refContext.get()?.let {
            this.isFrequencyUpdate = isFrequencyUpdate
            if (mGoogleLocation == null) {
                mGoogleLocation = GoogleApiClient.Builder(it)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build()
            } else {
                if (mGoogleLocation?.isConnected == true)
                    LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleLocation, this)
                mGoogleLocation?.connect()
            }
        }
    }

    override fun onInactive() {
        if (mGoogleLocation != null) {
            if (mGoogleLocation?.isConnected == true)
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleLocation, this)
            mGoogleLocation?.disconnect()
        }
    }

    override fun onActive() {
        if (mGoogleLocation != null) mGoogleLocation!!.connect()
        postValue(mLastLocation)
    }

    override fun onConnected(bundle: Bundle?) {
        refContext.get()?.let {
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
                if (!isFrequencyUpdate) {
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleLocation)
                } else {
                    val mLocationRequest = LocationRequest.create()
                    mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY// level city
                    mLocationRequest.interval = 1000 // Update location every second
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleLocation, mLocationRequest, this)
                    mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleLocation)
                }
            }
            postValue(mLastLocation)
        }
    }

    override fun onConnectionSuspended(i: Int) {
        postValue(mLastLocation)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        postValue(mLastLocation)
    }

    override fun onLocationChanged(location: Location) {
        mLastLocation = location
        postValue(mLastLocation)
    }
}
