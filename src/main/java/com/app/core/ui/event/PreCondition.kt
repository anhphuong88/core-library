package com.app.core.ui.event

import android.os.Looper

import io.reactivex.Observer
import io.reactivex.disposables.Disposables

internal object PreCondition {
    @JvmStatic
    fun mainThread(observer: Observer<*>?, action: () -> Unit) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            observer?.onSubscribe(Disposables.empty())
            observer?.onError(IllegalStateException("Please run on MainThread"))
        } else {
            action()
        }
    }
}
