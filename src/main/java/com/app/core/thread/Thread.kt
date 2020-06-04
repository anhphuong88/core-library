package com.app.core.thread

import com.app.core.rx.ObserverOnce
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun runInBackground(init: () -> Unit) {
    Observable.just("").observeOn(Schedulers.newThread()).subscribe(ObserverOnce {
        init()
    })
}

fun runInBackground(init: () -> Unit, delay: Long) {
    Observable.timer(delay, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.newThread())
            .subscribe(ObserverOnce {
                init()
            })
}

fun runInBackgroundCancelable(init: () -> Unit, delay: Long): Disposable {
    var disposable: Disposable? = null
    disposable =  Observable.timer(delay, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.newThread())
            .subscribe( {
                init()
                disposable?.dispose()
            }, {
                disposable?.dispose()
            })
    return disposable
}

fun runInSingleBackground(init: () -> Unit) {
    Observable.just("").observeOn(Schedulers.single()).subscribe(ObserverOnce {
        init()
    })
}

fun runInIoBackground(init: () -> Unit) {
    Observable.just("").observeOn(Schedulers.io()).subscribe(ObserverOnce {
        init()
    })
}