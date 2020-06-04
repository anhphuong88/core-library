package com.app.core.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.location.Location
import android.support.annotation.MainThread
import android.view.ViewGroup

import com.app.core.action.Action1
import com.app.core.action.Action2
import com.app.core.constant.DefaultConstants
import com.app.core.rx.Bus

open class BaseActivityViewModel(application: Application) : AndroidViewModel(application) {
    private var connection: ConnectionLiveData? = null
    private var permission: MutableLiveData<PermissionData>? = null
    private var location: LocationLiveData? = null
    private var isConnected: Boolean? = null


    var tabIndex = -1
    private var isShownKeyboard = false

    private fun getConnection(): ConnectionLiveData? {
        if (connection == null)
            connection = ConnectionLiveData(getApplication<Application>().applicationContext)
        return connection
    }

    fun getPermission(): MutableLiveData<PermissionData>? {
        if (permission == null) permission = MutableLiveData()
        return permission
    }

    private fun getLocation(): LocationLiveData? {
        if (location == null) {
            location = LocationLiveData(getApplication())
        }
        return location
    }

    /**
     * Add Listen Location to update
     *
     * @param owner    Activity or Fragment or Service
     * @param callback Location lastLocation
     */

    fun addListenLocation(owner: LifecycleOwner, callback: Action1<Location>) {
        getLocation()?.observe(owner, Observer { callback.invoke(it) })
    }

    fun addRequestListenLocation(isFrequencyUpdate: Boolean, owner: LifecycleOwner, callback: Action1<Location>) {
        addListenLocation(owner, callback)
        Navigation.instance.requestLocation(isFrequencyUpdate)
    }

    fun requestLocation(isFrequencyUpdate: Boolean) {
        getLocation()?.buildGoogleLocation(isFrequencyUpdate)
    }


    /**
     * Add Listen Network to update
     *
     * @param owner    Activity or Fragment or Service
     * @param callback Boolean::isConnected, Boolean::isReloadNetWork
     */
    fun addListenNetwork(owner: LifecycleOwner, callback: Action2<Boolean, Boolean>) {
        getConnection()?.observe(owner, Observer { value ->
            if (isConnected == null) {
                isConnected = value
                callback.invoke(isConnected, isConnected)
            } else {
                if (isConnected !== value) {
                    isConnected = value
                    callback.invoke(isConnected, isConnected)
                } else {
                    callback.invoke(value, false)
                }
            }
        })
    }

    fun addListenPermission(owner: LifecycleOwner, callback: Action1<PermissionData>) {
        getPermission()?.observe(owner, object : Observer<PermissionData> {
            override fun onChanged(value: PermissionData?) {
                callback.invoke(value)
                getPermission()?.removeObserver(this)
            }
        })
    }

    @MainThread
    fun addListenKeyboard(mainView: ViewGroup) {
        mainView.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            mainView.getWindowVisibleDisplayFrame(r)
            val hDiff = mainView.rootView.height - r.bottom
            if (hDiff >= 200) {
                if (!isShownKeyboard) {
                    Bus.normal()?.publish(DefaultConstants.ActionListener.KEYBOARD, hDiff)
                    isShownKeyboard = true
                }
            } else {
                if (isShownKeyboard) {
                    Bus.normal()?.publish(DefaultConstants.ActionListener.KEYBOARD, 0)
                    isShownKeyboard = false
                }
            }
        }
    }
}
