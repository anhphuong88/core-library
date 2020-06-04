package com.app.core.ui.event

import android.support.annotation.MainThread
import android.view.View
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

@MainThread
fun <T : View?> T.click(duration: Long = 300): Observable<T?> {
    return ClickObservable(this).throttleFirst(duration, TimeUnit.MILLISECONDS)
}

@MainThread
fun <T : View?> T.click(action: (T?) -> Unit, duration: Long = 300): Disposable {
    return ClickObservable(this)
            .throttleFirst(duration, TimeUnit.MILLISECONDS)
            .subscribe {
                action(it)
            }
}