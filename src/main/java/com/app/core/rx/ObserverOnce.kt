package com.app.core.rx

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class ObserverOnce<T>(var action: (t: T?) -> Unit) : Observer<T> {
    var disposable: Disposable? = null

    override fun onComplete() {
        disposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d;
    }

    override fun onNext(t: T) {
        action(t)
        disposable?.dispose()
    }

    override fun onError(e: Throwable) {
        action(null)
        disposable?.dispose()
    }
}

class ConsumerOnce<T>(var action: (t: T) -> Unit) : Observer<T> {
    var disposable: Disposable? = null

    override fun onComplete() {
        disposable?.dispose()
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d;
    }

    override fun onNext(t: T) {
        action(t)
        disposable?.dispose()
    }

    override fun onError(e: Throwable) {
        disposable?.dispose()
    }
}