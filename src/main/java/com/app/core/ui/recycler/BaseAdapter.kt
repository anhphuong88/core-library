package com.app.core.ui.recycler

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.app.core.constant.DefaultConstants
import com.app.core.log.Logger
import com.app.core.ui.animation.UIAnimation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

class BaseAdapter<T>(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var lst: MutableList<T?>? = null
    private var viewType: ((T?) -> Int)? = null
    private var viewHolder: ((ViewGroup, Int) -> RecyclerView.ViewHolder)? = null
    private var bind: ((RecyclerView.ViewHolder, Int, T?) -> Unit)? = null
    private var bindPayloads: ((RecyclerView.ViewHolder, Int, Any, T?) -> Unit)? = null
    private val subject = PublishSubject.create<T?>()
    private val compositeDisposable: CompositeDisposable? = CompositeDisposable()
    private var refRecyclerView: WeakReference<RecyclerView>? = null

    val list: List<T?>?
        get() = lst


    init {
        this.lst = ArrayList()
        this.refRecyclerView = WeakReference(recyclerView)
        this.refRecyclerView?.get()?.layoutManager = layoutManager
        this.refRecyclerView?.get()?.adapter = this
    }

    @SuppressWarnings("Unchecked cast")
    fun <V : View> buildOneHolder(
            cViHolder: ((ViewGroup) -> V),
            reset: (V, T?) -> Unit,
            bind: (V, T?) -> Unit
    ): BaseAdapter<*> {
        return this.holder { parent, _ -> BaseHolder<V, T>(cViHolder(parent), reset, bind) }
                .type { 0 }
                .bind { viHolder1, _, item ->
                    if (viHolder1 is BaseHolder<*, *>) {
                        (viHolder1 as? BaseHolder<V, Any?>)?.reset(item)
                        (viHolder1 as? BaseHolder<V, Any?>)?.bind(item)
                    }
                }
    }

    fun type(viewType: (T?) -> Int): BaseAdapter<*> {
        this.viewType = viewType
        return this
    }

    fun holder(viewHolder: (ViewGroup, Int) -> RecyclerView.ViewHolder): BaseAdapter<*> {
        this.viewHolder = viewHolder
        return this
    }

    fun bind(bind: (RecyclerView.ViewHolder, Int, T?) -> Unit): BaseAdapter<*> {
        this.bind = bind
        return this
    }

    fun bindPayloads(bindPayloads: (RecyclerView.ViewHolder, Int, Any, T?) -> Unit): BaseAdapter<*> {
        this.bindPayloads = bindPayloads
        return this
    }

    fun bindEvent(consumer: Consumer<T?>): BaseAdapter<*> {
        compositeDisposable?.add(
                subject
                        .subscribeOn(Schedulers.newThread())
                        .throttleFirst(UIAnimation.animTime, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer)
        )
        return this
    }

    fun bindEventUnDelay(consumer: Consumer<T?>): BaseAdapter<*> {
        compositeDisposable?.add(
                subject
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(consumer)
        )
        return this
    }

    /**
     * Publish Event for Child View of RecyclerView
     *
     * @param view need Parent View is RecyclerView
     */
    fun publishEvent(view: View) {
        if (refRecyclerView?.get() != null && view.parent is RecyclerView) {
            val position = refRecyclerView?.get()?.getChildLayoutPosition(view) ?: -1
            if (position >= 0 && lst != null && lst!!.size > position && lst!![position] != null) {
                subject.onNext(lst!![position]!!)
            }
        }
    }

    fun publishEvent(item: T) {
        subject.onNext(item)
    }

    fun subscribe(owner: LifecycleOwner, liveData: LiveData<DmHolder<T?>?>?) {
        liveData?.observe(owner, Observer { holder ->
            if (holder?.fn != null) {
                when (holder.fn) {
                    DefaultConstants.ActionList.UPDATE -> {
                        val size = if (lst != null) lst!!.size else 0
                        if (lst != null && holder.lst != null)
                            lst?.addAll(holder.lst!!)
                        else
                            lst = holder.lst as MutableList<T?>?
                        if (lst != null && lst!!.size > 0) {
                            notifyItemRangeInserted(size, lst!!.size)
                        }
                    }
                    DefaultConstants.ActionList.REMOVELAST -> if (lst != null && lst!!.size > 0) {
                        lst!!.removeAt(lst!!.size - 1)
                        notifyItemRemoved(lst!!.size)
                    }
                    DefaultConstants.ActionList.UPDATE_ITEM -> if (lst != null && holder.index >= 0 && (lst?.size
                                    ?: 0) > holder.index
                    ) {
                        if (holder.payLoad != null) {
                            notifyItemChanged(holder.index, holder.payLoad)
                        } else {
                            notifyItemChanged(holder.index)
                        }
                    }
                    DefaultConstants.ActionList.RESET -> {
                        if (lst != null)
                            lst!!.clear()
                        notifyDataSetChanged()
                    }
                    DefaultConstants.ActionList.RELOAD -> {
                        if (lst != null) {
                            lst?.clear()
                            if (holder.lst != null)
                                lst?.addAll(holder.lst!!)
                        } else {
                            lst = holder.lst as MutableList<T?>
                        }
                        notifyDataSetChanged()
                        if (refRecyclerView != null && refRecyclerView!!.get() != null) {
                            refRecyclerView?.get()?.scrollToPosition(0)
                        }
                    }
                }
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return viewHolder?.let { it(parent, viewType) }
                ?: BaseHolder<View, T>(view = View(parent.context))

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bind?.let { it(holder, position, if (lst?.size ?: 0 > position) lst!![position] else null) }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
        if (bindPayloads != null && !payloads.isEmpty()) {
            bindPayloads!!.invoke(
                    holder,
                    position,
                    payloads[0],
                    if (lst != null && lst!!.size > position) lst!![position] else null
            )
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewType != null && (lst?.size
                        ?: 0) > position
        ) viewType!!(lst!![position]) else super.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return lst?.size ?: 0
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        Logger.w("onDetachedFromRecyclerView")
        compositeDisposable?.clear()
    }

    companion object {
        fun <T> build(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) =
                BaseAdapter<T>(recyclerView, layoutManager)
    }
}
