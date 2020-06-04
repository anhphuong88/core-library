package com.app.core.base

import android.arch.lifecycle.LiveData
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager

import com.app.core.util.NetUtil

import java.lang.ref.WeakReference

@Suppress("DEPRECATION")
internal class ConnectionLiveData(context: Context) : LiveData<Boolean>() {

    private val refContext: WeakReference<Context>?

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (NetUtil.isAvailable()) {
                postValue(true)
            } else {
                postValue(false)
            }
        }
    }

    init {
        refContext = WeakReference(context)
    }

    override fun onActive() {
        refContext?.get()?.let {
            val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
            it.registerReceiver(networkReceiver, filter)
        }
    }

    override fun onInactive() {
        refContext?.get()?.unregisterReceiver(networkReceiver)
    }

}
