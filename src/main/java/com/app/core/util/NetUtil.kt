@file:Suppress("DEPRECATION")

package com.app.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.lang.ref.WeakReference

object NetUtil {
    var context: WeakReference<Context?>? = null
    var connected: Boolean? = false
    fun init(context: Context?) {
        this@NetUtil.context = WeakReference(context)
    }

    fun isAvailable(): Boolean {
        context?.let {
            val cm = it.get()?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            return cm?.run {
                val info: NetworkInfo? = this.activeNetworkInfo
                info?.isConnectedOrConnecting

            } ?: false
        }
        return false
    }
}