package com.app.core.ui.recycler

import android.support.v7.widget.RecyclerView
import android.view.View

class BaseHolder<V : View, T : Any?>(
        private val view: V,
        private val resetCallBack: ((V, T) -> Unit)? = null,
        private val bindCallBack: ((V, T) -> Unit)? = null
) : RecyclerView.ViewHolder(view) {

    fun reset(item: T) {
        resetCallBack?.let { it(this.view, item) }
    }

    fun bind(item: T) {
        bindCallBack?.let { it(this.view, item) }
    }
}
