package com.app.core.rx

import com.app.core.util.asListOfType
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.ReplaySubject
import io.reactivex.subjects.Subject

data class BusMessage(val key: String = "", val value: Any? = null)

enum class BusType { Default, Behavior, Replay }

object Bus {
    private var publish: Service? = Service()
        get() {
            if (field == null) field = Service()
            return field
        }
    private var behavior: Service? = Service(BehaviorSubject.create<Any>().toSerialized())
        get() {
            if (field == null) field = Service(BehaviorSubject.create<Any>().toSerialized())
            return field
        }

    fun normal(): Service? = publish
    fun navigation(): Service? = publish
    fun behavior(): Service? = behavior

    private var map: MutableMap<String, Service> = HashMap()

    fun get(key: String, busType: BusType = BusType.Default): Service? {
        if (!map.containsKey(key)) {
            map[key] = when (busType) {
                BusType.Behavior -> Service(BehaviorSubject.create<Any>().toSerialized())
                BusType.Replay -> Service(ReplaySubject.create<Any>().toSerialized())
                else -> Service()
            }
        }
        return map[key]
    }

    fun release(key: String) {
        if (map.containsKey(key)) {
            map[key]?.unSubscribeAll()
            map.remove(key)
        }
    }

    fun release() {
        map.forEach {
            it.value.unSubscribeAll()
        }
        map.clear()
    }
}

class Service(val subject: Subject<Any>? = PublishSubject.create<Any>().toSerialized()) {
    var disposables: CompositeDisposable? = CompositeDisposable()

    inline fun <reified E> subscribe(key: String = "", consumer: Consumer<E?>?, scheduler: Scheduler? = AndroidSchedulers.mainThread()): Disposable? {
        var disposable: Disposable? = null
        subject?.observeOn(Schedulers.newThread())
                ?.filter { o ->
                    o is BusMessage
                            && o.key.equals(key, true)
                            && (o.value is E? || o.value is E)
                }
                ?.map { o -> (o as? BusMessage)?.value as? E }
                ?.observeOn(scheduler)
                ?.subscribe(object : Observer<E?> {
                    override fun onComplete() {
                        disposables?.clear()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        disposable?.let { disposables?.add(it) }
                    }

                    override fun onNext(t: E) {
                        consumer?.accept(t)
                    }

                    override fun onError(e: Throwable) {
                        consumer?.accept(null)
                    }

                })
        return disposable
    }

    inline fun <reified E> subscribeList(key: String = "", consumer: Consumer<List<E?>?>?, scheduler: Scheduler? = AndroidSchedulers.mainThread()): Disposable? {
        var disposable: Disposable? = null
        subject?.observeOn(Schedulers.newThread())
                ?.filter { o ->
                    o is BusMessage
                            && o.key.equals(key, true)
                            && (o.value == null || (o.value is List<*>? && o.value.asListOfType<E?>() != null))
                }
                ?.map { o -> ((o as BusMessage).value as List<*>?)?.asListOfType<E?>() }
                ?.observeOn(scheduler)
                ?.subscribe(object : Observer<List<E?>?> {
                    override fun onComplete() {
                        disposables?.clear()
                    }

                    override fun onSubscribe(d: Disposable) {
                        disposable = d
                        disposable?.let { disposables?.add(it) }
                    }

                    override fun onNext(t: List<E?>) {
                        consumer?.accept(t)
                    }

                    override fun onError(e: Throwable) {
                        consumer?.accept(null)
                    }

                })
        return disposable
    }

    fun <E> publish(key: String, value: E?) {
        subject?.onNext(BusMessage(key, value))
    }

    fun unSubscribe(disposable: Disposable?) {
        disposable?.let { disposables?.remove(it) }
    }

    fun unSubscribeAll() {
        disposables?.clear()
    }
}