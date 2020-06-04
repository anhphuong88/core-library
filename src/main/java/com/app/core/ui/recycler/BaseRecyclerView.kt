package com.app.core.ui.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class BaseRecyclerView : RecyclerView {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        this.adapter = null
    }
}
