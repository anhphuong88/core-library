package com.app.core.ui.event


import android.view.View

import java.lang.ref.WeakReference

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.MainThreadDisposable


internal class Listener<T : View?>(view: T?, private var refObserver: Observer<in T?>?) : MainThreadDisposable(), View.OnClickListener {
    private val refView: WeakReference<T?>?

    init {
        refView = WeakReference(view)
    }

    override fun onClick(v: View) {
        if (!isDisposed ) refObserver?.onNext(refView?.get())
    }

    override fun onDispose() {
        refView?.get()?.setOnClickListener(null)
    }
}

internal class ClickObservable<T : View?>(view: T?) : Observable<T?>() {
    private val refView: WeakReference<T?>?

    init {
        this.refView = WeakReference(view)
    }

    override fun subscribeActual(observer: Observer<in T?>?) {
        PreCondition.mainThread(observer) {
            refView?.get()?.let {
                val listener: Listener<T> = Listener(it, observer)
                observer?.onSubscribe(listener)
                it?.setOnClickListener(listener)
            }
        }
    }
}
